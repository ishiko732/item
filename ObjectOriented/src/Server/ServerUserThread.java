package Server;

import Client.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerUserThread extends Thread {
    private final User user;
    private final DataOutputStream dos;
    private final DataInputStream dis;
    private final Socket client;

    public ServerUserThread(User user, DataOutputStream dos, DataInputStream dis, Socket client) {
        this.user = user;
        this.dos = dos;
        this.dis = dis;
        this.client = client;
    }

    @Override
    public void run() {
        super.run();
        String uid = user.getUID();
        String info;
        String regex = "\\[[^\\]]*\\]";//匹配中括号
        Pattern compile = Pattern.compile(regex);

        while (!client.isClosed()) {
            try {
                info = dis.readUTF();
                System.out.println("Client-" + uid + ":" + info);
                if (("login-" + uid).equals(info)) {//登录
                    synchronized (user){
                        if (Server.islogin(user) == 1) {//成功导入列表，登录成功
                            dos.writeUTF("login(YES):" + user.getUID());
                        } else {
                            dos.writeUTF("login(No):" + user.getUID());
                        }
                    }
                } else if (info.indexOf("Chat-[" + uid + "]:") == 0) {//发送信息到服务器处理
                    //Chat-[(UID)]:send=[(value)],obj=[(UID/Server)];
                    Server.sendMessage(user, info);
                }else if("command:Client!getUserList".equals(info)){//读取命令--命令:客户端!获取用户列表
                    Iterator<String> it = Server.getUserMap().keySet().iterator();
                    ArrayList<String> userList=new ArrayList<>();
                    while(it.hasNext()){
                        userList.add(it.next());
                    }
                    synchronized (this){
                        ObjectOutputStream oos= new ObjectOutputStream(client.getOutputStream());
                        oos.writeObject(userList);
                        System.out.println("Server:sendMessage=" +userList);
                    }
//                    oos.close();
                }
            } catch (SocketException e) {
                System.err.println("Client-" + uid + ":" + "移除用户信息。原因：与服务器断开了连接");
                Server.removeConnection(uid);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

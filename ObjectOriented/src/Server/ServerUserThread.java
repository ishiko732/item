package Server;

import Client.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
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
                    synchronized (user) {
                        if (Server.islogin(user) == 1) {//成功导入列表，登录成功
                            dos.writeUTF("login(YES):" + user.getUID());
                        } else {
                            dos.writeUTF("login(No):" + user.getUID());
                        }
                    }
                } else if (info.indexOf("Chat-[" + uid + "]:") == 0) {//发送信息到服务器处理
                    //Chat-[(UID)]:send=[(value)],obj=[(UID/Server)];
                    Server.sendMessage(user, info);
                } else if ("command:Client!getUserList;".equals(info)) {//读取命令--命令:客户端!获取用户列表
                    Map<String,User> serverMap=Server.getUserMap();
                    Iterator<String> it = serverMap.keySet().iterator();
                    Map<String,String> userMap = new HashMap<>();
                    while (it.hasNext()) {
                        String str=it.next();
                        userMap.put(str,serverMap.get(str).getPassword());
                    }
                    synchronized (user) {
                        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                        oos.writeObject(userMap);
                        System.out.println("Server:sendMessage=" + userMap);
                    }
//                    oos.close();
                }else if(info.indexOf("command:Client!attackUser:[")==0){//读取命令--命令:客户端!申请对战(申请人,邀请人)
                    Server.sendAttack(user, info);
                }else if(info.indexOf("command-game:newGame={write=[")==0){//开启游戏对局
                    Matcher matcher = compile.matcher(info);// newGame={write=[(userUID2)],black=[(userUID1)]}
                    ArrayList<String> arrayList=new ArrayList<>();
                    while (matcher.find()) {
                        arrayList.add(matcher.group().replaceAll("\\(|\\)|\\[|\\]", ""));
                    }
                    System.out.println("Server:开启对局:"+"write:"+arrayList.get(0)+" black:"+arrayList.get(1));
                    //创建房间操作,将user 1,2放入房间里面去
                    //未写


                }else if(info.indexOf("command-game:errorGame={user=[")==0){
                    Matcher matcher = compile.matcher(info);// errorGame={user=[(userUID1)],send=[(userUID2)拒绝了对战]}
                    ArrayList<String> arrayList=new ArrayList<>();
                    while (matcher.find()) {
                        arrayList.add(matcher.group().replaceAll("\\(|\\)|\\[|\\]", ""));
                    }
                    User user1=Server.getUserMap().get(arrayList.get(0));
                    //Chat-[(Server/UID)]:send=[(value)],obj=[(UID/Server)];
                    System.out.println("Server:发送消息给客户端");
                    Server.sendMessage(user1,"Chat-[Server]:send=["+arrayList.get(1)+"],obj=["+user1.getUID()+"]");
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

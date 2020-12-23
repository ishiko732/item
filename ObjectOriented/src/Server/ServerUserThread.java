package Server;

import Client.User;
import Game.Core;
import Game.GameRoomUser;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerUserThread extends Thread {
    private final User user;
    private final InputStream is;
    private final OutputStream out;
    private final Socket client;
    private final ArrayList<GameRoomUser> room = new ArrayList<>();
    public ServerUserThread(List<Object> list,  Socket client) {
        this.user =(User)list.get(0);
        this.is =(InputStream)list.get(1);
        this.out =(OutputStream)list.get(2);
        this.client = client;
    }

    @Override
    public void run() {
        super.run();
        String uid = user.getUID();
        String info;
        String regex = "\\[[^\\]]*\\]";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos=new DataOutputStream(out);
        while (!client.isClosed()) {
            try {
                info = dis.readUTF();
                System.out.println("Client-" + uid + ":" + info);
                if (info.indexOf("Chat-[" + uid + "]:") == 0) {//发送信息到服务器处理
                    //Chat-[(UID)]:send=[(value)],obj=[(UID/Server)];
                    Server.sendMessage(user, info);
                } else if ("command:Client!getUserList;".equals(info)) {//读取命令--命令:客户端!获取用户列表
                    Map<String, List<Object>> serverMap = Server.getUserMap();
                    Iterator<String> it = serverMap.keySet().iterator();
                    Map<String, String> userMap = new HashMap<>();
                    while (it.hasNext()) {
                        String str = it.next();
                        User user = (User) serverMap.get(str).get(0);
                        userMap.put(str, user.getPassword());
                    }
                    // userMap -> String
//                    System.out.println("get-list:"+userMap);
                    dos.writeUTF("get-userList:"+userMap.toString());
                } else if (info.indexOf("command:Client!attackUser:[") == 0) {//读取命令--命令:客户端!申请对战(申请人,邀请人)
                    Server.sendAttack(user, info);
                } else if (info.indexOf("command-game:newGame={write=[") == 0) {//开启游戏对局
                    Matcher matcher = compile.matcher(info);// newGame={write=[(userUID2)],black=[(userUID1)]}
                    ArrayList<String> arrayList = new ArrayList<>();
                    while (matcher.find()) {
                        arrayList.add(matcher.group().replaceAll("\\(|\\)|\\[|\\]", ""));
                    }
                    Map<String, List<Object>> userMap = Server.getUserMap();
                    Object o_write = userMap.get(arrayList.get(1)).get(0);//黑棋是自己
                    Object o_black = userMap.get(arrayList.get(0)).get(0);
                    GameRoomUser gameRoomUser = new GameRoomUser((User)o_write, (User)o_black, new Core(19, 19), room.size()+1);
                    synchronized (user) {
                        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                        oos.writeObject(gameRoomUser);
                        room.add(gameRoomUser);
                        //给发起者消息
                        oos = new ObjectOutputStream((OutputStream)userMap.get(arrayList.get(1)).get(2));
                        oos.writeObject(gameRoomUser);
                        room.add(gameRoomUser);
                    }
                    System.out.println("Server:开启对局:房间号(" + room.size() + ")write:" +((User) o_write).getUID()+ " black:" + ((User) o_black).getUID());
                    //创建房间操作,将user 1,2放入房间里面去
                    //未写


                } else if (info.indexOf("command-game:errorGame={user=[") == 0) {
                    Matcher matcher = compile.matcher(info);// errorGame={user=[(userUID1)],send=[(userUID2)拒绝了对战]}
                    ArrayList<String> arrayList = new ArrayList<>();
                    while (matcher.find()) {
                        arrayList.add(matcher.group().replaceAll("\\(|\\)|\\[|\\]", ""));
                    }

                    User user1 = (User)Server.getUserMap().get(arrayList.get(0)).get(0);
                    //Chat-[(Server/UID)]:send=[(value)],obj=[(UID/Server)];

                    GameRoomUser gameRoomUser = new GameRoomUser(null, null, new Core(19, 19), -1);
                    synchronized (user) {
                        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                        oos.writeObject(gameRoomUser);
                        room.add(gameRoomUser);
                    }
//                    Server.sendMessage(user1,"Chat-[Server]:send=["+arrayList.get(1)+"],obj=["+user1.getUID()+"]");
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

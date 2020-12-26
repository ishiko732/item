package Server;

import Client.User;
import Game.Core;
import Game.GameRoomUser;
import Game.Transfer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class ServerUserThread extends Thread {
    private final User user;
    private final InputStream is;
    private final OutputStream out;
    private final Socket client;

    public ServerUserThread(List<Object> list, Socket client) {
        this.user = (User) list.get(0);
        this.is = (InputStream) list.get(1);
        this.out = (OutputStream) list.get(2);
        this.client = client;
    }

    @Override
    public void run() {
        super.run();
        String uid = user.getUID();
        String info;
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(out);
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
                    dos.writeUTF("get-userList:" + userMap.toString());
                } else if (info.indexOf("command:Client!attackUser:[") == 0) {//读取命令--命令:客户端!申请对战(申请人,邀请人)
                    Server.sendAttack(user, info);
                } else if (info.indexOf("command-game:newGame={write=[") == 0) {//开启游戏对局
                    // newGame={write=[(userUID2)],black=[(userUID1)]}
                    ArrayList<String> arrayList =Transfer.transferGameMessage(info);
                    Map<String, List<Object>> userMap = Server.getUserMap();
                    Object o_write = userMap.get(arrayList.get(0)).get(0);
                    Object o_black = userMap.get(arrayList.get(1)).get(0);//黑棋是自己
                    int id = Server.getRoom().size() + 1;
                    GameRoomUser gameRoomUser = new GameRoomUser((User) o_write, (User) o_black, new Core(19, 19), id);
                    synchronized (user) {
                        dos.writeUTF("gameRoom:" + gameRoomUser.toString());//给被邀请人发消息
                        //给发起者消息
//                        System.out.println(arrayList.get(0));//申请人
//                        System.out.println(arrayList.get(1));//被邀请人
                        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream) userMap.get(arrayList.get(0)).get(2));//黑棋是申请人
                        dataOutputStream.writeUTF("gameRoom:" + gameRoomUser.toString());
                        Server.getRoom().put(id, gameRoomUser);
                    }
                    System.out.println("Server:开启对局:房间号(" + Server.getRoom().size() + ")write:" + ((User) o_write).getUID() + " black:" + ((User) o_black).getUID());
                } else if (info.indexOf("command-game:errorGame={write=[") == 0) {
                    // errorGame={write=[(userUID2)],black=[(userUID1)]}
                    ArrayList<String> arrayList = Transfer.transferGameMessage(info);
                    Map<String, List<Object>> userMap = Server.getUserMap();
                    Object o_write = userMap.get(arrayList.get(0)).get(0);
                    Object o_black = userMap.get(arrayList.get(1)).get(0);//黑棋是自己
                    GameRoomUser gameRoomUser = new GameRoomUser((User) o_write, (User) o_black, new Core(19, 19), -1);
                    synchronized (user) {
                        dos.writeUTF("gameRoom:" + gameRoomUser.toString());//给被邀请人发消息
                        //给发起者消息
//                        System.out.println(arrayList.get(0));//申请人
//                        System.out.println(arrayList.get(1));//被邀请人
                        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream) userMap.get(arrayList.get(0)).get(2));//黑棋是申请人
                        dataOutputStream.writeUTF("gameRoom:" + gameRoomUser.toString());
                    }
                    System.out.println("Server:无法开启对局:对方拒绝了游戏!");
                } else if (info.indexOf("command-game:game={var=") == 0) {//仅仅做转发工作 ,服务器不处理太多细节
                    //game={var=[(while)],xy=[(x|y)],roomID=[(id)]};
                    Map<String, String> gameMap = Transfer.gameMap(info);//{xy=(2,2), var=write, roomID=1}
                    forwardMessage(info, gameMap);

                } else if (info.indexOf("command-game:game={command=") == 0) {//仅仅做转发工作 ,服务器不处理太多细节
                    Map<String, String> map = Transfer.gameCommand(info);
//                    String command = map.get("command");
                    forwardMessage(info, map);//转发信息
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

    private void forwardMessage(String info, Map<String, String> map) throws IOException {//需要修改 NullPointerException
        GameRoomUser gameRoom = Server.getRoom().get(Integer.parseInt(map.get("roomID")));
        User user1 = gameRoom.getUser_black();
        User user2 = gameRoom.getUser_write();
        Map<String, List<Object>> userMap = Server.getUserMap();
        DataOutputStream user1_out = new DataOutputStream((OutputStream) userMap.get(user1.getUID()).get(2));
        user1_out.writeUTF(info);
        DataOutputStream user2_out = new DataOutputStream((OutputStream) userMap.get(user2.getUID()).get(2));
        user2_out.writeUTF(info);
    }

}



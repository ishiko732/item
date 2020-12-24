package Server;

import Client.User;
import Game.GameRoomUser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    private static final Map<String,List<Object>> userMap = new LinkedHashMap<>();
    private static final Map<Integer, GameRoomUser> room = new HashMap<>();
    public static void main(String[] args) {
        new Server();
    }
    public Server() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8089);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println("---Sever.listener---");
        while (true) {
            try {
                Socket client = server.accept();
                User user = Server.getUser(client.getInputStream());
                if (user == null) {
                    System.err.println("Server:未获取到有效的对象信息");
                    continue;
                }
                //多线程操作
                System.out.println("Client-" + user.getUID() + "：" + user);
                List<Object> list=new ArrayList<>();
                String uid = user.getUID();
                list.add(user);//用户类
                list.add(client.getInputStream());//读取客户端发送信息
                list.add(client.getOutputStream());;//写信息给客户端信息
//                new DataOutputStream(client.getOutputStream()).writeUTF("Server:get User" + user.getUID() + "(" + client.getInetAddress() + ":" + client.getPort() + ")");//发送给客户端，正常收到用户的对象，开始创建线程
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                if (!userMap.containsKey(uid)) {//登录操作
                    userMap.put(uid,list);
                    dos.writeUTF("Server:login User" + user.getUID());
                }else{
//                    dos.writeUTF("get-chat[Server],send=[登录失败,原因是用户已登录];" );
                    client.close();//已经有了,直接断开吧
                }
                new ServerUserThread(list,client).start();

            } catch (SocketException e) {
                System.err.println("Server:获取对象的时刻,客户端关闭");
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static User getUser(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        Object obj = ois.readObject();
        if (obj instanceof User) {
            return (User) obj;
        } else {
            return null;
        }
    }

    public static void removeConnection(String UID) {
        userMap.remove(UID);
    }


    public static void sendMessage(User user, String message) {
        String regex = "\\[[^\\]]*\\]";//chat-正则匹配
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(message);//String test = "Chat-[康明]:send=[\"我是猪\"],obj=[Server];";
        //Chat-[(UID)]:send=[(value)],obj=[(UID/Server)];
        ArrayList<String> arrayList = new ArrayList<>();
        while (matcher.find()) {
            arrayList.add(matcher.group().replaceAll("\\[|\\]", ""));
        }
        if (arrayList.size() != 3) {
            System.err.println("Server:收到来自(" + user.getUID() + ")错误的聊天信息:" + message);
        }
        try {
            if (user.getUID().equals(arrayList.get(0)) && (userMap.containsKey(arrayList.get(2)) || "Server".equals(arrayList.get(2)))) {//发送信息到user2 (服务器接受还未处理)
                String sendMessage = "get-chat[" + arrayList.get(0) + "],send=[" + arrayList.get(1) + "];";
                if ("Server".equals(arrayList.get(2))) {//我就是服务器，我应该如何处理呢？ 转发给全部人
                    System.out.println("Server：服务器收到来自" + arrayList.get(0) + "的信息：" + arrayList.get(1));
                    //0 user, 1 input 2 output
                    DataOutputStream dataOutputStream = new DataOutputStream((OutputStream) userMap.get(arrayList.get(0)).get(2));
                    dataOutputStream.writeUTF("sendMessage:(Yes)");
                    Iterator<String> userIt=userMap.keySet().iterator();
                    while (userIt.hasNext()) {
                        String uid=userIt.next();
                        if(!arrayList.get(0).equals(uid)){
                            new DataOutputStream((OutputStream) userMap.get(uid).get(2)).writeUTF(sendMessage);
                        }
                    }
                } else {
                    DataOutputStream dataOutputStream = new DataOutputStream((OutputStream) userMap.get(arrayList.get(2)).get(2));
                    dataOutputStream.writeUTF(sendMessage);
                    //user1 -(Message)-> user2
                    //message: get char[(User1.uid)],send=[(message)];
                    dataOutputStream.writeUTF("sendMessage:(Yes)");
                    //Server -( sendMessage:(Yes) )-> user1
                }
            } else if ("Server".equals(arrayList.get(0)) && userMap.containsKey(arrayList.get(2))) {//发送方是Server 接受方是客户端
                String sendMessage = "get-chat[" + arrayList.get(0) + "],send=[" + arrayList.get(1) + "];";
                DataOutputStream dataOutputStream = new DataOutputStream((OutputStream) userMap.get(arrayList.get(2)).get(2));
                dataOutputStream.writeUTF(sendMessage);

            } else {
                System.err.println("Server:发送信息失败的原因为(发送端情况:" + user.getUID().equals(arrayList.get(0)) + ",接受端情况:" + userMap.containsKey(arrayList.get(2)) + ");");
            }
        } catch (SocketException e) {
            System.err.println("Client-" + arrayList.get(2) + ":" + "拒绝了连接");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String[] attackDoubleUser(String message) {
        String regex = "\\[[^\\]]*\\]";//chat-正则匹配
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(message);//command:Client!attackUser:[(UID),(attackUserUID)];
        StringBuilder user_merge = new StringBuilder();
        while (matcher.find()) {
            user_merge.append(matcher.group().replaceAll("\\(|\\)|\\[|\\]", ""));
        }
        return user_merge.toString().split(",");//0 申请人 1被邀请人
    }

    public static void sendAttack(User user, String message) {
        String[] userUID = attackDoubleUser(message);//0 申请人 1被邀请人
        if (userUID.length != 2) {
            System.err.println("Server:收到来自(" + user.getUID() + ")错误的对战信息:" + message);
        }
        if (user.getUID().equals(userUID[0]) && (userMap.containsKey(userUID[1]) || "Server".equals(userUID[1]))) {//发送信息到user2 (服务器接受还未处理)
            try {
                //对于发起者 进行等待
                //对于被邀请 发起询问用户命令
                String sendMessage = "get-wait[" + userUID[0] + "],command=[wait],send=[" + userUID[1] + "];";
//                userMap.get(userUID[0]).getDos().writeUTF(sendMessage);//发送等待命令
                sendMessage = "get-attack[" + userUID[0] + "],command=[game],send=[请求对战];";
                new DataOutputStream((OutputStream) userMap.get(userUID[1]).get(2)).writeUTF(sendMessage);//发送请求命令到客户端2 userUID1
            } catch (SocketException e) {
                System.err.println("Client-" + userUID[1] + ":" + "拒绝了连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Server:发送信息失败的原因为(发送端情况:" + user.getUID().equals(userUID[0]) + ",接受端情况:" + userMap.containsKey(userUID[1]) + ");");
        }

    }

    public static Map<String, List<Object>> getUserMap() {
        return userMap;
    }

    public static Map<Integer, GameRoomUser> getRoom() {
        return room;
    }
}

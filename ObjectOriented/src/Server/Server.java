package Server;

import Client.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class Server {
    private static final Map<String, User> userlist = new LinkedHashMap<>();

    public Server() {
        try {
            ServerSocket server = new ServerSocket(8089);
            System.out.println("---Sever.listener---");
            while (true) {
                Socket client = server.accept();
                User user = Server.getUser(client.getInputStream());
                if (user == null) {
                    System.out.println("Server:未获取到有效的对象信息");
                    continue;
                }
//                userlist.put(uid, user);
                //多线程操作
                System.out.println("--Client-" + user.getUID() + "：" + user);
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                DataInputStream dis = new DataInputStream(client.getInputStream());
                dos.writeUTF("Server:get User" + user.getUID());//发送给客户端，正常收到用户的对象，开始创建线程
                new ServerUserThread(user, dos, dis,client).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUser(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (User) ois.readObject();
    }

    public static void removeConnection(String UID){
        userlist.remove(UID);
    }
    public static int islogin(User user) {//登录
        if (userlist.containsKey(user.getUID())) {
            return 0;
        } else {
            userlist.put(user.getUID(), user);
            return 1;
        }


    }
}

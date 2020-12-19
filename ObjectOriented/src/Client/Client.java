package Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final User user;
    private boolean push;

    public Client(User user) {
        this.user = user;
        try {
            client = new Socket(user.getServerIP(), user.getServerPort());
            dis = new DataInputStream(client.getInputStream());//read Server Massage->Client
            dos = new DataOutputStream(client.getOutputStream());//write Client Massage->Server
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public boolean pushUser() {//发送对象
        boolean ispush = false;
        try {
            ObjectOutputStream obj = new ObjectOutputStream(client.getOutputStream());//write User Object->Server
            obj.writeObject(user);//将对象送到服务器上
            ispush = ("Server:get User" + user.getUID()).equals(dis.readUTF());//服务器正常收到用户对象
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        this.push = true;
        return ispush;
    }

    public boolean loginUser() {//登录操作
        boolean islogin = false;
        if (!push) {
            pushUser();
        } else {
            try {
                dos.writeUTF("login-" + user.getUID());
                String ret = dis.readUTF();
                islogin = ("login(YES):" + user.getUID()).equals(ret);//正确登录
                if (!islogin) {
                    throw new IOException("Client-" + user.getUID() + ":" + ret);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return islogin;
    }

    public void sendMessage(String value, String acceptUser) {

        String message = "Chat-[(UID)]:send=[(value)],obj=[(UID/Server)];";
        message = message.replace("(UID)", user.getUID());
        message = message.replace("(value)", value);
        message = message.replace("(UID/Server)", acceptUser);
        try {
            dos.writeUTF(message);
//            String info = dis.readUTF();//读取回执信息
//            System.out.println(info);
//            if ("sendMessage:(Yes)".equals(info)) {
//                System.out.println("Client-" + user.getUID() + ":sendMessage=" + message);
//            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void messageListener() {
        ClientUserMessage userMessage = new ClientUserMessage(user, dis);
        System.out.println("Client-" + user.getUID() + ":listener message ing..");
        new Thread(userMessage).start();

    }

    public OutputStream getOutputStream() {
        OutputStream outputStream = null;
        try {
            outputStream = client.getOutputStream();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return outputStream;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public User getUser() {
        return user;
    }
}

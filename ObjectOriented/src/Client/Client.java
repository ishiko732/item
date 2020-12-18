package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;
    private User user;
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

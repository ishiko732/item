package Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
        ClientUserMessage userMessage = new ClientUserMessage(user, dis, dos);
        System.out.println("Client-" + user.getUID() + ":listener message ing..");
        new Thread(userMessage).start();

    }

    public ArrayList<String> getUserList() {
        ArrayList<String> arraylist = null;
        try {
            sendCommand("getUserList");//dos.writeUTF("command:Client!getUserList");写入命令--命令:客户端!获取用户列表
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
//            System.out.println("test-read-obj");
            arraylist = (ArrayList<String>) ois.readObject();
//            System.out.println("test-obj");
//            ois.close();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
        return arraylist;
    }

    public void sendCommand(String command) throws IOException {//写入命令--命令:客户端! (命令指令)
        dos.writeUTF("command:Client!" + command + ";");//写入命令--命令:客户端!
        // 获取用户列表 getUserList
        // 请求对战 attackUser:[(UID),(attackUserUID)]
    }

    public void applyAttack(String attackUserUID) {
        try {
            sendCommand("attackUser:[" + user.getUID() + "," + attackUserUID + "]");//command:Client!attackUser:[(UID),(attackUserUID)];
        } catch (IOException e) {
            e.printStackTrace();
        }
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

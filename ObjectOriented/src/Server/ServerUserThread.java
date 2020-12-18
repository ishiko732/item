package Server;

import Client.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

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
        String uid = user.getUID();
        String info;
        while (!client.isClosed()) {
            try {
                info = dis.readUTF();
                System.out.println("Client-" + uid + ":" + info);
                if (("login-" + uid).equals(info)) {
                    if (Server.islogin(user) == 1) {//成功导入列表，登录成功
                        dos.writeUTF("login(YES):" + user.getUID());
                    } else {
                        dos.writeUTF("login(No):" + user.getUID());
                    }
                }/*else if{

                }*/
            } catch(SocketException e){
                System.out.println("Client-" + uid + ":" + "关闭了TCP连接");
                Server.removeConnection(uid);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

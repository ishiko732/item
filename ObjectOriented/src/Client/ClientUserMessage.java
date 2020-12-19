package Client;

import Server.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientUserMessage extends Thread {
    private final User user;
    private final DataInputStream dis;
    private String message;

    public ClientUserMessage(User user, DataInputStream dis) {
        this.user = user;
        this.dis = dis;
    }

    @Override
    public void run() {
        super.run();
        String regex = "\\[[^\\]]*\\]";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        while (true) {
            try {
                Thread.sleep(50);
                synchronized (this) {
                    message = dis.readUTF();
                }
                if (!"".equals(message)) {
                    if (message.indexOf("get-chat[") == 0) {//接受到信息
                        // "get-chat["+chatMessage[0]+"],send=["+chatMessage[1]+"];"
                        Matcher matcher = compile.matcher(message);
                        ArrayList<String> arrayList = new ArrayList<>();
                        while (matcher.find()) {
                            arrayList.add(matcher.group().replaceAll("\\[|\\]", ""));
                        }
                        System.out.println("Client-" + user.getUID() + ":来自" + arrayList.get(0) + "客户端的消息:" + arrayList.get(1));
                    } else if ("sendMessage:(Yes)".equals(message)) {
                        System.out.println("Client-" + user.getUID() + ":sendMessage=" + message);
                    }
                }
            } catch (SocketException e) {
                System.err.println("Client-" + user.getUID() + ":" + "与服务器断开了连接");
                break;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public User getUser() {
        return user;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package Client;

import Game.GameRoomUser;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final User user;
    private boolean push;
    private PrintStream ps;
    private ObjectInputStream ois;
//    private ClientUserMessage userMessage=null;

    public Client(User user) {
        this.user = user;
        try {
            client = new Socket(user.getServerIP(), user.getServerPort());
            dis = new DataInputStream(client.getInputStream());//read Server Massage->Client
            dos = new DataOutputStream(client.getOutputStream());//write Client Massage->Server
            ObjectOutputStream obj = new ObjectOutputStream(client.getOutputStream());//write User Object->Server
            obj.writeObject(user);//将对象送到服务器上
            push = ("Server:login User" + user.getUID()).equals(dis.readUTF());//服务器正常收到用户对象
        } catch (SocketException e) {
            System.err.println("Client:与服务器通信失败");
        } catch (EOFException eofException) {
            System.err.println("Client:服务器关闭与Client(" + user.getUID() + ")通信");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public boolean islogin() {
        return push;
    }

    public void sendMessage(String value, String acceptUser) {

        String message = "Chat-[(UID)]:send=[(value)],obj=[(UID/Server)];";
        message = message.replace("(UID)", user.getUID());
        message = message.replace("(value)", value);
        message = message.replace("(UID/Server)", acceptUser);
        try {
            dos.writeUTF(message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void messageListener() {//创建监听器
        System.out.println("Client-" + user.getUID() + ":listener message ing..");
//        userMessage = new ClientUserMessage(user, dis, dos);
//        new Thread(userMessage).start();
        clientThread();
    }

    public Map<String, String> getUserList() {
        Map<String, String> userMap = null;
        try {
            sendCommand("getUserList");//dos.writeUTF("command:Client!getUserList");写入命令--命令:客户端!获取用户列表
            if(ois==null){
                ois = new ObjectInputStream(client.getInputStream());
            }
            userMap = (Map<String, String>) ois.readObject();
            System.out.println(userMap);
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
        return userMap;
    }

    public void sendCommand(String command) throws IOException {//写入命令--命令:客户端! (命令指令)
        dos.writeUTF("command:Client!" + command + ";");//写入命令--命令:客户端!
        // 获取用户列表 getUserList
        // 请求对战 attackUser:[(UID),(attackUserUID)]
    }

    public void applyAttack(String attackUserUID) {
        try {
            sendCommand("attackUser:[" + user.getUID() + "," + attackUserUID + "]");//command:Client!attackUser:[(UID),(attackUserUID)];
            if(ois==null){
                ois = new ObjectInputStream(client.getInputStream());
            }
            System.out.println("读取");
            Object o = ois.readObject();
            if(o instanceof  GameRoomUser){
                System.out.println(o);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public GameRoomUser getGameRoom(String my, String your) {
        GameRoomUser gameRoomUser = null;
        try {
            sendCommand("attackUser:[" + my + "," + your + "]");//写入命令--命令:客户端!请求对战
            while (gameRoomUser == null) {
//                gameRoomUser =userMessage.getGameRoomUser();
                Thread.sleep(1000);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return gameRoomUser;
    }

    private String message;
    private JTextArea jTXT;
    private GameRoomUser gameRoomUser;

    private void clientThread() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                String regex = "\\[[^\\]]*\\]";//匹配中括号
                Pattern compile = Pattern.compile(regex);
                while (true) {
                    try {
                        Thread.sleep(100);
                        synchronized (user) {
                            message = dis.readUTF();
                        }
//                System.out.println("(get)Client: "+message);
                        if (!"".equals(message)) {
                            if (message.indexOf("get-chat[") == 0) {//接受到信息
                                // "get-chat["+chatMessage[0]+"],send=["+chatMessage[1]+"];"
                                Matcher matcher = compile.matcher(message);
                                ArrayList<String> arrayList = new ArrayList<>();
                                while (matcher.find()) {
                                    arrayList.add(matcher.group().replaceAll("\\[|\\]", ""));
                                }
                                if (jTXT != null) {
                                    jTXT.append(arrayList.get(0) + ":" + arrayList.get(1) + "\n");
                                } else {
                                    System.out.println("Client-" + user.getUID() + ":来自" + arrayList.get(0) + "客户端的消息:" + arrayList.get(1));
                                }
                            } else if ("sendMessage:(Yes)".equals(message)) {
                                    System.out.println("Client-" + user.getUID() + ":sendMessage=" + message);
                            } else if (message.indexOf("get-wait[") == 0) {//等待命令
                                Matcher matcher = compile.matcher(message);//"get-wait[" + userUID[0] + "],command=[wait],send=[" + userUID[1] + "];";
                                StringBuilder merge = new StringBuilder();
                                while (matcher.find()) {
                                    merge.append(matcher.group().replaceAll("\\(|\\)|\\[|\\]", ""));
                                }
                                String[] waitMessage = merge.toString().split(",");
                                if (waitMessage.length != 3) {
                                    System.err.println("Client-" + user.getUID() + ":" + "错误的等待信息");
                                }
                            } else if (message.indexOf("get-attack[") == 0) {
                                Matcher matcher = compile.matcher(message);// "get-attack[" + userUID[0] + "],command=[game],send=[请求对战];";
                                ArrayList<String> arrayList = new ArrayList<>();
                                while (matcher.find()) {
                                    arrayList.add(matcher.group().replaceAll("\\(|\\)|\\[|\\]", ""));
                                }
                                if ("请求对战".equals(arrayList.get(2)) && "game".equals(arrayList.get(1))) {
                                    String str = JOptionPane.showInputDialog(null, "是否同意与" + arrayList.get(0) + "对战?(Y/N)", "申请对战", JOptionPane.PLAIN_MESSAGE);
                                    if ("Y".equalsIgnoreCase(str)) {
                                        if (jTXT != null) {
                                            jTXT.append("Server:您已经接受对战,稍等一会,开始游戏!" + "\n");
                                        } else {
                                            System.out.println("同意对战!"+user.getUID());
                                        }
                                        sendGameCommand("newGame={write=[" + arrayList.get(0) + "],black=[" + user.getUID() + "]}");
                                        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                                        Object obj = null;
                                        try {
                                            obj = ois.readObject();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        if (obj instanceof GameRoomUser) {
                                            gameRoomUser = (GameRoomUser) obj;
                                            System.out.println(gameRoomUser);
                                        }
                                    } else {
                                        if (jTXT != null) {
                                            jTXT.append("Server:您拒绝了对战!" + "\n");
                                        } else {
                                            System.out.println("拒绝对战!");
                                        }
                                        sendGameCommand("errorGame={user=[" + arrayList.get(0) + "],send=[" + user.getUID() + "拒绝了对战]}");
                                    }
                                }

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
        };
        new Thread(run).start();

    }

    public void sendGameCommand(String command) throws IOException {//写入命令--命令:客户端! (命令指令)
        dos.writeUTF("command-game:" + command + ";");//写入命令--命令:客户端!
        // 开启游戏对局 newGame={write=[(userUID2)],black=[(userUID1)]}
        // 拒绝对战 errorGame={user=[(userUID1)],send=[(userUID2)拒绝了对战]}
        // 请求对战 attackUser:[(UID),(attackUserUID)]
    }

    public JTextArea getjTXT() {
        return jTXT;
    }

    public void setjTXT(JTextArea jTXT) {
        this.jTXT = jTXT;
    }
}

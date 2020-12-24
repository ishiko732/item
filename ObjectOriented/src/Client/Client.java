package Client;

import GUI.ClientGUI;
import Game.Core;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final User user;
    private boolean push;
    private RoomUser roomUser;
    private Core core;
    private static boolean attackUser;


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

    public void sendCommand(String command) throws IOException {//写入命令--命令:客户端! (命令指令)
        dos.writeUTF("command:Client!" + command + ";");//写入命令--命令:客户端!
        // 获取用户列表 getUserList
        // 请求对战 attackUser:[(UID),(attackUserUID)]
    }

    public Map<String, String> getUserList() {
        HashMap<String, String> stringStringHashMap = null;
        try {
            sendCommand("getUserList");
            while (userMap == null) {
                Thread.sleep(1000);
            }
            stringStringHashMap = new HashMap<>(userMap);
            userMap = null;
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
        return stringStringHashMap;
    }

    public void applyAttack(String attackUserUID) {
//        RoomUser roomUser_auto=null;
        try {
            if (roomUser == null) {
                sendCommand("attackUser:[" + user.getUID() + "," + attackUserUID + "]");//command:Client!attackUser:[(UID),(attackUserUID)];
                while (roomUser == null) {
                    Thread.sleep(1000);
                }
//            roomUser_auto=roomUser;
//            roomUser=null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public User getUser() {
        return user;
    }

    public RoomUser getGameRoom(String my, String your) {
        try {
            if (roomUser == null) {
                sendCommand("attackUser:[" + my.replace(" ", "") + "," + your.replace(" ", "") + "]");//command:Client!attackUser:[(UID),(attackUserUID)];
                while (roomUser == null) {
                    Thread.sleep(1000);
                }
                System.out.println(roomUser);
                if (roomUser.getRoomID() == -1) {
                    roomUser = null;
                    if (jTXT != null) {
                        jTXT.append("Server:对方拒绝了对战!" + "\n");
                    } else {
                        System.out.println("拒绝对战!");
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return roomUser;

    }

    private String message;
    private JTextArea jTXT;
    private Map<String, String> userMap = null;

    private void clientThread() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                String regex = "\\[[^\\]]*\\]";//匹配中括号
                Pattern compile = Pattern.compile(regex);
                while (true) {
                    try {
                        Thread.sleep(100);
                        message = dis.readUTF();
                        System.out.println("(get)Client-" + user.getUID() + ": " + message);
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
                                    if (arrayList.get(0).equals(user.getUID())) {
                                        sendGameCommand("newGame={write=[" + arrayList.get(0) + "],black=[" + user.getUID() + "]}");
                                        if (jTXT != null) {
                                            jTXT.append("Server:你选择了自己,将进入单机模式!" + "\n");
                                        } else {
                                            System.out.println("Client(" + user.getUID() + ")您进入单机对战!");
                                        }
                                    } else {
                                        String str = JOptionPane.showInputDialog(null, "是否同意与" + arrayList.get(0) + "对战?(Y/N)", "申请对战", JOptionPane.PLAIN_MESSAGE);
                                        if ("Y".equalsIgnoreCase(str)) {
                                            if (jTXT != null) {
                                                jTXT.append("Server:您已经接受对战,稍等一会,开始游戏!" + "\n");
//                                                while(roomUser==null){
//                                                    Thread.sleep(1000);
//                                                }
//                                                setCore(new Core(19, 19,Client.this, roomUser.getRoomID()));
//                                                GameRoomUser gameRoomUser = new GameRoomUser(roomUser.getUser_write(), roomUser.getUser_black(), getCore(), roomUser.getRoomID());//将棋盘连起来
////                                                new RoomWindows(GateWindows.jtp, Client.this, uid, gameRoomUser);
                                            } else {
                                                System.out.println("Client(" + user.getUID() + ")您已经同意对战!");
                                            }
                                            sendGameCommand("newGame={write=[" + arrayList.get(0) + "],black=[" + user.getUID() + "]}");
                                        } else {
                                            if (jTXT != null) {
                                                jTXT.append("Server:您拒绝了对战!" + "\n");
                                            } else {
                                                System.out.println("拒绝对战!");
                                            }
                                            sendGameCommand("errorGame={write=[" + arrayList.get(0) + "],black=[" + user.getUID() + "]}");
                                        }
                                    }
                                }
                            } else if (message.indexOf("get-userList:") == 0) {
                                if (userMap == null) {
                                    userMap = new HashMap<>();
                                    synchronized (userMap) {
                                        String regex_list = "\\{[^\\]]*\\}";//匹配中括号
                                        Pattern compile_list = Pattern.compile(regex_list);
                                        Matcher matcher = compile_list.matcher(message);
                                        matcher.find();
                                        String[] userList = matcher.group().replaceAll("\\{|\\}", "").split(",");
                                        for (String s : userList) {
                                            String[] user_split = s.split("=");
                                            userMap.put(user_split[0], user_split[1]);
                                        }
                                    }
                                }
                            } else if (message.indexOf("gameRoom:") == 0) {
                                if (roomUser == null) {
                                    roomUser = transferRoomUser(message);
                                }
                            } else if (message.indexOf("command-game:game={var=") == 0) {
                                //game={var=[(while)],xy=[(x|y)],roomID=[(id)]}
                                Map<String, String> gameMap = transferGameMap(message);//{xy=(2,2), var=write, roomID=1}
                                int var = "white".equals(gameMap.get("var")) ? 1 : 2;
                                String[] xy = gameMap.get("xy").replaceAll("\\(|\\)", "").split(",");
                                int a = core.ChessIt(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]), var);
                                System.out.println("接收" + gameMap.get("var") + ":" + gameMap.get("xy") + "执行状态:" + a);
                                if (a == 1) {
                                    ClientGUI.getGameGui().repaint();
                                    JOptionPane.showMessageDialog(null, "白棋赢了", "恭喜", JOptionPane.DEFAULT_OPTION);
                                }
                                if (a == 2) {
                                    ClientGUI.getGameGui().repaint();
                                    JOptionPane.showMessageDialog(null, "黑棋赢了", "恭喜", JOptionPane.DEFAULT_OPTION);
                                }
                                if (a != -1) {
                                    try {
//                                        System.out.println("是否是攻击方:" + attackUser + "下方的计时器:" + ClientGUI.getGameGui().getPlayerTime_my().getFlag() + "上方的计时器:" + ClientGUI.getGameGui().getPlayerTime_your().getFlag());
                                        if ((var == 1 && attackUser) || (var == 2 && !attackUser)) {//如果是黑棋,同时是发起方  --下边是发起方
                                            if (ClientGUI.getGameGui().getPlayerTime_my() != null) {
                                                ClientGUI.getGameGui().getPlayerTime_your().stop_time();
                                                ClientGUI.getGameGui().getPlayerTime_my().reset_time();
                                                ClientGUI.getGameGui().getPlayerTime_my().start_time();
                                            }
                                        } else if ((var == 2 && attackUser) || (var == 1 && !attackUser)) {
                                            if (ClientGUI.getGameGui().getPlayerTime_your() != null) {
                                                ClientGUI.getGameGui().getPlayerTime_my().stop_time();
                                                ClientGUI.getGameGui().getPlayerTime_your().reset_time();
                                                ClientGUI.getGameGui().getPlayerTime_your().start_time();
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                    ClientGUI.getGameGui().repaint();
                                }
                                if (core.checkSum()) {
                                    Object[] options = new Object[]{"确认"};
                                    JOptionPane.showOptionDialog(null, "平局,可以开始新对局!", "平局", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION, null, options, options[0]);
                                }
                            } else if (message.indexOf("command-game:game={command=") == 0) {
                                //game={command=remake,roomID=id}
                                Map<String, String> map = transferGameCommand(message);
                                String command = map.get("command");
                                System.out.println(command);
                                if ("remake".equals(command)) {//重新开始
                                    core.Restart();
                                    ClientGUI.getGameGui().repaint();
                                } else if ("summation".equals(command)) {//求和
                                    Object[] options = {"确认", "取消"};
                                    int n = JOptionPane.showOptionDialog(null, "确认申请和棋?", "申请和棋", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    options = new Object[]{"确认"};
                                    if (n == 0) {
                                        core.Restart();
                                        ClientGUI.getGameGui().repaint();
                                        JOptionPane.showOptionDialog(null, "平局,开始新对局!", "和棋成功", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION, null, options, options[0]);
                                    } else if (n == 1) {
                                        JOptionPane.showOptionDialog(null, "和棋失败,进行对局", "和棋失败", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                                    }
                                } else if ("regret".equals(command)) {//悔棋
                                    core.RetChess();
                                    if (ClientGUI.getGameGui().getVar() == 1) {
                                        ClientGUI.getGameGui().setVar(2);
                                    } else if (ClientGUI.getGameGui().getVar() == 2) {
                                        ClientGUI.getGameGui().setVar(1);
                                    }
                                    ClientGUI.getGameGui().repaint();
                                } else if ("admit".equals(command)) {//认输
                                    Object[] options = {"确认", "取消"};
                                    String str = (ClientGUI.getGameGui().getVar() == 1) ? "白棋" : "黑棋";
                                    int n = JOptionPane.showOptionDialog(null, str + ":确认申请认输吗?", "申请认输", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                                    if (n == 0) {
                                        core.Restart();
                                        ClientGUI.getGameGui().repaint();
                                        options = new Object[]{"确认"};
                                        JOptionPane.showOptionDialog(null, str + "已经认输,开始新对局!", "确认认输", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION, null, options, options[0]);
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
        // 拒绝对战 errorGame=={write=[(userUID2)],black=[(userUID1)]}
        // 请求对战 attackUser:[(UID),(attackUserUID)]
        // 棋子信息 game={var=[(while)],xy=[(x|y)],roomID=[(id)]}
        // 重新开始 game={command=remake,roomID=id}
        // 求和 game={command=summation,roomID=id}
        // 悔棋 game={command=regret,roomID=id}
        // 认输 game={command=admit,roomID=id}

    }

    public JTextArea getjTXT() {
        return jTXT;
    }

    public void setjTXT(JTextArea jTXT) {
        this.jTXT = jTXT;
    }

    public static RoomUser transferRoomUser(String users) {
        RoomUser roomUser = new RoomUser();
        String regex = "\\{[^\\]]*\\}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(users);
        matcher.find();
        String group = matcher.group();
        String[] split = group.substring(1, group.length() - 1).split("},");
        for (int i = 0; i < split.length - 1; i++) {
            split[i] += "}";
            matcher = compile.matcher(split[i]);
            matcher.find();
            String[] user = matcher.group().replaceAll("\\{|\\}| ", "").split(",");
            User userMessage = new User();
            Map<String, String> userMap = new HashMap<>();
            for (String s : user) {
                String[] user_split = s.split("=");
                userMap.put(user_split[0], user_split[1].replace("\'", ""));
            }
            userMessage.setUID(userMap.get("UID"));
            userMessage.setPassword(userMap.get("password"));
            userMessage.setServerIP(userMap.get("serverIP"));
            userMessage.setServerPort(Integer.parseInt(userMap.get("serverPort")));
            if (i == 0) {
                roomUser.setUser_black(userMessage);
            } else {
                roomUser.setUser_write(userMessage);
            }
        }
        roomUser.setRoomID(Integer.parseInt(split[2].replace(" roomID=", "")));
        return roomUser;
    }

    public Map<String, String> transferGameMap(String str) {
        String regex = "\\{[^\\]]*\\}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        matcher.find();
        String[] messageGame = matcher.group().replaceAll("\\{|\\}", "").split(",");
        Map<String, String> gameMap = new HashMap<>();
        for (String s : messageGame) {
            String[] game_split = s.split("=");
            gameMap.put(game_split[0], game_split[1].replace("|", ","));
        }
        return gameMap;
    }

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }

    public RoomUser getRoomUser() {
        return roomUser;
    }

    public void setRoomUser(RoomUser roomUser) {
        this.roomUser = roomUser;
    }

    public static boolean isAttackUser() {
        return attackUser;
    }

    public static void setAttackUser(boolean attackUser) {
        Client.attackUser = attackUser;
    }

    public Map<String, String> transferGameCommand(String str) {
        //"command-game:game={command=remake,roomID=id};"
        String regex = "\\{[^\\]]*\\}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        matcher.find();
        String[] messageGame = matcher.group().replaceAll("\\{|\\}", "").split(",");
        Map<String, String> gameMap = new HashMap<>();
        for (String s : messageGame) {
            String[] game_split = s.split("=");
            gameMap.put(game_split[0], game_split[1].replace("|", ","));
        }
        return gameMap;
    }

}

package Client;

import GUI.ClientGUI;
import GUI.RoomWindows;
import Game.Core;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

import Game.Transfer;

public class Client {
    private DataOutputStream dos;
    private DataInputStream dis;
    private final User user;
    private boolean push;
    private RoomUser roomUser;
    private Core core;
    private static boolean attackUser;
    public static boolean sendUser;
    //客户端线程
    private String message;
    private JTextArea textArea;
    private Map<String, String> userMap = null;

    public Client(User user) {
        this.user = user;
        try {
            Socket client = new Socket(user.getServerIP(), user.getServerPort());
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

    public boolean isLogin() {
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
        clientThread();
    }

    public void sendCommand(String command, boolean game) throws IOException {//写入命令--命令:客户端! (命令指令)
        if (game) {
            dos.writeUTF("command-game:" + command + ";");//写入命令--命令:客户端!
            // 开启游戏对局 newGame={write=[(userUID2)],black=[(userUID1)]}
            // 拒绝对战 errorGame=={write=[(userUID2)],black=[(userUID1)]}
            // 请求对战 attackUser:[(UID),(attackUserUID)]
            // 棋子信息 game={var=[(write)],xy=[(x|y)],roomID=[(id)]}
            // 重新开始 game={command=remake,roomID=id,(isLogic=1)}
            // 求和 game={command=summation,roomID=id,(isLogic=1)}
            // 悔棋 game={command=regret,roomID=id,var=write,(isLogic=1)}
            // 认输 game={command=admit,roomID=id}
            // 退出游戏 game={command=exit,roomID=id}
        } else {
            dos.writeUTF("command:Client!" + command + ";");//写入命令--命令:客户端!
            // 获取用户列表 getUserList
            // 请求对战 attackUser:[(UID),(attackUserUID)]
        }
    }

    public Map<String, String> getUserList() {
        HashMap<String, String> stringStringHashMap = null;
        try {
            sendCommand("getUserList", false);
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
                sendCommand("attackUser:[" + user.getUID() + "," + attackUserUID + "]", false);//command:Client!attackUser:[(UID),(attackUserUID)];
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
                sendCommand("attackUser:[" + my.replace(" ", "") + "," + your.replace(" ", "") + "]", false);//command:Client!attackUser:[(UID),(attackUserUID)];
                while (roomUser == null) {
                    Thread.sleep(1000);
                }
                System.out.println(roomUser);
                if (roomUser.getRoomID() == -1) {
                    roomUser = null;
                    if (textArea != null) {
                        textArea.append("Server:对方拒绝了对战!" + "\n");
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

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
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

    private void clientThread() {
        Runnable run = () -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    message = dis.readUTF();
//                    System.out.println("(get)Client-" + user.getUID() + ": " + message);
                    if (!"".equals(message)) {
                        if (message.indexOf("get-chat[") == 0) {//接受到信息
                            // "get-chat["+chatMessage[0]+"],send=["+chatMessage[1]+"];"
                            ArrayList<String> arrayList = Transfer.chat(message);
                            if (textArea != null) {
                                textArea.append(arrayList.get(0) + ":" + arrayList.get(1) + "\n");
                            } else {
                                System.out.println("Client-" + user.getUID() + ":来自" + arrayList.get(0) + "客户端的消息:" + arrayList.get(1));
                            }
                        } else if ("sendMessage:(Yes)".equals(message)) {
                            System.out.println("Client-" + user.getUID() + ":sendMessage=" + message);
                        } else if (message.indexOf("get-wait[") == 0) {//等待命令
                            String[] waitMessage = Transfer.transferWait(message);
                            if (waitMessage.length != 3) {
                                System.err.println("Client-" + user.getUID() + ":" + "错误的等待信息");
                            }
                        } else if (message.indexOf("get-attack[") == 0) {
                            // "get-attack[" + userUID[0] + "],command=[game],send=[请求对战];";
                            ArrayList<String> arrayList = Transfer.getAttack(message);
                            if ("请求对战".equals(arrayList.get(2)) && "game".equals(arrayList.get(1))) {
                                if (arrayList.get(0).equals(user.getUID())) {
                                    sendCommand("newGame={write=[" + arrayList.get(0) + "],black=[" + user.getUID() + "]}", true);
//                                    JOptionPane.showMessageDialog(this, "Server:你选择了自己,将进入单机模式!");
                                    if (textArea != null) {
                                        textArea.append("Server:你选择了自己,将进入单机模式!" + "\n");
                                    } else {
                                        System.out.println("Client(" + user.getUID() + ")您进入单机对战!");
                                    }
                                } else {
                                    Object[] options = {"同意", "拒绝"};
                                    int n = JOptionPane.showOptionDialog(null, "是否同意与" + arrayList.get(0) + "对战?", "申请对战(" + user.getUID() + ")", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (n == 0) {
                                        if (textArea != null) {
                                            textArea.append("Server:您已经接受对战,稍等一会,开始游戏!" + "\n");
                                        } else {
                                            System.out.println("Client(" + user.getUID() + ")您已经同意对战!");
                                        }
                                        sendCommand("newGame={write=[" + arrayList.get(0) + "],black=[" + user.getUID() + "]}", true);
                                    } else {
                                        if (textArea != null) {
                                            textArea.append("Server:您拒绝了对战!" + "\n");
                                        } else {
                                            System.out.println("拒绝对战!");
                                        }
                                        sendCommand("errorGame={write=[" + arrayList.get(0) + "],black=[" + user.getUID() + "]}", true);
                                    }
                                }
                            }
                        } else if (message.indexOf("get-userList:") == 0) {
                            if (userMap == null) {
                                userMap = Transfer.transferUserMap(message);
                            }
                        } else if (message.indexOf("gameRoom:") == 0) {
                            if (roomUser == null) {
                                roomUser = Transfer.roomUser(message);
                            }
                        } else if (message.indexOf("command-game:game={var=") == 0) {
                            //game={var=[(while)],xy=[(x|y)],roomID=[(id)]}
                            Map<String, String> gameMap = Transfer.gameMap(message);//{xy=(2,2), var=write, roomID=1}
                            int var = "white".equals(gameMap.get("var")) ? 1 : 2;
                            String[] xy = gameMap.get("xy").replaceAll("[()]", "").split(",");
                            int a = core.ChessIt(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]), var);
                            System.out.println("接收" + gameMap.get("var") + ":" + gameMap.get("xy") + "执行状态:" + a);
                            if (a == 1) {
                                ClientGUI.getGameGui().repaint();
                                String color = attackUser ? ":你输了" : ":你赢了";
                                JOptionPane.showMessageDialog(null, user.getUID() + color);
                                ClientGUI.getGameGui().getPlayerTime_my().stop_time();
                                ClientGUI.getGameGui().getPlayerTime_your().stop_time();
                            }
                            if (a == 2) {
                                ClientGUI.getGameGui().repaint();
                                String color = attackUser ? ":你赢了" : ":你输了";
                                JOptionPane.showMessageDialog(null, user.getUID() + color);
                                ClientGUI.getGameGui().getPlayerTime_my().stop_time();
                                ClientGUI.getGameGui().getPlayerTime_your().stop_time();
                            }
                            if (a == 0) {
                                try {
//                                        System.out.println("是否是攻击方:" + attackUser + "下方的计时器:" + ClientGUI.getGameGui().getPlayerTime_my().getFlag() + "上方的计时器:" + ClientGUI.getGameGui().getPlayerTime_your().getFlag());
                                    if ((var == 1 && attackUser) || (var == 2 && !attackUser)) {//如果是黑棋,同时是发起方  --下边是发起方
                                        if (ClientGUI.getGameGui().getPlayerTime_my() != null) {
                                            ClientGUI.getGameGui().getPlayerTime_your().stop_time();
                                            ClientGUI.getGameGui().getPlayerTime_my().reset_time();
                                            ClientGUI.getGameGui().getPlayerTime_my().start_time();
                                        }
                                    } else {
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
                                JOptionPane.showMessageDialog(null, "平局,可以开始新对局!");
                            }
                        } else if (message.indexOf("command-game:game={command=") == 0) {
                            //game={command=remake,roomID=id}
                            Map<String, String> map = Transfer.gameCommand(message);
                            String command = map.get("command");
                            System.out.println("gameCommand:" + command);
                            if ("remake".equals(command)) {//重新开始
                                if (!map.containsKey("isLogic") && sendUser) {
                                    Object[] options = {"确认", "取消"};
                                    int n = JOptionPane.showOptionDialog(null, "是否同意重新开始?", "重新开始", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (n == 0) {
                                        sendCommand("game={command=remake,roomID=" + map.get("roomID") + ",isLogic=1}", true);
                                    } else {
                                        sendCommand("game={command=remake,roomID=" + map.get("roomID") + ",isLogic=0}", true);
                                    }
                                } else if (map.containsKey("isLogic")) {
                                    int isLogic = "1".equals(map.get("isLogic")) ? 1 : 0;
                                    if (isLogic == 1) {
                                        core.Restart();
                                        ClientGUI.getGameGui().getPlayerTime_my().stop_time();
                                        ClientGUI.getGameGui().getPlayerTime_your().stop_time();
                                        ClientGUI.getGameGui().getPlayerTime_my().reset_time();
                                        ClientGUI.getGameGui().getPlayerTime_your().reset_time();
                                        if (Client.isAttackUser()) {
                                            ClientGUI.getGameGui().getPlayerTime_my().start_time();
                                        } else {
                                            ClientGUI.getGameGui().getPlayerTime_your().start_time();
                                        }
                                        ClientGUI.getGameGui().repaint();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "重新开始失败!");
                                    }
                                }
                            } else if ("summation".equals(command)) {//求和
                                if (!map.containsKey("isLogic") && !sendUser) {
                                    Object[] options = {"确认", "取消"};
                                    int n = JOptionPane.showOptionDialog(null, "确认申请和棋?", "申请和棋", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (n == 0) {
                                        sendCommand("game={command=summation,roomID=" + map.get("roomID") + ",isLogic=1}", true);
                                    } else {
                                        sendCommand("game={command=summation,roomID=" + map.get("roomID") + ",isLogic=0}", true);
                                    }
                                } else if (map.containsKey("isLogic")) {
                                    int isLogic = "1".equals(map.get("isLogic")) ? 1 : 0;
                                    if (isLogic == 1) {
                                        core.Restart();
                                        ClientGUI.getGameGui().getPlayerTime_my().stop_time();
                                        ClientGUI.getGameGui().getPlayerTime_your().stop_time();
                                        JOptionPane.showMessageDialog(null, "和棋成功,打成平局!新对局!");
                                        ClientGUI.getGameGui().repaint();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "和棋失败,进行对局!");
                                    }
                                }
                            } else if ("regret".equals(command)) {//悔棋
//                                    int var = "write".equals(map.get("var")) ? 1 : 2;
                                if (!map.containsKey("isLogic") && !sendUser) {//第一份信息是没有isLogic --发送同意悔棋请求
                                    Object[] options = {"同意", "拒绝"};
                                    int n = JOptionPane.showOptionDialog(null, "对方申请悔棋,是否同意?", "申请和棋", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (n == 0) {//同意
                                        String v = "write".equals(map.get("var")) ? "write" : "black";//攻击方为黑棋
                                        sendCommand("game={command=regret,roomID=" + map.get("roomID") + ",isLogic=1,var=" + v + "}", true);
                                    }
                                } else if (map.containsKey("isLogic")) {//第二份信息是有isLogic --收到请求情况
                                    int isLogic = "1".equals(map.get("isLogic")) ? 1 : 0;
                                    if (isLogic == 1) {
                                        core.RetChess();
                                        if (ClientGUI.getGameGui().getVar() == 1) {
                                            ClientGUI.getGameGui().setVar(2);
                                        } else if (ClientGUI.getGameGui().getVar() == 2) {
                                            ClientGUI.getGameGui().setVar(1);
                                        }
//                                            System.out.println("下方的计时器:" + ClientGUI.getGameGui().getPlayerTime_my().getFlag() + "上方的计时器:" + ClientGUI.getGameGui().getPlayerTime_your().getFlag());
                                        ClientGUI.getGameGui().repaint();
                                        if (ClientGUI.getGameGui().getPlayerTime_my() != null && ClientGUI.getGameGui().getPlayerTime_your() != null) {
                                            int myFlag = ClientGUI.getGameGui().getPlayerTime_my().getFlag();
                                            if (myFlag == 1 || myFlag == -1) {
                                                ClientGUI.getGameGui().getPlayerTime_my().stop_time();
                                                ClientGUI.getGameGui().getPlayerTime_your().reset_time();
                                                ClientGUI.getGameGui().getPlayerTime_your().start_time();
                                            } else {
                                                ClientGUI.getGameGui().getPlayerTime_your().stop_time();
                                                ClientGUI.getGameGui().getPlayerTime_my().reset_time();
                                                ClientGUI.getGameGui().getPlayerTime_my().start_time();
                                            }
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "悔棋失败!");
                                    }
                                }
                                Client.sendUser = false;//处理完信息以后 置未false
                            } else if ("admit".equals(command)) {//认输
                                if (!sendUser) {
                                    JOptionPane.showMessageDialog(null, "对方已经认输,游戏结束!");
                                    RoomWindows.roomExit();
                                }
                                core.Restart();
                                ClientGUI.getGameGui().repaint();
                                Client.sendUser = false;//处理完信息以后 置未false
//                                roomUser=null;
//                                GateWindows.newListener();
                            } else if ("exit".equals(command)) {//退出游戏
                                RoomWindows.roomExit();
//                                roomUser=null;
//                                GateWindows.newListener();
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
        };
        new Thread(run).start();

    }
}

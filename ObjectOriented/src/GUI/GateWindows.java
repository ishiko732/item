package GUI;

import Client.Client;
import Game.Core;
import Client.RoomUser;
import Game.GameRoomUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class GateWindows extends JFrame {
    private final ClientGUI gui;
    //GUI
    JSplitPane game_gate_jsp, jsp2;
    public static JTabbedPane jtp = new JTabbedPane();//选项卡
    JTabbedPane jtp1, jtp2;
    JLabel userPicture, userInfo, userImg;//User1 图片,UID,头像
    JPanel user_picture_message, userMessage, serverMessage, rightBorder;
//    public static JPanel room1 = new JPanel(new BorderLayout());
    JButton button_flush = new JButton("刷新");
    JButton button_exit = new JButton("退出");
    JTextArea serverTextArea;

    public static JButton[] btnSeat = new JButton[30];
    public JLabel[] userName = new JLabel[30];

    //    private ArrayList<Integer> finalI_arrayList=new ArrayList<>();
    //END GUI
    private void init() {
        this.setTitle("Client-gate");

        //JSplitPane
        game_gate_jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//分割界面
        jsp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        //JTabbedPane
        jtp1 = new JTabbedPane();
        jtp2 = new JTabbedPane();

        //JPanel
        user_picture_message = new JPanel();
        userMessage = new JPanel();
        serverMessage = new JPanel();
        rightBorder = new JPanel();

        //User1
        userPicture = new JLabel(new ImageIcon("./res/img/boy1.gif"));
        userInfo = new JLabel(gui.getClient().getUser().getUID());
        userImg = new JLabel(new ImageIcon(gui.getClient().getUser().getPassword()));

        //operator JSP
        game_gate_jsp.setLeftComponent(jsp2);
        game_gate_jsp.setDividerSize(2);  //设置分隔条大小
        jsp2.setDividerSize(2);
        jsp2.setDividerLocation(300); //设置左边分割线离上界面的距离
        game_gate_jsp.setDividerLocation(200); //设置分割线离左界面的距离
        //operator JTP
        jtp.addTab("游戏大厅", game_gate_jsp);
        jtp1.addTab("用户信息", user_picture_message);
        jtp2.addTab("服务器信息", serverMessage);

        //布局
        //左上界面
        //图片
        user_picture_message.setLayout(new BorderLayout());
        user_picture_message.add(userPicture, "Center");
        //用户信息
        userMessage.setLayout(new FlowLayout()); //南部用流式布局
        userMessage.add(userImg);
        userMessage.add(userInfo);
        user_picture_message.add(userMessage, "South");
        jsp2.setLeftComponent(jtp1);
        //左下界面
        serverMessage.setLayout(new BorderLayout());
        serverTextArea = new JTextArea();
//        gui.getClient().setJTextArea(serverTextArea);
        JTextField jtf = new JTextField();
        JButton send = new JButton("发送");
        JButton clear = new JButton("清空");
        JPanel South3 = new JPanel(new BorderLayout()); //左下的南部
        South3.add(jtf, "Center");
        JPanel rightJP = new JPanel(); //左下的南部
        rightJP.add(send);
        rightJP.add(clear);
        South3.add(rightJP, "East");
        serverMessage.add(serverTextArea, "Center");
        serverMessage.add(South3, "South");

        ActionListener send_clear_listener = e -> {
            if (e.getSource() == send) {
                String UID = gui.getClient().getUser().getUID();
                serverTextArea.append(UID + ":" + jtf.getText() + "\n");
                gui.getClient().sendMessage(jtf.getText(), "Server");
            } else if (e.getSource() == clear) {
                serverTextArea.setText("");
            }
        };
        send.addActionListener(send_clear_listener);
        clear.addActionListener(send_clear_listener);

        jsp2.setRightComponent(jtp2);

        //右边界面
        button_flush = new JButton("刷新");
        button_exit = new JButton("退出");
        rightBorder.setLayout(new BorderLayout());
        //右北
        JLabel title = new JLabel("<<<< 五子棋游戏 >>>>");
        JPanel jp = new JPanel();
        JPanel rightNorth = new JPanel();
        rightNorth.setLayout(new BorderLayout()); //右界面北部
        rightNorth.add(title, "West");
        rightNorth.add(jp, "East");
        jp.setLayout(new FlowLayout());
        jp.add(button_flush);
        jp.add(button_exit);
        //右中
        JPanel rightCenter = new JPanel();
        rightCenter.setLayout(null);//设置空布局
        rightCenter.setBackground(new Color(81, 113, 158));//设置背景颜色
        //设置桌子号
        Font f = new Font("宋体", Font.PLAIN, 16);  //设置字体大小
        JLabel[] seatNumber = new JLabel[15];
        int q = 0, p = 0;
        for (int i = 0; i < 15; i++) {
            seatNumber[i] = new JLabel("- " + (i + 1) + " -");
            seatNumber[i].setFont(f);
            seatNumber[i].setBounds(80 + q * 193, 60 + p * 150, 100, 100);
            rightCenter.add(seatNumber[i]);
            q++;
            if ((i + 1) % 3 == 0) {
                q = 0;
                p++;
            }
        }

        //添加座位按钮
        int a = 0, b = 0;
        for (int i = 0; i < btnSeat.length; i += 2) {
            btnSeat[i] = new JButton(new ImageIcon("./res/img/none.gif"));
            btnSeat[i].setBounds(35 + a * 193, 35 + b * 150, 40, 45);
            userName[i] = new JLabel("", JLabel.CENTER);
            userName[i].setBounds(33 + a * 193, 70 + b * 150, 40, 45);
            rightCenter.add(btnSeat[i]);
            rightCenter.add(userName[i]);
            a++;
            if ((i + 2) % 6 == 0) {
                a = 0;
                b++;
            }
        }
        a = 0;
        b = 0;
        for (int i = 1; i < btnSeat.length; i += 2) {
            btnSeat[i] = new JButton(new ImageIcon("./res/img/none.gif"));
            btnSeat[i].setBounds(128 + a * 193, 35 + b * 150, 40, 45);
            userName[i] = new JLabel("", JLabel.CENTER);
            userName[i].setBounds(128 + a * 193, 70 + b * 150, 40, 45);
            rightCenter.add(btnSeat[i]);
            rightCenter.add(userName[i]);
            a++;
            if ((i + 1) % 6 == 0) {
                a = 0;
                b++;
            }
        }
        //添加桌子
        int j = 0, k = 0;
        JLabel[] table = new JLabel[15];
        for (int i = 0; i < table.length; i++) {
            //noinspection SpellCheckingInspection
            table[i] = new JLabel(new ImageIcon("./res/img/xqnone.gif"));
            table[i].setBounds(75 + j * 193, 30 + k * 150, 53, 53);
            rightCenter.add(table[i]);
            j++;
            if ((i + 1) % 3 == 0) {
                j = 0;
                k++;
            }
        }


        rightBorder.add(rightNorth, "North");
        rightBorder.add(rightCenter, "Center");
        game_gate_jsp.setRightComponent(rightBorder);
        //监听
        button_flush.addActionListener((ActionEvent e) -> {
            if (e.getSource() == button_flush) {
                flushList();
            }
        });
        button_exit.addActionListener((ActionEvent) -> {//退出按钮
            System.exit(0);
        });
        //创建房间
        for (int i = 0; i < btnSeat.length; i++) {
            int finalI = i;
            btnSeat[i].addActionListener(new ActionListener() {  //设置“座位”按钮
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnSeat[finalI].setIcon(
                            new ImageIcon(gui.getClient().getUser().getPassword()));
                    btnSeat[finalI].setSize(38, 38);
                    uid.put(finalI, gui.getClient().getUser().getUID());//用户信息
//                    System.out.println(uid);
                    String user1 = uid.get(finalI);
                    String user2 = finalI != 0 && finalI % 2 == 0 ? uid.get(finalI + 1) : uid.get(finalI - 1);
                    RoomUser gameUser = gui.getClient().getGameRoom(user1, user2);
                    serverTextArea.append("Server:" + gui.getClient().getUser().getUID() + "请等待信息\n");
                    synchronized (this) {
                        Client.setAttackUser(true);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
//                    gui.getClient().setAttackUser(true);
//                    gui.getClient().setCore(new Core(19, 19,gui.getClient(),gameUser.getRoomID()));
//                    GameRoomUser gameRoomUser = new GameRoomUser(gameUser.getUser_write(), gameUser.getUser_black(), gui.getClient().getCore(), gameUser.getRoomID());//将棋盘连起来
//                    new RoomWindows(jtp, gui.getClient(), uid, gameRoomUser);

                }

            });
        }
        jFrame_operator();
        newListener();
    }

    private void flushList() {
        uid.clear();
        int t = 0;
        Map<String, String> seat = gui.getClient().getUserList();
//        System.out.println(seat);
//        for (int i = 0; i < btnLabel.length; i++) {
//            btnLabel[i].setText("");
//        }
        for (String name : seat.keySet()) {
            btnSeat[t].setIcon(new ImageIcon(seat.get(name)));
            userName[t].setText(name);
//            btnLabel[t].setText(name);
            uid.put(t, name);
            t += 2;
        }

    }

    public void jFrame_operator() {
        //JFrame 基本操作
        Dimension dim = this.getToolkit().getScreenSize();//获取屏幕大小
        this.setBounds(dim.width / 2 - 450, dim.height / 2 - 450, 900, 900);//设置窗口大小，width和height是取屏幕宽度和高度
        this.add(jtp);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    Map<Integer, String> uid = new HashMap<>();

    public void newListener() {
        new Thread(() -> {
//            flushList();
            while (gui.getClient().getRoomUser() == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (gui.getClient().getRoomUser().getRoomID() == -1) {
                gui.getClient().setRoomUser(null);
            } else {
                gui.getClient().setCore(new Core(19, 19, gui.getClient(), gui.getClient().getRoomUser().getRoomID()));
                GameRoomUser gameRoomUser;
                synchronized (this) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Client.isAttackUser()) {
                        gameRoomUser = new GameRoomUser(gui.getClient().getRoomUser().getUser_write(), gui.getClient().getRoomUser().getUser_black(), gui.getClient().getCore(), gui.getClient().getRoomUser().getRoomID());//将棋盘连起来
                    } else {
                        gameRoomUser = new GameRoomUser(gui.getClient().getRoomUser().getUser_black(), gui.getClient().getRoomUser().getUser_write(), gui.getClient().getCore(), gui.getClient().getRoomUser().getRoomID());//将棋盘连起来
                    }
                }
                jtp.addTab("五子棋游戏房间 " + gui.getClient().getRoomUser().getRoomID(), new ImageIcon(gui.getClient().getUser().getPassword()), RoomWindows.jsp1);
                jtp.setSelectedIndex(1);
                RoomWindows roomWindows = new RoomWindows(jtp, gui.getClient(), uid, gameRoomUser);
                gui.getClient().setTextArea(roomWindows.getChatMessage());//将聊天转移到游戏窗口
            }
        }).start();
    }

    public GateWindows(ClientGUI gui) {
        this.gui = gui;
        init();
        gui.getClient().messageListener();
        gui.getClient().setTextArea(serverTextArea);
        flushList();
        //system.out -> Text
//        PrintStream ps = new PrintStream(System.out) {
//            @Override
//            public void println(String x) {
//                serverTextArea.append(x + "\n");
//            }
//        };
//        System.setOut(ps);
    }


}

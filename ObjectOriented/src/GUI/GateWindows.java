package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

public class GateWindows extends JFrame {
    private ClientGUI gui;
    //GUI
    JSplitPane game_gate_jsp, jsp2;
    public static JTabbedPane jtp = new JTabbedPane();//选项卡
    JTabbedPane jtp1, jtp2;
    JLabel userPicture, userInfo, userImg;//User1 图片,UID,头像
    JPanel user_picture_message, userMessage, serverMessage, rightBorder;
    public static JPanel room1 = new JPanel(new BorderLayout());
    JButton button_join = new JButton("自动进入");
    JButton button_exit = new JButton("退出");
    JTextArea serverTextArea;

    public static JButton[] btnseat = new JButton[30];

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

        ActionListener send_clear_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == send) {
                    String UID=gui.getClient().getUser().getUID();
                    serverTextArea.append(UID+":"+jtf.getText() + "\n");
                    gui.getClient().sendMessage(jtf.getText(), "Server");
                } else if (e.getSource() == clear) {
                    serverTextArea.setText("");
                }
            }
        };
        send.addActionListener(send_clear_listener);
        clear.addActionListener(send_clear_listener);

        //system.out -> Text
        PrintStream ps = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                serverTextArea.append(x + "\n");
            }
        };
        System.setOut(ps);
        jsp2.setRightComponent(jtp2);

        //右边界面
//        button_join = new JButton("自动进入");
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
        jp.add(button_join);
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
        for (int i = 0; i < btnseat.length; i += 2) {
            btnseat[i] = new JButton(new ImageIcon("./res/img/none.gif"));
            btnseat[i].setBounds(35 + a * 193, 35 + b * 150, 40, 45);
            rightCenter.add(btnseat[i]);
            a++;
            if ((i + 2) % 6 == 0) {
                a = 0;
                b++;
            }
        }
        a = 0;
        b = 0;
        for (int i = 1; i < btnseat.length; i += 2) {
            btnseat[i] = new JButton(new ImageIcon("./res/img/none.gif"));
            btnseat[i].setBounds(128 + a * 193, 35 + b * 150, 40, 45);
            rightCenter.add(btnseat[i]);
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
        button_exit.addActionListener((ActionEvent) -> {//退出按钮
            System.exit(0);
        });
        //创建房间
        for (int i = 0; i < btnseat.length; i++) {
            int finalI = i;
            btnseat[i].addActionListener(new ActionListener() {  //设置“座位”按钮
                @Override
                public void actionPerformed(ActionEvent e) {
                    new room(jtp);
                    jtp.addTab("五子棋游戏房间 " + (finalI / 2 + 1), new ImageIcon(gui.getClient().getUser().getPassword()), room.jsp1);
                    btnseat[finalI].setIcon(
                            new ImageIcon(gui.getClient().getUser().getPassword()));
                    jtp.setSelectedIndex(1);
                }
            });
        }
        //JFrame 基本操作
        Dimension dim = this.getToolkit().getScreenSize();//获取屏幕大小
        this.setBounds(dim.width / 2 - 450, dim.height / 2 - 450, 900, 900);//设置窗口大小，width和height是取屏幕宽度和高度
        this.add(jtp);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public GateWindows(ClientGUI gui) {
        this.gui = gui;
        init();
    }

}

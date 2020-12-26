package GUI;


import Game.Core;
import Game.PlayerTime;
import Client.Client;
import Client.RoomUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;


@SuppressWarnings("FieldMayBeFinal")
public class RoomWindows extends JPanel implements ActionListener {//由于申请对战的人是黑棋 所以需要再修改
    //分割界面
    public static JSplitPane jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JSplitPane jsp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JSplitPane jsp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    //选项卡
    JTabbedPane jtp;
    JTabbedPane yourself = new JTabbedPane();
    JTabbedPane myself = new JTabbedPane();
    JTabbedPane jtp3 = new JTabbedPane();

    JLabel UserName_your;
    JLabel UserName_my;
    JLabel UserImg_your;
    JLabel UserImg_my;
    JLabel time1 = new JLabel("本步剩余时间：");
    JLabel time2 = new JLabel("本步剩余时间：");
    JLabel title = new JLabel("<<<< 五子棋游戏 房间 >>>>");

    JButton send = new JButton("发送");
    JButton exit = new JButton("退出");
    JButton restart = new JButton("重新开始");
    JButton summation = new JButton("求和");
    JButton admit = new JButton("认输");
    JButton regret = new JButton("悔棋");

    JPanel One = new JPanel();
    JPanel Two = new JPanel();
    JPanel Three = new JPanel();
    JPanel Four = new JPanel();

    JTextArea chatMessage;
    JTextField sendText_JFiled;

    private Core core;
    private GameGUI gobang;
    private Map<Integer, String> roomMessage;
    private Client client;
    private RoomUser gameRoom;

    public RoomWindows(JTabbedPane jtp, Client client, Map<Integer, String> roomMessage, RoomUser gameRoom) {
        this.gameRoom = gameRoom;
        this.core = client.getCore();
        this.jtp = jtp;
        this.roomMessage = roomMessage;
        this.client = client;
        jsp1.setLeftComponent(jsp2);
        jsp2.setRightComponent(jsp3);
        //设置分隔条大小
        jsp1.setDividerSize(2);
        jsp2.setDividerSize(2);
        jsp3.setDividerSize(2);
        //设置分隔条位置
        jsp1.setDividerLocation(200);
        jsp2.setDividerLocation(200);
        jsp3.setDividerLocation(200);
        //设置选项卡名字
        yourself.addTab("对手", One);
        myself.addTab("自己", Two);
        jtp3.addTab("聊天", Three);
        //第一个界面
        One.setLayout(new BorderLayout());//设置边框式布局
        JPanel North1 = new JPanel(); //One界面的北部
        PlayerTime playerTime_your;
        PlayerTime playerTime_my;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Client.isAttackUser());
//        if(Client.isAttackUser()){//攻击方是自己
        UserImg_my = new JLabel(new ImageIcon(gameRoom.getUser_black().getPassword()));
        UserName_my = new JLabel(gameRoom.getUser_black().getUID());
        UserImg_your = new JLabel(new ImageIcon(gameRoom.getUser_write().getPassword()));
        UserName_your = new JLabel(gameRoom.getUser_write().getUID());
//        }else{
//            UserImg_my = new JLabel(new ImageIcon(gameRoom.getUser_write().getPassword()));
//            UserName_my = new JLabel(gameRoom.getUser_write().getUID());
//
//            UserImg_your = new JLabel(new ImageIcon(gameRoom.getUser_black().getPassword()));
//            UserName_your = new JLabel(gameRoom.getUser_black().getUID());
//        }
        playerTime_my = new PlayerTime(true);
        playerTime_your = new PlayerTime(true);

        North1.add(UserImg_your);
        North1.add(UserName_your);
        One.add(North1, "North");
        One.add(time1, "West");
        //玩家1时间--他人
        playerTime_your.setBounds(100, 500, 200, 100);
        playerTime_your.setOpaque(false);
        One.add(playerTime_your, "East");
        jsp2.setLeftComponent(yourself);

        //第二个界面
        Two.setLayout(new BorderLayout());//设置边框式布局
        JPanel North2 = new JPanel(); //One界面的北部
//        System.out.println(gameRoom);
        North2.add(UserImg_my);
        North2.add(UserName_my);
        Two.add(North2, "North");
        //玩家2时间--自己
        playerTime_my.setBounds(100, 500, 200, 100);
        playerTime_my.setOpaque(false);
        Two.add(time2, "West");
        Two.add(playerTime_my, "East");
        //玩家2时间

        jsp3.setLeftComponent(myself);

        //第三个界面
        Three.setLayout(new BorderLayout());
        JPanel South3 = new JPanel(new BorderLayout()); //Three界面南部
        chatMessage = new JTextArea();
        sendText_JFiled = new JTextField();
        South3.add(sendText_JFiled, "Center");
        South3.add(send, "East");
        send.addActionListener(this);
        Three.add(chatMessage, "Center");
        Three.add(South3, "South");
        jsp3.setRightComponent(jtp3);

        //第四个界面
        Four.setLayout(new BorderLayout());
        JPanel South4 = new JPanel(); //第四个界面南部
        South4.setBackground(Color.gray);

        Four.add(South4, "South");
        Four.add(title, "North");
        jsp1.setRightComponent(Four);
        //棋盘
        if (gameRoom.getUser_black().getUID().equals(gameRoom.getUser_write().getUID())) {
            playerTime_your = playerTime_my = null;
        }
        gobang = new GameGUI(core, playerTime_your, playerTime_my);
        Four.add(gobang, "Center");
        South4.add(exit);
        South4.add(restart);
        South4.add(regret);
        South4.add(admit);
        South4.add(summation);
        //绑定监听
        exit.addActionListener(this);
        restart.addActionListener(this);
        regret.addActionListener(this);
        admit.addActionListener(this);
        summation.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            roomExit();
        } else if (e.getSource() == restart) {//重新开始
            try {
                if (ClientGUI.getGameGui().getPlayerTime_my() == ClientGUI.getGameGui().getPlayerTime_your()) {
                    core.Restart();
                    gobang.repaint();
                } else {
                    Client.sendUser = true;
                    client.sendCommand("game={command=remake,roomID=" + gameRoom.getRoomID() + "}", true);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (e.getSource() == summation) {//求和
            try {
                if (ClientGUI.getGameGui().getPlayerTime_my() == ClientGUI.getGameGui().getPlayerTime_your()) {
                    Object[] options = {"确认", "取消"};
                    int n = JOptionPane.showOptionDialog(this, "确认申请和棋?", "申请和棋", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (n == 0) {
                        core.Restart();
                        gobang.repaint();
                        JOptionPane.showMessageDialog(this, "平局,可以开始新对局!");
                    } else if (n == 1) {
                        JOptionPane.showMessageDialog(this, "和棋失败,进行对局");
                    }
                }
                Client.sendUser = true;
                client.sendCommand("game={command=summation,roomID=" + gameRoom.getRoomID() + "}", true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (e.getSource() == regret) {//悔棋
            try {
                if (ClientGUI.getGameGui().getPlayerTime_my() == ClientGUI.getGameGui().getPlayerTime_your()) {
                    core.RetChess();
                    gobang.repaint();
                    gobang.setVar(gobang.getVar() == 1?2:1);
                } else {
                    Client.sendUser = true;
                    String v = Client.isAttackUser() ? "black" : "write";//攻击方为黑棋
                    client.sendCommand("game={command=regret,roomID=" + gameRoom.getRoomID() + ",var=" + v + "}", true);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (e.getSource() == admit) {//认输
            Object[] options = {"确认", "取消"};
            String str = (gobang.getVar() == 1) ? "白棋" : "黑棋";
            int n = JOptionPane.showOptionDialog(null, str + ":确认申请认输吗?", "申请认输", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (n == 0) {
                try {
                    if (ClientGUI.getGameGui().getPlayerTime_my() != ClientGUI.getGameGui().getPlayerTime_your()){
                        Client.sendUser = true;
                        client.sendCommand("game={command=admit,roomID=" + gameRoom.getRoomID() + "}", true);
                    }
                    JOptionPane.showMessageDialog(this, str + "已经认输,开始新对局!");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                core.Restart();
                gobang.repaint();
                roomExit();
            }
        } else if (e.getSource() == send) {
            if (UserName_my.getText().equals(UserName_your.getText())) {//黑白方都是自己
                client.sendMessage(sendText_JFiled.getText(), "Server");
                chatMessage.append(client.getUser().getUID() + ":" + sendText_JFiled.getText() + "\n");
            } else {
                String color = Client.isAttackUser() ? "(黑):" : "(白):";
                client.sendMessage(sendText_JFiled.getText(), gameRoom.getUser_write().getUID());
                chatMessage.append(client.getUser().getUID() + color + sendText_JFiled.getText() + "\n");
            }
        }
    }

    private void roomExit() {
        jtp.remove(jsp1);
        String userUID = client.getUser().getUID();
        Iterator<Map.Entry<Integer, String>> it = roomMessage.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            if (userUID.equals(entry.getValue())) {
                GateWindows.btnSeat[entry.getKey()].setIcon(new ImageIcon("./src/gobang/img/none.gif"));
                GateWindows.userName[entry.getKey()].setText("");
                it.remove();
            }
        }
    }

    @SuppressWarnings("unused")
    public void setChatMessage(JTextArea chatMessage) {
        this.chatMessage = chatMessage;
    }

    public JTextArea getChatMessage() {
        return chatMessage;
    }
}
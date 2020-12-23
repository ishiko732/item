package GUI;


import Game.Core;
import Game.GameRoomUser;
import Game.PlayerTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;


public class RoomWindows extends JPanel implements ActionListener {//由于申请对战的人是黑棋 所以需要再修改
    //分割界面
    public static JSplitPane jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JSplitPane jsp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JSplitPane jsp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    //选项卡
    JTabbedPane jtp = new JTabbedPane();
    JTabbedPane yourself = new JTabbedPane();
    JTabbedPane myself = new JTabbedPane();
    JTabbedPane jtp3 = new JTabbedPane();

    JLabel blackUserName = new JLabel();
    JLabel writeUserName = new JLabel();
    JLabel blackUserImg = new JLabel();
    JLabel writeUserImg = new JLabel();
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

    JTextArea jta = null;
    JTextField jtf = null;

    private Core core;
    private GameGUI gobang;
    private Map<Integer, String> roomMessage;
    private ClientGUI gui;
    private GameRoomUser gameRoom;
    private int myBang;//0 黑白都是自己 1是白棋 2是黑棋

    public RoomWindows(JTabbedPane jtp, ClientGUI gui, Map<Integer, String> roomMessage, GameRoomUser gameRoom) {
        this.gameRoom = gameRoom;
        this.core = gameRoom.getCore();
        this.jtp = jtp;
        this.roomMessage = roomMessage;
        this.gui = gui;

        if(gui.getClient().getUser().getUID().equals(gameRoom.getUser_write().getUID())){//白棋
            myBang=1;
        }else if(gui.getClient().getUser().getUID().equals(gameRoom.getUser_black().getUID())){//黑棋
            myBang=2;
        }else{
            myBang=0;
        }
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
        blackUserImg = new JLabel(new ImageIcon(gameRoom.getUser_write().getPassword()));
        blackUserName = new JLabel(gameRoom.getUser_write().getUID());
        North1.add(blackUserImg);
        North1.add(blackUserName);
        One.add(North1, "North");
        One.add(time1, "West");
        //玩家1时间--他人
        PlayerTime playerTime1 = new PlayerTime(true);
        playerTime1.setBounds(100, 500, 200, 100);
        playerTime1.setOpaque(false);
        One.add(playerTime1, "East");
        jsp2.setLeftComponent(yourself);

        //第二个界面
        Two.setLayout(new BorderLayout());//设置边框式布局
        JPanel North2 = new JPanel(); //One界面的北部
        System.out.println(gameRoom);
        writeUserImg = new JLabel(new ImageIcon(gameRoom.getUser_black().getPassword()));
        writeUserName = new JLabel(gameRoom.getUser_black().getUID());
        North2.add(writeUserImg);
        North2.add(writeUserName);
        Two.add(North2, "North");
        //玩家2时间--自己
        PlayerTime playerTime2 = new PlayerTime(true);
        playerTime2.setBounds(100, 500, 200, 100);
        playerTime2.setOpaque(false);
        Two.add(time2, "West");
        Two.add(playerTime2, "East");
        //玩家2时间

        jsp3.setLeftComponent(myself);

        //第三个界面
        Three.setLayout(new BorderLayout());
        JPanel South3 = new JPanel(new BorderLayout()); //Three界面南部
        jta = new JTextArea();
        jtf = new JTextField();
        South3.add(jtf, "Center");
        South3.add(send, "East");
        send.addActionListener(this);
        Three.add(jta, "Center");
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
        gobang = new GameGUI(core, playerTime1, playerTime2);
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
            jtp.remove(jsp1);
            String userUID = gui.getClient().getUser().getUID();
            Iterator<Map.Entry<Integer, String>> it = roomMessage.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, String> entry = it.next();
                if (userUID.equals(entry.getValue())) {
                    GateWindows.btnseat[entry.getKey()].setIcon(new ImageIcon("./src/gobang/img/none.gif"));
                    it.remove();
                }
            }
        } else if (e.getSource() == restart) {//重新开始
            core.Restart();
            gobang.repaint();
        } else if (e.getSource() == summation) {//求和
            Object[] options = {"确认", "取消"};
            int n = JOptionPane.showOptionDialog(null, "确认申请和棋?", "申请和棋", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            options = new Object[]{"确认"};
            if (n == 0) {
                core.Restart();
                gobang.repaint();
                JOptionPane.showOptionDialog(null, "平局,开始新对局!", "和棋成功", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION, null, options, options[0]);
            } else if (n == 1) {
                JOptionPane.showOptionDialog(null, "和棋失败,进行对局", "和棋失败", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            }
        } else if (e.getSource() == regret) {//悔棋
            core.RetChess();
            if (gobang.getVar() == 1) {
                gobang.setVar(2);
            } else if (gobang.getVar() == 2) {
                gobang.setVar(1);
            }
            gobang.repaint();
        } else if (e.getSource() == admit) {//认输
            Object[] options = {"确认", "取消"};
            String str = (gobang.getVar() == 1) ? "白棋" : "黑棋";
            int n = JOptionPane.showOptionDialog(null, str + ":确认申请认输吗?", "申请认输", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (n == 0) {
                core.Restart();
                gobang.repaint();
                options = new Object[]{"确认"};
                JOptionPane.showOptionDialog(null, str + "已经认输,开始新对局!", "确认认输", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION, null, options, options[0]);
            }
        } else if (e.getSource() == send) {
            switch (myBang) {
                case 0:
                    gui.getClient().sendMessage(jtf.getText(), "Server");
                    break;
                case 1://白棋
                    gui.getClient().sendMessage(jtf.getText(), gameRoom.getUser_black().getUID());
                    break;
                case 2://黑棋
                    gui.getClient().sendMessage(jtf.getText(), gameRoom.getUser_write().getUID());
                    break;
            }
            jta.append(gui.getClient().getUser().getUID() + ":" + jtf.getText() + "\n");
        }


    }
}
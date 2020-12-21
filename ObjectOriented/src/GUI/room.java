package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class room extends JPanel {
    //分割界面
    public static JSplitPane jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JSplitPane jsp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JSplitPane jsp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    //选项卡
    JTabbedPane jtp = new JTabbedPane();
    JTabbedPane jtp1 = new JTabbedPane();
    JTabbedPane jtp2 = new JTabbedPane();
    JTabbedPane jtp3 = new JTabbedPane();

    JLabel userName1 = new JLabel();
    JLabel userName2 = new JLabel();
    JLabel userImg1 = new JLabel();
    JLabel userImg2 = new JLabel();
    JLabel time1 = new JLabel("本步剩余时间：");
    JLabel time2 = new JLabel("本步剩余时间：");
    JLabel title = new JLabel("<<<< 五子棋游戏 房间 >>>>");

    JButton send = new JButton("发送");
    JButton exit = new JButton("退出");
    JButton start = new JButton("开始");
    JButton qiuhe = new JButton("求和");
    JButton renshu = new JButton("认输");
    JButton huiqi = new JButton("悔棋");

    JPanel One = new JPanel();
    JPanel Two = new JPanel();
    JPanel Three = new JPanel();
    JPanel Four = new JPanel();

    JTextArea jta = null;
    JTextField jtf = null;


    public room(JTabbedPane jtp,ClientGUI gui) {
        this.jtp = jtp;

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
        jtp1.addTab("对手", One);
        jtp2.addTab("自己", Two);
        jtp3.addTab("聊天", Three);
        //第一个界面
        One.setLayout(new BorderLayout());//设置边框式布局
        JPanel North1 = new JPanel(); //One界面的北部
        userImg1 = new JLabel(new ImageIcon("./res/face/1-1.gif"));
        userName1 = new JLabel("name1");
        North1.add(userImg1);
        North1.add(userName1);
        One.add(North1, "North");
        One.add(time1, "Center");
        jsp2.setLeftComponent(jtp1);

        //第二个界面
        Two.setLayout(new BorderLayout());//设置边框式布局
        JPanel North2 = new JPanel(); //One界面的北部

        userImg2 = new JLabel(new ImageIcon(gui.getClient().getUser().getPassword()));
        userName2 = new JLabel(gui.getClient().getUser().getUID());
        North2.add(userImg2);
        North2.add(userName2);
        Two.add(North2, "North");
        Two.add(time2, "Center");
        jsp3.setLeftComponent(jtp2);

        //第三个界面
        Three.setLayout(new BorderLayout());
        JPanel South3 = new JPanel(new BorderLayout()); //Three界面南部
        jta = new JTextArea();
        jtf = new JTextField();
        South3.add(jtf, "Center");
        South3.add(send, "East");
        Three.add(jta, "Center");
        Three.add(South3, "South");
        jsp3.setRightComponent(jtp3);

        //第四个界面
        Four.setLayout(new BorderLayout());
        JPanel South4 = new JPanel(); //第四个界面南部
        South4.setBackground(Color.gray);
        South4.add(exit);
        South4.add(start);
        South4.add(huiqi);
        South4.add(renshu);
        South4.add(qiuhe);
        Four.add(South4, "South");
        Four.add(title, "North");
        jsp1.setRightComponent(Four);

        Four.add(new GameGUI(), "Center");

        exit.addActionListener(new ActionListener() {  //设置“退出”按钮
            @Override
            public void actionPerformed(ActionEvent e) {
                jtp.remove(jsp1);
                GateWindows.btnseat[0].setIcon(new ImageIcon("./src/gobang/img/none.gif"));
            }
        });
    }
}
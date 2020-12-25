package GUI;

import Client.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginWindows extends JFrame implements ActionListener {
    private final String ip;
    private final ClientGUI gui;
    private String text;
    //-----GUI
    JLabel personInfo, userInfo, serverInfo, imgInfo, userImg;
    JTextField severIP, userName;
    JButton[] btnImg = new JButton[86];
    //插入图像列表
    JButton btnConnect, btnRest, btnExit;

    JPanel pNorth = new JPanel();
    JPanel pCenter = new JPanel();
    JPanel pSouth = new JPanel();

    //End--GUI
    private void init() {
        {
            //JLabel
            personInfo = new JLabel("please user");//标签提示
            userInfo = new JLabel("name:");//标签提示 输入用户名字
            serverInfo = new JLabel("Server:");//标签提示 输入服务器IP
            imgInfo = new JLabel("imag:");//标签提示 选择头像
            userImg = new JLabel(new ImageIcon("./res/face/1-1.gif"), SwingConstants.LEFT);
            //JTextField
            severIP = new JTextField(ip);
            userName = new JTextField();
            //button
            btnConnect = new JButton("connection");//创建连接按钮
            btnRest = new JButton("reset");//创建重置按钮
            btnExit = new JButton("exit");//创建退出按钮
        }//组件创建
        //listener
        btnConnect.addActionListener(this);//监听器--连接
        btnRest.addActionListener(this);//监听器--重置
        btnExit.addActionListener(this);//监听器--退出
        {
            //Border
            this.setLayout(new BorderLayout());//设置整个窗口为边框布局
            //place
            //North北区 -- 区内用边界式布局
            pNorth.setLayout(new BorderLayout());
            pNorth.add(personInfo, "North");//定义北区内北部分
            //North西区
            JPanel pNorthWest = new JPanel();
            pNorthWest.setLayout(new GridLayout(3, 1)); //网格式布局
            pNorthWest.add(userInfo);//标签提示 输入用户名字
            pNorthWest.add(serverInfo);//标签提示 输入服务器IP
            pNorthWest.add(imgInfo);//标签提示 选择头像
            pNorth.add(pNorthWest, "West");//定义北区内西部分
            //North中区
            JPanel pNorthCenter = new JPanel();
            pNorthCenter.setLayout(new GridLayout(3, 1)); //网格式布局
            pNorthCenter.add(userName);//文本框 UserName
            pNorthCenter.add(severIP);//文本框 ServerIP
            pNorthCenter.add(userImg);//图片框 UserImg
            pNorth.add(pNorthCenter, "Center"); //定义北区内中部分
            //Center中区
            pCenter.setLayout(null);
            {
                //创建同一监听器
                ActionListener imgListener = e -> {
                    String msg = e.getActionCommand();
                    text = msg;
                    userImg.setIcon(new ImageIcon(msg));
                };
                int j = 0, k = 0;
                for (int i = 1; i <= 85; i++) {
                    btnImg[i] = new JButton(new ImageIcon("./res/face/" + i + "-1.gif"));
                    btnImg[i].setActionCommand("./res/face/" + i + "-1.gif");//设置此按钮激发的动作事件的命令名称
                    btnImg[i].addActionListener(imgListener);//设置监听器
//                    btnImg[i].setText("./res/face/" + i + "-1.gif");
                    btnImg[i].setBounds(65 + (k++) * 35, j * 35, 35, 35);
                    pCenter.add(btnImg[i], SwingConstants.CENTER);
                    if (i % 10 == 0) {
                        j += 1;
                        k = 0;
                    }
                }
            }//图片列表
            //South南区
            pSouth.setLayout(new FlowLayout()); //流式布局
            pSouth.add(btnConnect);
            pSouth.add(btnRest);
            pSouth.add(btnExit);
        }//窗口布局设置

        //JFrame 添加组件
        this.add(pNorth, "North");
        this.add(pCenter, "Center");
        this.add(pSouth, "South");
        //对窗口的基本操作
        Dimension dim = this.getToolkit().getScreenSize();//获取屏幕大小
        this.setTitle("goBang-login");//设置窗口标题
        this.setBounds(dim.width / 2 - 250, dim.height / 2 - 250, 500, 500);//设置窗口大小，width和height是取屏幕宽度和高度
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConnect) {
            String server_merge = severIP.getText();
            String[] server = server_merge.split(":");
            User user = new User(userName.getText(), text, server[0], Integer.parseInt(server[1]));
            gui.setClient(new Client(user));
            if (gui.getClient().isLogin()) {
                new GateWindows(gui);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "连接服务器失败!");
            }

        } else if (e.getSource() == btnRest) {
            userName.setText("");
            userImg.setIcon(new ImageIcon("./res/face/1-1.gif"));
            severIP.setText(ip);
        } else if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    public LoginWindows(ClientGUI GUI) {
        this.ip = "127.0.0.1:8089";
        this.gui = GUI;
        text = "./res/face/1-1.gif";
        try {//修改为windows画风格
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) {
            exception.printStackTrace();
        }
        init();
    }
}

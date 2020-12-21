package GUI;


import Game.Clock;
import Game.Core;
import Game.TimeClock;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.*;

public class GameGUI extends JPanel implements MouseListener {
    public Core core;
    private static final long serialVersionUID = 1L;
    private int var = 1;
    private int[] time=new int[1];
    private Thread th;
    private TimeClock timeclock;
    public GameGUI() {
        core = new Core(19, 19);
//        this.setSize(800, 650);
//        this.setLocation(800, 300);
        this.addMouseListener(this);
        time[0]=30;//为了传地址,用数组
        timeclock=new TimeClock(this,time);
        Thread th=new Thread(timeclock);
        th.start();
    }



    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        // 横
        for (int i = 1; i <= 19; i++)
            g.drawLine(30, 30 + i * 30, 570, 30 + i * 30);
        // 竖线
        for (int i = 0; i < 19; i++)
            g.drawLine(30 + i * 30, 60, 30 + i * 30, 600);

        int[][] board = core.getCore();//获取棋盘状态
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1)
                    g.drawOval(20 + i * 30, 50 + j * 30, 20, 20);
                if(board[i][j]==2)
                    g.fillOval(20+i*30, 50+j*30, 20, 20);
            }
        }
        String[] name={"认输","悔棋","申请和棋","重新开始","游戏状态"};
        for (int i=0;i<name.length;i++){
            g.drawRect(690,60+i*60, 70, 30);
            g.drawString(name[i],700,80+i*60);
        }
        String str=((this.var==1)?"白棋":"黑棋")+" 倒计时:";
        g.drawString(str + time[0]+"s", 650,440);

    }
    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getX() < 570 && e.getY() < 600) {
            int a = core.ChessIt(_CgetX(e.getX()), (_CgetY(e.getY())), var);
            this.repaint();
            if (a == 1) {
                JOptionPane.showMessageDialog(null,"白棋赢了", "恭喜", JOptionPane.DEFAULT_OPTION);
                timeclock.setExist(true);
            }
            if(a==2) {
                JOptionPane.showMessageDialog(null,"黑棋赢了", "恭喜", JOptionPane.DEFAULT_OPTION);
                timeclock.setExist(true);
            }
            if(a!=-1) {
                if(var==1) var=2;
                else if(var==2) var=1;
                time[0]=30;//为了传地址,用数组
                timeclock.setExist(true);
                timeclock=new TimeClock(this,time);
                Thread th=new Thread(timeclock);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                th.start();

            }
            if(core.checkSum()){
                Object[] options = new Object[]{"确认"};
                JOptionPane.showOptionDialog(null,"平局,可以开始新对局!","平局",JOptionPane.YES_NO_OPTION,JOptionPane.CLOSED_OPTION, null,options,options[0]);
            }
        }
        else if(e.getX()>690&&e.getX()<760&&e.getY()>60&&e.getY()<90) {//认输
            Object[] options = {"确认","取消"};
            String str=(this.var==1)?"白棋":"黑棋";
            int n = JOptionPane.showOptionDialog(null,str+":确认申请认输吗?","申请认输",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[1]);
            if(n==0) {
                this.core.Restart();
                this.repaint();
                timeclock.setExist(true);
                options = new Object[]{"确认"};
                JOptionPane.showOptionDialog(null,str+"已经认输,开始新对局!","确认认输",JOptionPane.YES_NO_OPTION,JOptionPane.CLOSED_OPTION, null,options,options[0]);
            }

        }
        else if(e.getX()>690&&e.getX()<760&&e.getY()>120&&e.getY()<150) {//悔棋
            core.RetChess();
            if(var==1) var=2;
            else if(var==2) var=1;
            time[0]=30;//为了传地址,用数组
            timeclock.setExist(true);
            timeclock=new TimeClock(this,time);
            Thread th=new Thread(timeclock);
            try {
                Thread.sleep(250);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            th.start();
//            this.repaint();
        }
        else if(e.getX()>690&&e.getX()<760&&e.getY()>180&&e.getY()<210) {//申请和棋
            Object[] options = {"确认","取消"};
            int n = JOptionPane.showOptionDialog(null,"确认申请和棋?","申请和棋",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[0]);
            options = new Object[]{"确认"};
            if(n==0){
                this.core.Restart();
                this.repaint();
                timeclock.setExist(true);
                JOptionPane.showOptionDialog(null,"平局,开始新对局!","和棋成功",JOptionPane.YES_NO_OPTION,JOptionPane.CLOSED_OPTION, null,options,options[0]);
            }else if(n==1){
                JOptionPane.showOptionDialog(null,"和棋失败,进行对局","和棋失败",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE, null,options,options[0]);
            }
        }
        else if(e.getX()>690&&e.getX()<760&&e.getY()>240&&e.getY()<270) {//重新开始
            core.Restart();
            this.repaint();
        }
        else if(e.getX()>690&&e.getX()<760&&e.getY()>300&&e.getY()<330){//游戏状态
            Object[] options = {"白先","黑先"};
            int n = JOptionPane.showOptionDialog(null,"白先还是黑先？","游戏设置",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[0]);
            if(n==0) this.var=1;
            if(n==1) this.var=2;
            this.core.Restart();
//            this.core.setCore();
//            System.out.println(this.core.checkSum());
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    private int _CgetX(int x) {
        x -= 30;
        if (x % 15 <= 7)
            return x / 30;
        else
            return x / 30 + 1;
    }

    private int _CgetY(int y) {
        y -= 60;
        if (y % 15 <= 7)
            return y / 30;
        else
            return y / 30 + 1;
    }
}
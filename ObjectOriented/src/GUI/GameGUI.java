package GUI;


import Game.Core;
import Game.PlayerTime;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.*;

public class GameGUI extends JPanel implements MouseListener {
    public Core core;
    private static final long serialVersionUID = 1L;
    private int var = 1;
    private PlayerTime playerTime1,playerTime2;
//    private ClientGUI gui;

    public GameGUI(Core core,PlayerTime playerTime1,PlayerTime playerTime2) {
        this.core = core;
        this.addMouseListener(this);
        this.playerTime1=playerTime1;
        this.playerTime2=playerTime2;
//        gui.getClient().sendCommand();
        playerTime1.start_time();
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
                if (board[i][j] == 2)
                    g.fillOval(20 + i * 30, 50 + j * 30, 20, 20);
            }
        }
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
                JOptionPane.showMessageDialog(null, "白棋赢了", "恭喜", JOptionPane.DEFAULT_OPTION);
            }
            if (a == 2) {
                JOptionPane.showMessageDialog(null, "黑棋赢了", "恭喜", JOptionPane.DEFAULT_OPTION);
            }
            if (a != -1) {
                if (var == 1) {
                    var = 2;
                    if(playerTime2!=null){
                        playerTime1.stop_time();
                        playerTime2.reset_time();
                        playerTime2.start_time();
                    }
                }
                else if (var == 2) {
                    var = 1;
                    if(playerTime1!=null){
                        playerTime2.stop_time();
                        playerTime1.reset_time();
                        playerTime1.start_time();
                    }
                }
                this.repaint();
            }
            if (core.checkSum()) {
                Object[] options = new Object[]{"确认"};
                JOptionPane.showOptionDialog(null, "平局,可以开始新对局!", "平局", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION, null, options, options[0]);
            }
        } else if (e.getX() > 590 && e.getX() < 660 && e.getY() > 300 && e.getY() < 330) {//游戏状态
            Object[] options = {"白先", "黑先"};
            int n = JOptionPane.showOptionDialog(null, "白先还是黑先？", "游戏设置", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n == 0) this.var = 1;
            if (n == 1) this.var = 2;
            this.core.Restart();
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

    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }
}
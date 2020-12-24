package GUI;


import Game.Core;
import Game.PlayerTime;
import Client.Client;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.swing.*;

public class GameGUI extends JPanel implements MouseListener {
    public Core core;
    private static final long serialVersionUID = 1L;
    private int var = 2;
    private PlayerTime playerTime_your, playerTime_my;

    public GameGUI(Core core, PlayerTime playerTime_your, PlayerTime playerTime_my) {
        this.core = core;
        ClientGUI.setGameGui(this);
        this.addMouseListener(this);
        this.playerTime_your = playerTime_your;
        this.playerTime_my = playerTime_my;
        if (Client.isAttackUser()) {
            playerTime_my.start_time();
        } else {
            playerTime_your.start_time();
        }
        repaint();
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
        if (e.getX() < 570 && e.getY() < 600) {
            //下棋方
            int flag = ClientGUI.getGameGui().getPlayerTime_my().getFlag();
            boolean jg1=Client.isAttackUser()&&(flag==1||flag==-1);//是攻击方同时又是下方计时器==这肯定是黑棋
            boolean jg2=!Client.isAttackUser()&&(flag==1||flag==-1);//非攻击方 同时又是下方计时器==这是白棋
            var=Client.isAttackUser()?1:2;
            System.out.println(var==1&&jg1||var==2&&jg2);
            if(var==1&&jg1||var==2&&jg2){
                core.ChessIt_newWork(_CgetX(e.getX()), (_CgetY(e.getY())));
            }else{
                JOptionPane.showMessageDialog(null,  "非你回合,非法下棋!!");
            }
//            System.out.println(var==1&&jg1);//是黑棋同时也是攻击方
//            System.out.println(var==2&&jg2);//是白棋同时也是被..
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

    public PlayerTime getPlayerTime_your() {
        return playerTime_your;
    }

    public void setPlayerTime_your(PlayerTime playerTime_your) {
        this.playerTime_your = playerTime_your;
    }

    public PlayerTime getPlayerTime_my() {
        return playerTime_my;
    }

    public void setPlayerTime_my(PlayerTime playerTime_my) {
        this.playerTime_my = playerTime_my;
    }
}
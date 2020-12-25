package test;

import GUI.GameGUI;
import Game.Core;

import javax.swing.*;

import java.awt.*;

public class GameGUITest {
    public static void main(String[] args) {
        JFrame Demo=new JFrame("测试棋盘");
        Demo.add(new GameGUI(new Core(19, 19),null,null));
        Dimension dim = Demo.getToolkit().getScreenSize();//获取屏幕大小
        Demo.setBounds(dim.width / 2 - 450, dim.height / 2 - 450, 800, 650);//设置窗口大小，width和height是取屏幕宽度和高度
        Demo.setVisible(true);
        Demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
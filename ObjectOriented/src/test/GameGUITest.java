package test;

import GUI.GameGUI;
import Game.Clock;
import Game.Core;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameGUITest {
    public static void main(String[] args) {
        JFrame Demo=new JFrame("测试棋盘");
        Demo.add(new GameGUI(new Core(19, 19),null,null));
        Dimension dim = Demo.getToolkit().getScreenSize();//获取屏幕大小
        Demo.setBounds(dim.width / 2 - 450, dim.height / 2 - 450, 800, 650);//设置窗口大小，width和height是取屏幕宽度和高度
        Demo.setVisible(true);
        Demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Test
    void Clock(){
        Clock clock=new Clock(0,0,5);
//        Clock.timeR(clock);
    }
}
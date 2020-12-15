package test;

import GUI.GameGUI;
import Game.Clock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameGUITest {
    public static void main(String[] args) {
        new GameGUI("五子棋");
    }

    @Test
    void Clock(){
        Clock clock=new Clock(0,0,5);
//        Clock.timeR(clock);
    }
}
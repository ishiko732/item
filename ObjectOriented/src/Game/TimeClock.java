package Game;

import javax.swing.*;

public class TimeClock implements Runnable{
    private JFrame app;
    private Clock clock;
    private int[] time;
    private static Thread th;
    private boolean exist=false;
    public TimeClock(JFrame app,int[] time){
        clock = new Clock(0, 0, 30);
        this.time=time;
        this.app=app;
    }
    @Override
    public void run() {
        while (time[0] !=0) {
            if(exist){
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clock.reduce();
            app.repaint();
            this.time[0]=clock.getSecond();
        }
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}

package Game;

import java.awt.*;

public class Clock {
    private int hour, minute, second;

    public Clock() {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }

    public Clock(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    //设定时间
    public void setup(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    //使时钟前进1秒
    public void add() {
        ++this.second;
        if(second>=60){
            minute+=1;
            second-=60;
            if(minute>=60){
                hour=(hour+1)%24;
                minute-=60;
            }
        }
    }
    //使时钟后退1秒
    public void reduce(){
        --this.second;
        if(second<0){
            minute-=1;
            second=60+second;
            if(minute<0){
                --hour;
                minute=60+minute;
                if(hour<0){
                    hour=24+hour;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Clock{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }

    public static void timeR(Clock clock){
        System.out.println(clock);
        while(clock.getHour()!=0||clock.getMinute()!=0||clock.getSecond()!=0){
            clock.reduce();
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(clock);
        }
    }

//    public static void time30(Graphics g,String str){
//        Clock clock=new Clock(0,0,30);
//        g.drawString(str+ clock.getSecond()+"s",650,440);
//        while(clock.getHour()!=0||clock.getMinute()!=0||clock.getSecond()!=0){
//            clock.reduce();
//            try{
//                Thread.sleep(1000);
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
//            g.clearRect(650,440,70,30);
//            g.drawString(str+ clock.getSecond()+"s",650,440);
//        }
//    }
}

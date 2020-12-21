package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlayerTime extends JPanel  implements ActionListener{
    //定义一个标签用于显示
    private JLabel label;
    //定义一个timer用于计时
    private Timer timer;

    public int i=0;
    //flag变量，用于记录
    private int flag = 0;

    //时间参数
    private int minute ;
    private int second ;

    private int m=0,s=0;

    private boolean re;

    public PlayerTime(boolean re){
        setBackground(Color.white);
        setPreferredSize(new Dimension(200,100));
        setLayout(null);
        this.re=re;
        //数字显示
        this.minute = 0;
        if(re){
            label = new JLabel("00:30");
            this.second = 30;
        }else{
            label = new JLabel("00:00");
            this.second = 0;
        }
        label.setBounds(88, 15, 100, 100);
        label.setFont(new Font("Dialog", 1, 24));
        add(label);

        timer = new Timer(1000, this);
        timer.start();


    }

    public void reset_time(){
        timer.stop();
        flag = 0;
        label.setText(resetTime());
    }

    public void start_time(){
        flag = 1;
        label.setText(countTime());
        timer.restart();
    }

    public void stop_time(){
        timer.stop();
        flag = 0;
        label.setText(stopTime());
    }
    /*
     * 计时方法
     */
    public String countTime(){
        if(this.re){
            this.second -= 1;
            if(this.second == 0){
                this.minute -= 1;
                this.second = 60;
                if(this.minute == -1){
                    this.minute =60;
                }
            }

        }else{
            this.second += 1;
            if(this.second == 60){
                this.minute += 1;
                this.second = 0;
            }
            if(this.minute == 60){
                this.minute = 0;
            }
        }
        String str ;
        if(minute==60){
            str ="时间到了";
            timer.stop();
            flag = 0;
        }else{
            str = this.toString(this.minute, this.second);
        }
        return str;
    }

    /*
     * 暂停计时方法
     */
    public String stopTime(){
        this.minute=0;
        if(re){
            this.second=30;
        }else{
            this.second=0;
        }
        String str = this.toString( this.minute, this.second);
        return str;
    }

    /*
     * 重置计时方法
     */
    public String resetTime(){
        this.minute=0;
        if(!re){
            this.second=0;
        }else{
            this.second=30;
        }
        String str = this.toString( this.minute, this.second);
        return str;
    }

    /*
     * 显示时间方法
     */
    public String toString(int minute, int second){
        m=minute;
        s=second;
        String str2 = String.format("%02d", this.minute);
        String str3 = String.format("%02d", this.second);
        return (str2 + ":" + str3);
    }

    public boolean getTimer(){

        if(second+minute*60>=20)
            return false;
        else
            return true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(flag==1 && timer.isRunning()){
            label.setText(countTime());
//            System.out.println( minute + ":" + second);
        }
        else if(flag==0 && !timer.isRunning()){
            label.setText(stopTime());
//            System.out.println( minute + ":" + second);
        }
    }
}




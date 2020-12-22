package Game;

import Client.User;

import java.io.Serializable;

public class GameRoomUser implements Serializable {
    private User user_write,user_black;//对战用户
    private Core core;//对战棋盘
    private int roomID;

    public GameRoomUser(User user_write, User user_black,Core core,int roomID) {
        this.user_write = user_write;
        this.user_black = user_black;
        this.core=core;
        this.roomID=roomID;
    }
    public User getUser_write() {
        return user_write;
    }

    public void setUser_write(User user_write) {
        this.user_write = user_write;
    }

    public User getUser_black() {
        return user_black;
    }

    public void setUser_black(User user_black) {
        this.user_black = user_black;
    }

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public String toString() {
        return "GameRoomUser{" +
                "user_write=" + user_write +
                ", user_black=" + user_black +
                ", roomID=" + roomID +
                '}';
    }
}

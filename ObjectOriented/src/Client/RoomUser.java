package Client;

import java.io.Serializable;

public class RoomUser implements Serializable {
    private User user_write,user_black;//对战用户
    private int roomID;

    public RoomUser() {
    }

    public RoomUser(User user_write, User user_black, int roomID) {
        this.user_write = user_write;
        this.user_black = user_black;
        this.roomID = roomID;
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

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public String toString() {
        return "RoomUser{" +
                "user_write=" + user_write +
                ", user_black=" + user_black +
                ", roomID=" + roomID +
                '}';
    }
}

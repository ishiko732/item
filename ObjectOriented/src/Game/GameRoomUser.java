package Game;

import Client.User;
import Client.RoomUser;

import java.io.Serializable;

public class GameRoomUser extends RoomUser implements Serializable{
    private Core core;//对战棋盘

    public GameRoomUser(User user_write, User user_black,Core core,int roomID) {
        super(user_write,user_black,roomID);
        this.core=core;
    }
    public Core getCore() {
        return core;
    }
    public void setCore(Core core) {
        this.core = core;
    }
//    @Override
//    public String toString() {
//        return "GameRoomUser{" +
//                "user_write=" + super.getUser_write() +
//                ", user_black=" + super.getUser_black() +
//                ", roomID=" + super.getRoomID() +
//                '}';
//    }
}

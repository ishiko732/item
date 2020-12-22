package Game;

import Client.User;

public class GameRoomUser {
    private User user_write,user_black;//对战用户
    private Core core;//对战棋盘

    public GameRoomUser(User user_write, User user_black,Core core) {
        this.user_write = user_write;
        this.user_black = user_black;
        this.core=core;
    }
}

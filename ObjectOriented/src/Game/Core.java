package Game;

import Client.Client;

import java.io.IOException;
import java.util.Stack;

/**
 * @author GodofOrange
 * 棋盘数据结构
 */
@SuppressWarnings("FieldMayBeFinal")//对该类的警告信息忽略
public class Core {
    //棋盘大小
    private int[][] core;
    private int x;
    private int y;
    private Client client = null;//不是null 将具备发送棋子功能
    private int room;
    //栈
    Stack<Chess> stack;

    public Core(int x, int y) {
        stack = new Stack<>();
        core = new int[x][y];
        this.x = x;
        this.y = y;
    }

    public Core(int x, int y, Client client, int roomID) {
        stack = new Stack<>();
        core = new int[x][y];
        this.x = x;
        this.y = y;
        this.client = client;
        this.room = roomID;
    }

    //检查该地是否有空位置
    private boolean __CanInput(int x, int y) {
        return core[x][y] == 0;
    }

    //判断输赢
    private int checkVictory(int x, int y, int var) {
        //横向判断
        int trans = 0;
        for (int i = x - 4; i < x + 5; i++) {
            if (i < 0 || i >= this.x) continue;
            if (core[i][y] == var) {
                trans++;
            } else {
                trans = 0;
            }
            if (trans == 5) return var;
        }
        //纵向判断
        int longitudinal = 0;
        for (int i = y - 4; i < y + 5; i++) {
            if (i < 0 || i >= this.y) continue;
            if (core[x][i] == var) {
                longitudinal++;
            } else {
                longitudinal = 0;
            }
            if (longitudinal == 5) return var;
        }
        //从左上到右下
        int leftUPToDown = 0;
        for (int i = x - 4, j = y + 4; i < x + 5 && j > y - 5; i++, j--) {
            if (i < 0 || i >= this.x || j < 0 || j >= this.y) continue;
            if (core[i][j] == var) {
                leftUPToDown++;
            } else {
                leftUPToDown = 0;
            }
            if (leftUPToDown == 5) return var;
        }
        //从左下到右上
        int leftDownToUP = 0;
        for (int i = x + 4, j = y + 4; i > x - 5 && j > y - 5; i--, j--) {
            if (i < 0 || i >= this.x || j < 0 || j >= this.y) continue;
            if (core[i][j] == var) {
                leftDownToUP++;
            } else {
                leftDownToUP = 0;
            }
            if (leftDownToUP == 5) return var;
        }
        return 0;
    }

    /**
     * 在该位置下棋  1:white 2:black
     *
     * @param x   横坐标
     * @param y   纵坐标
     * @param var 棋子种类
     * @return 1:white 赢   2:black赢
     */
    public int ChessIt(int x, int y, int var) {
        if (__CanInput(x, y)) {
            core[x][y] = var;
            Chess chess = new Chess(x, y);
            stack.push(chess);
            return checkVictory(x, y, var);//0 没出结果 1 白 2 黑
        } else return -1;
    }

    public void ChessIt_newWork(int x, int y) {
        if (client != null) {
            try {
                String v =Client.isAttackUser()?"black" : "white";//攻击方为黑棋
                client.sendCommand("game={var=" + v + ",xy=(" + x + "|" + y + "),roomID=" + room + "}",true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //悔棋
    @SuppressWarnings("UnusedReturnValue")//对该方法禁止检查 返回值未使用情况
    public boolean RetChess() {
        if (stack.isEmpty()) return false;
        Chess chess = stack.pop();
        core[chess.x][chess.y] = 0;
        return true;
    }

    //获得棋盘状态
    public int[][] getCore() {
        return this.core;
    }

    public void setCore() {
        for (int i = 0; i < core.length; i++) {
            for (int j = 0; j < core[0].length; j++) {
                core[i][j] = 1;
            }
        }
    }


    //重新开始
    public void Restart() {
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                this.core[i][j] = 0;
            }
        }
        this.stack.clear();
    }

    //求和
    public boolean checkSum() {
        int flag = 1;//已经和了
        for (int[] ints : core) {
            for (int j = 0; j < core[0].length; j++) {
                if (ints[j] == 0) {
                    flag = 0;//未和
                    break;
                }
            }
        }
        return flag == 1;
    }
}

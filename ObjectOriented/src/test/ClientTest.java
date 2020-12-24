package test;

import static org.junit.jupiter.api.Assertions.*;

import Client.*;
import GUI.ClientGUI;
import GUI.LoginWindows;
import Server.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientTest {
    @Test
    void testUser() {
        User user = new User("001", "2019112", "127.0.0.1", 8089);
        System.out.println(user);
        assertEquals("127.0.0.1", user.getServerIP());
        assertEquals("001", user.getUID());
        assertEquals("2019112", user.getPassword());
        assertEquals(8089, user.getServerPort());
    }

    @Test
    void testServer() {
        Server server = new Server();
    }

    @Test
    void testLogin() {//测试登录
        for (int i = 1; i < 10; i++) {
            User user = new User(String.valueOf(i), "2019112", "127.0.0.1", 8089);
            Client client = new Client(user);
            assertTrue(client.islogin());
            System.out.println("login(" + user.getUID() + "):" + client.islogin());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testLogin_name() {//测试重名登录
        User user1 = new User("001", "2019112", "127.0.0.1", 8089);
        Client client1 = new Client(user1);
        assertTrue(client1.islogin());
        System.out.println("login(" + user1.getUID() + "):" + client1.islogin());

        User user2 = new User("001", "2019112", "127.0.0.1", 8089);
        Client client2 = new Client(user2);
        assertFalse(client2.islogin());
        System.out.println("login(" + user2.getUID() + "):" + client2.islogin());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testClientMessage() {
        ArrayList<Client> clients = new ArrayList<Client>();
        for (int i = 0; i < 5; i++) {
            User user = new User("UID-" + i, "2019112" + i, "127.0.0.1", 8089);
            clients.add(new Client(user));
            assertTrue(clients.get(i).islogin());
            clients.get(i).messageListener();
        }
        for (int i = 0; i < 3; i++) {//与客户端交流
            clients.get(0).sendMessage("hello,我是" + clients.get(0).getUser().getUID()
                    , "UID-" + (int) (Math.random() * 10) % 5);
        }
        clients.get(0).sendMessage("hello,服务器，我是客户端 ", "Server");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testUserList() {
        ArrayList<Client> clients = new ArrayList<Client>();//获取客户端在线列表
        for (int i = 0; i < 5; i++) {
            User user = new User("UID-" + i, "./res/face/" + (i + 1) + "-1.gif", "127.0.0.1", 8089);
            clients.add(new Client(user));
            assertTrue(clients.get(i).islogin());
            clients.get(i).messageListener();
//            clients.get(i).messageListener();
        }
        for (int i = 0; i < 5; i++) {
            Map<String, String> userMap = clients.get(0).getUserList();
            System.out.println((i + 1) + "UserList=" + userMap);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testTransferUserMap() {
        String userStr = "get-userList:{UID-0=./res/face/1-1.gif, UID-1=./res/face/2-1.gif, UID-2=./res/face/3-1.gif, UID-3=./res/face/4-1.gif, UID-4=./res/face/5-1.gif}";
        String regex = "\\{[^\\]]*\\}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(userStr);
        matcher.find();
        String[] userList = matcher.group().replaceAll("\\{|\\}", "").split(",");
        Map<String, String> userMap = new HashMap<>();
        for (String s : userList) {
            String[] user_split = s.split("=");
            userMap.put(user_split[0], user_split[1]);
        }
        System.out.println(userMap);
    }

    @Test
    void testUserOnline() {//模拟客户端在线列表
        ArrayList<Client> clients = new ArrayList<Client>();
        for (int i = 0; i < 5; i++) {
            User user = new User("UID-" + i, "./res/face/" + (i + 1) + "-1.gif", "127.0.0.1", 8089);
            clients.add(new Client(user));
            assertTrue(clients.get(i).islogin());
            clients.get(i).messageListener();
        }
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testReceiveAttack() {//客户端1-接受方
        User user = new User("UID0", "./res/face/1-1.gif", "127.0.0.1", 8089);
        Client client = new Client(user);
        assertTrue(client.islogin());
        client.messageListener();
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    void testDoubleattack() {//客户端2-发起方
        User user = new User("申请人", "2019110A", "127.0.0.1", 8089);
        Client client = new Client(user);
        assertTrue(client.islogin());
        client.messageListener();
        System.out.println("申请与UID0对战");
        client.applyAttack("UID0");
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testTransferRoomUser() {
        RoomUser roomUser = new RoomUser();
        String userStr = "gameRoom:RoomUser{user_write=User{UID='UID-1', password='20191101', serverIP='127.0.0.1', serverPort=8089}, user_black=User{UID='UID-0', password='20191100', serverIP='127.0.0.1', serverPort=8089}, roomID=1}";
        String regex = "\\{[^\\]]*\\}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(userStr);
        matcher.find();
        String group = matcher.group();
        String[] split = group.substring(1, group.length() - 1).split("},");
        for (int i = 0; i < split.length - 1; i++) {
            split[i] += "}";
            matcher = compile.matcher(split[i]);
            matcher.find();
            String[] user = matcher.group().replaceAll("\\{|\\}| ", "").split(",");
            User userMessage = new User();
            Map<String, String> userMap = new HashMap<>();
            for (String s : user) {
                String[] user_split = s.split("=");
                userMap.put(user_split[0], user_split[1].replace("'",""));
            }
            userMessage.setUID(userMap.get("UID"));
            userMessage.setPassword(userMap.get("password"));
            userMessage.setServerIP(userMap.get("serverIP"));
            userMessage.setServerPort(Integer.parseInt(userMap.get("serverPort")));
            if (i == 0) {
                roomUser.setUser_black(userMessage);
            } else {
                roomUser.setUser_write(userMessage);
            }
        }
        roomUser.setRoomID(Integer.parseInt(split[2].replace(" roomID=", "")));
        System.out.println(roomUser);
    }
    @Test
    void testTransferxy(){
        String str="command-game:game={var=write,xy=(2|2),roomID=1};";//"command-game:game={var=(while),xy=(x|y),roomID=(id)};"
        String regex = "\\{[^\\]]*\\}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        matcher.find();
        String[] messageGame = matcher.group().replaceAll("\\{|\\}","").split(",");
        Map<String,String>gameMap=new HashMap<>();
        for (String s : messageGame) {
            String[] game_split = s.split("=");
            gameMap.put(game_split[0], game_split[1].replace("|",","));
        }
        System.out.println(gameMap);
        System.out.println(Arrays.toString(gameMap.get("xy").replaceAll("\\(|\\)","").split(",")));
    }
    @Test
    void testgameCommand(){
        String str="command-game:game={command=remake,roomID=id};";//"command-game:game={var=(while),xy=(x|y),roomID=(id)};"
        String regex = "\\{[^\\]]*\\}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        matcher.find();
        String[] messageGame = matcher.group().replaceAll("\\{|\\}","").split(",");
        Map<String,String>gameMap=new HashMap<>();
        for (String s : messageGame) {
            String[] game_split = s.split("=");
            gameMap.put(game_split[0], game_split[1].replace("|",","));
        }
        System.out.println(gameMap);
        System.out.println(gameMap.get("command"));
        System.out.println(gameMap.get("roomID"));
    }
    @Test
    void testSendMessage() {//
        //Chat-[(UID)]:send=\"[(value)]\",obj=[(UID/Server)];
        String regex = "\\[[^\\]]*\\]";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        String dakuohao = "Chat-[康明]:send=[\"我是猪\"],obj=[Server]";
        Matcher matcher = compile.matcher(dakuohao);
        while (matcher.find()) {
            System.out.println(matcher.group().replaceAll("\\(|\\)|\\[|\\]", "") + ";");
        }
    }

    @Test
    void testGetMessage() {
        String regex = "\\[[^\\]]*\\]";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        String dakuohao = "get-chat[" + "康明" + "],send=[" + "你是猪" + "];";
        Matcher matcher = compile.matcher(dakuohao);
        while (matcher.find()) {
            System.out.println(matcher.group().replaceAll("\\(|\\)|\\[|\\]", "") + ";");
        }
    }

    @Test
    void testGetAttack() {
        String regex = "\\[[^\\]]*\\]";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        String dakuohao = "command:Client!attackUser:[(UID),(attackUserUID)];";
        Matcher matcher = compile.matcher(dakuohao);
        String message = "";
        while (matcher.find()) {
            message += matcher.group().replaceAll("\\(|\\)|\\[|\\]", "");
        }
        System.out.println(Arrays.toString(message.split(",")));
    }

    @Test
    void testArrayListread() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("w");
        arrayList.add("obj");

        try {
            FileOutputStream fs = new FileOutputStream("1.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fs);
            oos.writeObject(arrayList);
            FileInputStream fi = new FileInputStream("1.ser");
            ObjectInputStream ooi = new ObjectInputStream(fi);
            ArrayList<String> ar = (ArrayList<String>) ooi.readObject();
            ooi.close();
            oos.close();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    void message() {
        String a = JOptionPane.showInputDialog(null, "是否同意与" + "对战?(Y/N)", "申请对战", JOptionPane.PLAIN_MESSAGE);
        System.out.println(a);
    }

    @Test
    void client1() {
        new LoginWindows(new ClientGUI());
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void client2() {
        new LoginWindows(new ClientGUI());
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

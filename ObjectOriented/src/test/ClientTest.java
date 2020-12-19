package test;

import static org.junit.jupiter.api.Assertions.*;

import Client.*;
import Server.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
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
            client.pushUser();
            assertTrue(client.loginUser());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    void testClientMessage() {
        ArrayList<Client> clients = new ArrayList<Client>();
        for (int i = 0; i < 5; i++) {
            User user = new User("UID-" + i, "2019112" + i, "127.0.0.1", 8089);
            clients.add(new Client(user));
            clients.get(i).pushUser();
            assertTrue(clients.get(i).loginUser());
            clients.get(i).messageListener();
        }
        for (int i = 0; i < 3; i++) {//与客户端交流
            clients.get(0).sendMessage("hello,我是"+ clients.get(0).getUser().getUID()
                     , "UID-"+(int)(Math.random()*10)%5);
        }
        clients.get(0).sendMessage("hello,服务器，我是客户端 ", "Server");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testUserList(){
        ArrayList<Client> clients = new ArrayList<Client>();//获取客户端在线列表
        for (int i = 0; i < 5; i++) {
            User user = new User("UID-" + i, "2019110" + i, "127.0.0.1", 8089);
            clients.add(new Client(user));
            clients.get(i).pushUser();
            assertTrue(clients.get(i).loginUser());
            clients.get(i).messageListener();
        }
        ArrayList<String> userList = clients.get(0).getUserList();
        System.out.println("UserList="+userList);
        for (int i = 1; i <userList.size() ; i++) {
            clients.get(0).sendMessage("请求与"+userList.get(i)+"对战!",userList.get(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
    void testArrayListread(){
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("w");
        arrayList.add("obj");

        try {
            FileOutputStream fs=new FileOutputStream("1.ser");
            ObjectOutputStream oos=new ObjectOutputStream(fs);
            oos.writeObject(arrayList);
            FileInputStream fi=new FileInputStream("1.ser");
            ObjectInputStream ooi=new ObjectInputStream(fi);
            ArrayList<String> ar=(ArrayList<String>)ooi.readObject();
            ooi.close();
            oos.close();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }
}

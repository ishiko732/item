package test;

import static org.junit.jupiter.api.Assertions.*;

import Client.*;
import Server.*;
import org.junit.jupiter.api.Test;

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
    void testLogin() {
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

        for (int i = 0; i < 2; i++) {
            User user = new User("UID-" + i, "2019112" + i, "127.0.0.1", 8089);
            clients.add(new Client(user));
            clients.get(i).pushUser();
            assertTrue(clients.get(i).loginUser());
            clients.get(i).messageListener();
        }
        for (int i = 0; i < 3; i++) {
            clients.get(0).sendMessage("hello,我是一号机 " + i, "UID-1");
        }
        clients.get(0).sendMessage("hello,服务器，我是客户端 ", "Server");
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
}

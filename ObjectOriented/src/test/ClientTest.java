package test;

import static org.junit.jupiter.api.Assertions.*;

import Client.*;
import Server.*;
import org.junit.jupiter.api.Test;

public class ClientTest {
    @Test
    void testUser() {
        User user=new User("001","2019112","127.0.0.1",8089);
        System.out.println(user);
        assertEquals("127.0.0.1",user.getServerIP());
        assertEquals("001",user.getUID());
        assertEquals("2019112",user.getPassword());
        assertEquals(8089,user.getServerPort());
    }

    @Test
    void testServer(){
        Server server=new Server();
    }
    @Test
    void testLogin(){
        for (int i = 1; i <10 ; i++) {
            User user=new User(String.valueOf(i),"2019112","127.0.0.1",8089);
            Client client=new Client(user);
            client.pushUser();
            assertTrue(client.loginUser());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

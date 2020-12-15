package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Login  {
    Socket client;
    public boolean isLogin(User user) throws IOException {
        client=new Socket(user.getServerIP(),user.getServerPort());
        DataInputStream in=new DataInputStream(client.getInputStream());//read Server Massage->Client
        DataOutputStream out=new DataOutputStream(client.getOutputStream());
        out.writeUTF("login:getUID");
        String UID=in.readUTF();
        user.setUID(UID);
        ObjectOutputStream out_obj=new ObjectOutputStream(client.getOutputStream());//write User Object->Server
        out_obj.writeObject(user);
        return "login-yes".equals(in.readUTF());
    }
}

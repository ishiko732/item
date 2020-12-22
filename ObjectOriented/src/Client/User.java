package Client;

import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable {
    private String UID;
    private String password;
    private String serverIP;
    private int serverPort;

    public User() {
    }

    public User(String UID, String password, String serverIP, int serverPort) {//UID默认写"0"
        this.UID = UID;
        this.password = password;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public String toString() {
        return "User{" +
                "UID='" + UID + '\'' +
                ", password='" + password + '\'' +
                ", serverIP='" + serverIP + '\'' +
                ", serverPort=" + serverPort +
                '}';
    }
}

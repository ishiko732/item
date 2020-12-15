package Client;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
    private String UID;
    private String serverIP;
    private int serverPort;


    public User() {
    }

    public User(String name, String password, String UID, String serverIP, int serverPort) {//UID默认写"0"
        this.name = name;
        this.password = password;
        this.UID = UID;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", UID='" + UID + '\'' +
                ", serverIP='" + serverIP + '\'' +
                ", serverPort=" + serverPort +
                '}';
    }
}

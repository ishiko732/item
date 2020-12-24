package GUI;

import Client.Client;

public class ClientGUI {
    private Client client;
    private static GameGUI gamegui;
    public static void main(String[] args) {
        new LoginWindows(new ClientGUI());
//        new LoginWindows(new ClientGUI());
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public static GameGUI getGameGui() {
        return gamegui;
    }

    public static void setGameGui(GameGUI gamegui) {
        ClientGUI.gamegui= gamegui;
    }
}

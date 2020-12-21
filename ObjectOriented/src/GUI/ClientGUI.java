package GUI;

import Client.Client;

public class ClientGUI {
    private Client client;
    public static void main(String[] args) {
        new LoginWindows(new ClientGUI());
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

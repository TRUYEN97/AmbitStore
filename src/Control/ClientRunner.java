/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.Socket.ClientReceiver;
import Model.Socket.ClientSender;
import Model.AllKeyword;
import Model.Source.Setting;
import Unicast.Client.Client;
import Unicast.commons.Actions.simplePackage;
import View.UI;
import java.io.IOException;
import javax.swing.Timer;

/**
 *
 * @author Administrator
 */
public class ClientRunner implements Runnable {

    private final Client<simplePackage> client;

    public ClientRunner(UI display, ClientReceiver receiver, ClientSender sender) throws IOException {
        this.client = new Client(receiver);
        sender.setClient(client);
        new Timer(1000, (e) -> {
            if (!this.client.isConnect()) {
                String host = Setting.getInstance().getString(AllKeyword.SERVER_HOST);
                int port = Setting.getInstance().getInteger(AllKeyword.SERVER_PORT);
                this.client.connect(host, port);
                display.showServerAddr(
                        String.format("Host: %s  -  Post: %s", host, port));
            }
            display.setServerConnect(this.client.isConnect());
        }).start();
    }

    @Override
    public void run() {
        while (true) {
            if (client.isConnect()) {
                client.run();
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

}

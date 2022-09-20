/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.Socket.ClientReceiver;
import Model.Socket.ClientSender;
import Model.AllKeyword;
import Model.PcInformation;
import Model.Servants;
import Model.Source.Setting;
import Unicast.Client.Client;
import Unicast.commons.Actions.Object.MyName;
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
    private final UI display;
    private final Thread thread;
    private final ClientSender sender;

    public ClientRunner(UI display, Servants servants) throws IOException {
        this.client = new Client(servants.getClientReceiver());
        this.display = display;
        this.sender = servants.getClientSender();
        this.sender.setClient(client);
        new Timer(500, (e) -> {
            display.setServerConnect(this.client.isConnect());
        }).start();
        this.thread = new Thread(this);
    }

    public void start() {
        this.thread.start();
    }

    public void stop() {
        this.thread.stop();
    }

    @Override
    public void run() {
        while (true) {
            if (client.isConnect()) {
                client.run();
            } else {
                String host = Setting.getInstance().getString(AllKeyword.SERVER_HOST);
                int port = Setting.getInstance().getInteger(AllKeyword.SERVER_PORT);
                display.showServerAddr(String.format("Host: %s  -  Post: %s", host, port));
                if (this.client.connect(host, port)) {
                    sender.sendMyName();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            }
        }

    }

}

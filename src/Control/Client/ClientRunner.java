/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Client;

import Model.AllKeyword;
import Model.Source.Setting;
import Unicast.Client.Client;
import Unicast.commons.Actions.SimplePackage;
import View.UI;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Administrator
 */
public class ClientRunner implements Runnable {

    private final Setting setting;
    private final Client<SimplePackage> client;

    public ClientRunner(UI display) throws IOException {
        this.client = new Client(new ClientReceiver(display));
        this.setting = Setting.getInstance();
        new Timer(1000, (e) -> {
            if (!this.client.isConnect()) {
                String host = setting.getString(AllKeyword.SERVER_HOST);
                int port = setting.getInteger(AllKeyword.SERVER_PORT);
                this.client.connect(host, port);
                display.showServerAddr(
                        String.format("<html><center>Host: %s</center><br>Post: %s</html>", host, port));
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

    public void send(SimplePackage simplePackage) {
        if (!this.client.send(simplePackage)) {
            JOptionPane.showMessageDialog(null, "Request to server failed!");
        }
    }

}

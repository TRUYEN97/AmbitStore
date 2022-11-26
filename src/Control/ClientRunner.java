/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.Socket.ThisClieant.ClientSender;
import Model.AllKeyword;
import Model.Application.AppDownload;
import Model.Socket.ThisClieant.ClientReceiver;
import Model.Source.Setting;
import Unicast.Client.Client;
import Unicast.commons.Actions.simplePackage;

/**
 *
 * @author Administrator
 */
public class ClientRunner implements Runnable {

    private static volatile ClientRunner instanse;
    private final Client<simplePackage> client;
    private final Thread thread;
    private final ClientSender sender;
    private final ClientReceiver clientReceiver;

    private ClientRunner(){
        this.clientReceiver = new ClientReceiver();
        this.client = new Client<>(clientReceiver);
        this.sender = new ClientSender(client);
        this.clientReceiver.setSender(sender);
        this.thread = new Thread(this);
    }

    public static ClientRunner getInstanse() {
        ClientRunner ins = ClientRunner.instanse;
        if (ins == null) {
            synchronized (ClientRunner.class) {
                ins = ClientRunner.instanse;
                if (ins == null) {
                    ins = instanse = new ClientRunner();
                }
            }
        }
        return ins;
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
        while (true) {
            if (this.client.isConnect()) {
                client.run();
            } else {
                String host = Setting.getInstance().getString(AllKeyword.SERVER.SERVER_HOST);
                int port = Setting.getInstance().getInteger(AllKeyword.SERVER.SERVER_PORT);
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

    public ClientSender getSender() {
        return this.sender;
    }

    public boolean isConnect() {
        return client.isConnect() && this.clientReceiver.usedToReceive();
    }

    public void setAppDownLoad(AppDownload appDownload) {
        this.clientReceiver.setAppDownload(appDownload);
    }

}

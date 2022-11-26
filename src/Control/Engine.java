/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.Application.AppDownload;
import Model.Application.AppPackage;
import SystemTray.MySystemTray;
import Model.Source.Setting;
import Unicast.commons.Actions.simplePackage;
import View.UI;
import java.awt.AWTException;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Engine {

    private final UI ui;
    private final MySystemTray systemTray;
    private final String title;
    private final ClientRunner clientRunner;
    private final ServerStore serverStore;

    public Engine(String title) throws IOException, Exception {
        this.title = title;
        this.ui = new UI(title);
        this.clientRunner = ClientRunner.getInstanse();
        AppPackage appPackage = new AppPackage(ui.getStorePanel(), clientRunner.getSender());
        this.serverStore = new ServerStore(appPackage);
        appPackage.setServerStore(serverStore.getStoreServerManage().getOnlineNumble());
        clientRunner.setAppDownLoad(new AppDownload(appPackage));
        this.systemTray = new MySystemTray(ui);
    }

    public void run() {
        if (!this.systemTray.initTrayIcon(Setting.getInstance().getMyIconPath(), this.title)) {
            JOptionPane.showConfirmDialog(null, "SystemTray not support!");
        }
        try {
            this.systemTray.apply();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        ClientRunner.getInstanse().start();
        this.serverStore.start();
        this.ui.display();
    }

}

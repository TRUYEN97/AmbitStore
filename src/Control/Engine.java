/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.Socket.ClientReceiver;
import Model.Socket.ClientSender;
import Model.Servants;
import SystemTray.MySystemTray;
import Model.Source.Setting;
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
    private final Core core;
    private final MySystemTray systemTray;
    private final ClientSender clientSender;
    private final ClientReceiver clientReceiver;
    private final Servants servants;
    private final String title;

    public Engine(String title) throws IOException {
        this.title = title;
        this.servants = new Servants();
        this.clientSender = new ClientSender();
        this.ui = new UI(title, clientSender);
        this.clientReceiver = new ClientReceiver(servants, ui);
        this.core = new Core(this.ui, clientReceiver, clientSender);
        this.systemTray = new MySystemTray(ui);
    }

    public void run() {
        this.core.run();
        this.systemTray.initSystemTray();
        if (!this.systemTray.initTrayIcon(Setting.getInstance().getMyIconPath(), this.title)) {
            JOptionPane.showConfirmDialog(null, "SystemTray not support!");
        }
        try {
            this.systemTray.apply();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        this.ui.display();
    }

}

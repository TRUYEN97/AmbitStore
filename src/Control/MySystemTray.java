/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import View.UI;
import java.awt.SystemTray;
import java.awt.TrayIcon;

/**
 *
 * @author Administrator
 */
public class MySystemTray {

    private final UI ui;
    private TrayIcon trayIcon;
    private SystemTray systemTray;

    public MySystemTray(UI ui) {
        this.ui = ui;
    }

    public boolean init() {
        if (SystemTray.isSupported()) {
            return false;
        }
        this.systemTray = SystemTray.getSystemTray();
        return true;
    }

}

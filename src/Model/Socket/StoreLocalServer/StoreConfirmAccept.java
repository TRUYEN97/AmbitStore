/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Socket.StoreLocalServer;

import Model.Application.AppPackage;
import Unicast.Server.ClientHandler;
import Unicast.Server.Handlermanage.WaitLine.AbsConfirmAccept;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class StoreConfirmAccept extends AbsConfirmAccept<String, String>{

    private final AppPackage appPackage;
    
    public StoreConfirmAccept(AppPackage appPackage) {
        super();
        this.appPackage = appPackage;
    }

    
    @Override
    protected String acceptable(ClientHandler<String> handler, String data) {
        if (data == null) {
            return null;
        }
        if (onlineNumble.hasOnline(data)) {
            JOptionPane.showMessageDialog(null, data + " has connected!");
            return null;
        }
        if (appPackage.hasContainAppOpened(data)) {
            return data;
        }
        return null;
    }
    
}

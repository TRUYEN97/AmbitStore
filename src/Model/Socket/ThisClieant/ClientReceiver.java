/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Socket.ThisClieant;

import Model.Application.AppDownload;
import Unicast.commons.Actions.MapRowsParameter;
import Unicast.commons.Actions.Object.ObjectPackage;
import Unicast.commons.Actions.simplePackage;
import Unicast.commons.Enum.ACTION;
import javax.swing.JOptionPane;
import Unicast.commons.Interface.IOjectClientReceiver;

/**
 *
 * @author Administrator
 */
public class ClientReceiver implements IOjectClientReceiver<simplePackage> {

    private AppDownload appDownload;
    private ClientSender sender;
    private boolean usedToReceive;

    public ClientReceiver() {
        this.usedToReceive = false;
    }

    public void setAppDownload(AppDownload appDownload) {
        this.appDownload = appDownload;
    }

    public void setSender(ClientSender sender) {
        this.sender = sender;
    }

    @Override
    public void receiver(simplePackage pkg) {
        ACTION action = pkg.getAction();
        if (action == null) {
            return;
        }
        switch (action) {

            case WHO_ARE_U -> {
                this.sender.sendMyName();
            }

            case WELCOME -> {
                this.usedToReceive = true;
                this.sender.reDownloadAllProgram();
            }

            case MESSAGE -> {
                if (pkg instanceof ObjectPackage mess) {
                    JOptionPane.showMessageDialog(null, mess.getdata());
                }
            }

            case UPDATE_ALL_PROGRAM -> {
                if (appDownload == null) {
                    return;
                }
                appDownload.reDownload((MapRowsParameter) pkg);
            }
            
            case UPDATE_PROGRAM -> {
                if (appDownload == null) {
                    return;
                }
                appDownload.reDownload((MapRowsParameter) pkg);
            }

        }
    }

    public boolean usedToReceive() {
        return this.usedToReceive;
    }

}

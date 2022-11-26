/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Application;

import Model.Application.AppEntity.AppEntity;
import Model.Socket.ThisClieant.ClientSender;
import Unicast.commons.Interface.IListNumble;
import View.StorePanel;
import View.UI;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class AppPackage {

    private final HashMap<String, AppEntity> apps;
    private final ClientSender sender;
    private final StorePanel storePanel;
    private IListNumble<String, String> onlineNumble;

    public AppPackage(StorePanel storePanel, ClientSender sender) throws Exception {
        this.sender = sender;
        this.apps = new HashMap<>();
        this.storePanel = storePanel;
    }

    public void setServerStore(IListNumble<String, String> onlineNumble) {
        this.onlineNumble = onlineNumble;
    }
    
    void deleteApp(String project){
        AppEntity app = this.apps.remove(project);
        this.storePanel.deleteApp(app);
    }

    void setBackupFolder(String project, File tempFolder, String appName,
            String appFileName, String version, String description) throws Exception {
        AppEntity app;
        if (this.apps.containsKey(project)) {
            app = this.apps.get(project);
            app.setBackupfodler(tempFolder, appName, appFileName, version, description);
        } else {
            app = new AppEntity(this.sender, this.onlineNumble, project,
                    appName, appFileName, version, tempFolder, description);
            this.apps.put(project, app);
        }
        this.storePanel.updateApp(app);
    }

    public boolean hasContainAppOpened(String project) {
        for (var app : this.apps.values()) {
            if (app.isRuning() && app.getWindowTitle().equals(project)) {
                return true;
            }
        }
        return false;
    }

}

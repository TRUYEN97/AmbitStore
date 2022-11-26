/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.Application.AppPackage;
import Model.Socket.StoreLocalServer.StoreConfirmAccept;
import Model.Socket.StoreLocalServer.StoreServerReceive;
import Model.Socket.StoreLocalServer.StoreServerSender;
import Model.Source.Setting;
import Unicast.Server.Handlermanage.DefaultServerHandleManagement;
import Unicast.Server.Server;

/**
 *
 * @author Administrator
 */
public class ServerStore {

    private final Server server;
    private final Setting setting;
    private final DefaultServerHandleManagement<String, String> storeHanlerManager;

    public ServerStore(AppPackage appPackage) throws Exception {
        this.setting = Setting.getInstance();
        storeHanlerManager = new DefaultServerHandleManagement<>(
                        new StoreServerReceive(),
                        new StoreServerSender(),
                        new StoreConfirmAccept(appPackage),
                        setting.getGirdColumn() * setting.getGirdRow(),
                        2);
        this.server = new Server(this.setting.getPortStoreServer(), storeHanlerManager);
    }
    
    public DefaultServerHandleManagement<String, String> getStoreServerManage(){
        return this.storeHanlerManager;
    }

    public void start() {
        this.server.start();
    }

}

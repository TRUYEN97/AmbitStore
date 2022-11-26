/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Socket.ThisClieant;

import Model.Loger;
import Model.PcInformation;
import Unicast.commons.Actions.Object.MyName;
import Unicast.commons.Actions.UpdateProject;
import Unicast.commons.Actions.simplePackage;
import Unicast.commons.Enum.ACTION;
import Unicast.commons.Interface.ISend;

/**
 *
 * @author Administrator
 */
public class ClientSender {

    private final ISend<simplePackage> client;

    public ClientSender(ISend<simplePackage> client) {
        this.client = client;
    }

    public boolean reDownloadAllProgram() {
        return this.send(new simplePackage(ACTION.UPDATE_ALL_PROGRAM));
    }

    private boolean send(simplePackage pg) {
        if (pg == null) {
            return false;
        }
        boolean result;
        Loger.getInstance().addLog("Send",
                String.format("%s - %s", pg.getAction(),
                        result = this.client.send(pg)));
        return result;
    }

    public void sendMyName() {
        PcInformation pcInfo = PcInformation.getInstance();
        this.client.send(new MyName(
                pcInfo.getComputerName(),
                pcInfo.getSystemName(),
                pcInfo.getLine(),
                pcInfo.getAllHardwareAddress()));
    }

    public boolean update(String project) {
        return send(new UpdateProject(project));
    }

}

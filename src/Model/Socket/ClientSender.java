/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Socket;

import Model.Loger;
import Unicast.commons.Actions.simplePackage;
import Unicast.commons.Enum.ACTION;
import Unicast.commons.Interface.ISend;

/**
 *
 * @author Administrator
 */
public class ClientSender {

    private ISend client;

    public void setClient(ISend client) {
        this.client = client;
    }

    public boolean update() {
        return this.send(new simplePackage(ACTION.UPDATE));
    }

    private boolean send(simplePackage pg) {
        if(pg == null){
            return false;
        }
        boolean result;
        Loger.getInstance().addLog("Send",
                String.format("%s - %s", pg.getAction()
                        , result = this.client.send(pg)));
        return result;
    }

}

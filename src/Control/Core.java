/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Control.Client.ClientRunner;
import Model.App.AppPackage;
import View.UI;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class Core {

    private final AppPackage appMangement;  
    private final ClientRunner clientRunner;
    private final UI ui;

    public Core(UI ui) throws IOException {
        this.appMangement = new AppPackage();
        this.ui = ui;
        this.clientRunner = new ClientRunner(ui);
    }

    void run() {
        new Thread(clientRunner).start();
        this.appMangement.scanApps();
        this.ui.addApps(this.appMangement.getApps());
    }

}

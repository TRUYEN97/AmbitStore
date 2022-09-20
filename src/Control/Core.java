/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.App.AppPackage;
import Model.Servants;
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

    public Core(UI ui, Servants servants) throws IOException {
        this.appMangement = new AppPackage();
        this.ui = ui;
        this.clientRunner = new ClientRunner(ui, servants);
    }

    public ClientRunner getClientRunner() {
        return clientRunner;
    }

    void run() {
        this.clientRunner.start();
        this.appMangement.scanApps();
        this.ui.addApps(this.appMangement.getApps());
    }

}

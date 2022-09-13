/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.App.AppPackage;
import View.UI;

/**
 *
 * @author Administrator
 */
public class Core {

    private final AppPackage appMangement;
    private final UI ui;

    public Core(UI ui) {
        this.appMangement = new AppPackage();
        this.ui = ui;
    }

    void run() {
        this.appMangement.scanApps();
        this.ui.addApps(this.appMangement.getApps());
    }

}

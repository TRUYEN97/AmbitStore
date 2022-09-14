/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.App;

import Model.Source.Setting;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class AppPackage {

    private final List<AppElement> apps;
    private final Setting setting;

    public AppPackage() {
        this.apps = new ArrayList<>();
        this.setting = Setting.getInstance();
    }

    public void scanApps() {
        this.apps.clear();
        this.apps.addAll(new ScanApp(this.setting.getStoreFolder()).run());
    }

    public List<AppElement> getApps() {
        return new ArrayList<>(apps);
    }

}

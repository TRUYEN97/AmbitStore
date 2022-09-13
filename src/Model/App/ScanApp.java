/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.App;

import Model.AllKeyword;
import Model.Setting;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
class ScanApp {

    private final File storeFolder;

    public ScanApp(String storeFolder) {
        this.storeFolder = new File(storeFolder);
    }

    public List<AppElement> run() {
        List<AppElement> apps = new ArrayList<>();
        File[] folders = this.storeFolder.listFiles();
        for (File folder : folders) {
            if (folder.isHidden() || folder.isFile()) {
                continue;
            }
            if (isAppFolder(folder)) {
                apps.add(new AppElement(folder));
            }
        }
        return apps;
    }

    private boolean isAppFolder(File folder) {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().equalsIgnoreCase(Setting.getInstance().getRunFile())) {
                return true;
            }
        }
        return false;
    }

}

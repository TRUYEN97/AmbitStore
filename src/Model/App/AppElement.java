/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.App;

import Communicate.Cmd.Cmd;
import Model.Source.Setting;
import Robot.MyControl;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class AppElement {

    private final String name;
    private final File icon;
    private final File runFile;

    public AppElement(File folder) {
        this.name = folder.getName();
        this.icon = getIconin(folder);
        this.runFile = getRunfile(folder);
    }

    public String getName() {
        return name;
    }

    public File getIcon() {
        return icon;
    }

    public File getRunFile() {
        return runFile;
    }

    public void run() {
        if (runFile == null) {
            return;
        }
        MyControl myControl = new MyControl();
        if (!myControl.showWindows(myControl.findWindows(getName()))) {
            Cmd cmd = new Cmd();
            cmd.sendCommand(String.format("cd %s && %s",
                    runFile.getParent(), runFile.getName()));
        }
    }

    private File getRunfile(File folder) {
        if (folder == null) {
            return null;
        }
        return getFileIn(folder, Setting.getInstance().getRunFile());
    }

    private File getFileIn(File folder, String filePath) {
        File runF = new File(String.format("%s%s%s", folder.getPath(),
                File.separator, filePath));
        return runF.exists() ? runF : null;
    }

    private File getIconin(File folder) {
        if (folder == null) {
            return null;
        }
        return getFileIn(folder, Setting.getInstance().getAppIconPath());
    }

}

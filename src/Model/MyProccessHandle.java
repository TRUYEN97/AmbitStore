/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Communicate.Cmd.Cmd;
import Model.Source.Setting;
import Time.WaitTime.Class.TimeMs;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class MyProccessHandle {

    private final Cmd cmd;
    private final Setting setting;
    private String windowTitle;
    private String nameApp;
    private File runFile;

    public MyProccessHandle() {
        this.cmd = new Cmd();
        this.setting = Setting.getInstance();
    }

    public void setRunFile(File runFile, String nameApp) {
        this.runFile = runFile;
        this.nameApp = nameApp;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public boolean runApp() {
        if (runFile == null) {
            return false;
        }
        if (!isRuning()) {
            run();
        }
        return true;
    }

    public boolean shutdown() {
        if (!isUserWantClose()) {
            return false;
        }
        String killAppCmd = String.format("taskkill /fi \"STATUS eq RUNNING\" /fi \"WINDOWTITLE eq %s\"", this.windowTitle);
        cmd.sendCommand(killAppCmd);
        String response = cmd.readAll();
        if (response.startsWith("SUCCESS: Sent termination signal to")) {
            this.windowTitle = null;
            return true;
        }
        return false;
    }

    public boolean isRuning() {
        if (this.windowTitle == null) {
            return false;
        }
        String checkRunningCmd = String.format("tasklist.exe /nh "
                + "/fi \"STATUS eq RUNNING\" /fi \"WINDOWTITLE eq %s\"",
                this.windowTitle);
        if (!cmd.sendCommand(checkRunningCmd)) {
            return false;
        }
        String response = cmd.readAll();
        return response != null && !response.contains("INFO: No tasks are running which match the specified criteria.");
    }

    private boolean run() {
        if (runFile == null) {
            return false;
        }
        String afterDoc = runFile.getName().substring(runFile.getName().lastIndexOf(".") + 1);
        String windowTitleTemp = String.format("%s-%s", this.nameApp, System.currentTimeMillis());
        String runbat = this.setting.getRunCommand(afterDoc);
        String commandRun = String.format("cd %s && start \"%s\" %s %s %s",
                runFile.getParent(), windowTitleTemp, runbat, runFile.getName(), windowTitleTemp);
        if (!cmd.sendCommand(commandRun)) {
            return false;
        }
        cmd.readAll(new TimeMs(500));
        this.windowTitle = windowTitleTemp;
        return true;
    }

    private boolean isUserWantClose() throws HeadlessException {
        return JOptionPane.showConfirmDialog(null,
                String.format("%s has running!\r\nDo you want to disable this app?", nameApp),
                "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Application.AppEntity;

import Model.MyProccessHandle;
import Model.Socket.ThisClieant.ClientSender;
import Model.Source.Setting;
import Unicast.Server.ClientHandler;
import Unicast.commons.Interface.IListNumble;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Administrator
 */
public class AppEntity implements IappParameter {

    private final ClientSender sender;
    private final String project;
    private final String appName;
    private final String version;
    private String thisMD5;
    private final Backup backup;
    private final SourceRunFile sourceRunFile;
    private final MyProccessHandle myProccessHandle;
    private final IListNumble<String, String> onlineNumble;
    private String description;
    private final Timer timerCheck;

    public AppEntity(ClientSender sender, IListNumble<String, String> onlineNumble,
            String project, String appName, String appFileName,
            String vesion, File tempFolder, String description) throws Exception {
        this.sender = sender;
        this.onlineNumble = onlineNumble;
        String storeFolder = Setting.getInstance().getStoreFolder();
        if (project == null) {
            throw new Exception(String.format("project: null", project));
        }
        if (appName == null) {
            throw new Exception(String.format("App name: null", appName));
        }
        this.backup = new Backup(String.format("%s/%s/Backup",
                storeFolder, project));
        this.sourceRunFile = new SourceRunFile(String.format("%s/%s/App",
                storeFolder, project), appFileName);
        this.myProccessHandle = new MyProccessHandle();
        this.project = project;
        this.appName = appName;
        this.version = vesion;
        setBackupfodler(tempFolder, appName, appFileName, vesion, description);
        this.timerCheck = new Timer(10000, (ActionEvent e) -> {
            try {
                if (!checkIntegrityOfApp()) {
                    AppEntity.this.sender.update(AppEntity.this.project);
                }
                if (!myProccessHandle.isRuning()) {
                    AppEntity.this.timerCheck.stop();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppEntity.this.timerCheck.stop();
            }
        });
    }

    public final void setBackupfodler(File tempFolder, String appName, String appFileName,
            String version, String description) throws Exception {
        this.description = description;
        if (appName == null || !this.appName.equals(appName)) {
            throw new Exception(String.format("App name not match! %s - %s", this.appName, appName));
        }
        File[] zipFiles = tempFolder.listFiles();
        if (zipFiles == null || zipFiles.length <= 0) {
            throw new Exception(String.format("project: %S - backup folder is null or empty!", project));
        }
        this.backup.clear();
        this.backup.setAppBackupFolder(zipFiles);
        this.thisMD5 = this.backup.getMD5();
        if (!this.version.equals(version) || !isMD5SourceFileTrue()) {
            backupApp();
        }
    }

    private boolean backupApp() {
        boolean hasRuning = this.myProccessHandle.isRuning();
        boolean transerRs = transferFileBackupToSource();
        if (hasRuning) {
            this.myProccessHandle.runApp();
        }
        return transerRs;
    }

    private boolean transferFileBackupToSource() {
        try {
            if (this.myProccessHandle.isRuning()) {
                if (this.onlineNumble.hasOnline(this.myProccessHandle.getWindowTitle())) {
                    waitForAppClose();
                }
                this.myProccessHandle.shutdown();
                while (this.myProccessHandle.isRuning()) {
                    Thread.sleep(100);
                }
            }
            this.sourceRunFile.setAppSourceFolder(this.backup.getFolder());
            File runFile = this.sourceRunFile.getRunFile();
            if (runFile == null) {
                JOptionPane.showMessageDialog(null,
                        String.format("Not found the run file in %s app", project));
                return false;
            }
            this.myProccessHandle.setRunFile(runFile, appName);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getWindowTitle() {
        return this.myProccessHandle.getWindowTitle();
    }

    @Override
    public boolean isRuning() {
        return this.myProccessHandle.isRuning();
    }

    @Override
    public void runApp() {
        try {
            if (isMD5SourceFileTrue() || (isMD5BackupFileTrue() && transferFileBackupToSource())) {
                myProccessHandle.runApp();
                timerCheck.start();
            } else {
                sender.update(project);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public String getAppName() {
        return this.appName;
    }

    @Override
    public String getProjectName() {
        return this.project;
    }

    @Override
    public Icon getIcon() {
        return this.sourceRunFile.getIcon();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    private synchronized boolean checkIntegrityOfApp() throws Exception {
        if (isMD5SourceFileTrue()) {
            return true;
        }
        if (isMD5BackupFileTrue()) {
            backupApp();
            return true;
        }
        return false;
    }

    private boolean isMD5BackupFileTrue() throws NoSuchAlgorithmException, IOException {
        return this.thisMD5.equals(this.backup.getMD5());
    }

    private boolean isMD5SourceFileTrue() throws IOException, NoSuchAlgorithmException {
        return this.thisMD5.equals(this.sourceRunFile.getMD5());
    }

    private void waitForAppClose() {
        ClientHandler<String> handler = this.onlineNumble.getClientHandler(this.myProccessHandle.getWindowTitle());
    }

    @Override
    public boolean shutdown() {
        return this.myProccessHandle.shutdown();
    }

}

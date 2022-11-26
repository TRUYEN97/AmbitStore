/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Application;

import Communicate.FtpClient.FtpClient;
import FileTool.FileService;
import FileTool.MD5;
import Model.AllKeyword;
import Model.Source.Setting;
import Unicast.commons.Actions.MapRowsParameter;
import java.awt.HeadlessException;
import java.io.File;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class AppDownload {

    private final Setting setting;
    private final FileService fileService;
    private final MD5 md5;
    private final FtpClient ftpClient;
    private final AppPackage appPackage;

    public AppDownload(AppPackage appPackage) throws Exception {
        this.setting = Setting.getInstance();
        this.fileService = new FileService();
        this.appPackage = appPackage;
        this.md5 = new MD5();
        this.ftpClient = new FtpClient();
        if (!this.ftpClient.connect(setting.getString(AllKeyword.FTP.IP),
                setting.getInteger(AllKeyword.FTP.PORT))) {
            throw new Exception("connect to ftp failed!");
        }
        if (!this.ftpClient.login(setting.getString(AllKeyword.FTP.USER),
                setting.getString(AllKeyword.FTP.PASS))) {
            throw new Exception("login to ftp failed!");
        }
    }
    

    public void reDownload(MapRowsParameter programPackage) {
        for (HashMap<String, String> app : programPackage.getAll()) {
            String projectName = app.get(AllKeyword.APP.PROJECT_NAME);
            String appName = app.get(AllKeyword.APP.APP_NAME);
            File tempFolder = new File(String.format("%s/%s/%s",
                    this.setting.getTempFolder(), projectName, appName));
            try {
                downLoadApp(app, tempFolder);
                downLoadDefaultConfig(app, tempFolder);
                downloadConfig(app, tempFolder);
                reUpdateToAppPackage(app, tempFolder);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        String.format("Download %s failed!\r\n%s",
                                app.get(AllKeyword.APP.PROJECT_NAME),
                                ex.getLocalizedMessage()));
                ex.printStackTrace();
            } finally {
                this.fileService.deleteFolder(tempFolder.getParent());
            }
        }
    }

    private void reUpdateToAppPackage(HashMap<String, String> app, File tempFolder) throws Exception {
        String project = app.get(AllKeyword.APP.PROJECT_NAME);
        String appName = app.get(AllKeyword.APP.APP_NAME);
        String appFileName = app.get(AllKeyword.APP.APP_FILE_NAME);
        String defaultConfigName = app.get(AllKeyword.APP.DEFAULT_CONFIG_NAME);
        String configName = app.get(AllKeyword.APP.CONFIG_NAME);
        String version = String.format("%s-%s-%s", appName, defaultConfigName, configName);
        String description = createdDercription(app);
        this.appPackage.setBackupFolder(project, tempFolder, appName, appFileName, version, description);
    }

    private String createdDercription(HashMap<String, String> app) {
        String defaultConfigName = app.get(AllKeyword.APP.DEFAULT_CONFIG_NAME);
        String configName = app.get(AllKeyword.APP.CONFIG_NAME);
        String desription = String.format("%s\r\n- %s:\r\n\t%s\r\n- %s:\r\n\t%s",
                app.get(AllKeyword.APP.PROJECT_NAME),
                app.get(AllKeyword.APP.APP_NAME),
                app.get(AllKeyword.APP.APP_DESCRIPTION),
                defaultConfigName,
                app.get(AllKeyword.APP.DEFAULT_CONFIG_DESCRIPTION));
        if (defaultConfigName.equals(configName)) {
            return desription;
        }
        return desription.concat(String.format("\r\n- %s:\r\n\t%s",
                configName,
                app.get(AllKeyword.APP.CONFIG_DESCRIPTION)));
    }

    private void downloadConfig(HashMap<String, String> app, File localProject) throws HeadlessException, Exception {
        String configPath = app.get(AllKeyword.APP.CONFIG_PATH);
        String configMD5 = app.get(AllKeyword.APP.CONFIG_MD5);
        String configName = app.get(AllKeyword.APP.CONFIG_NAME);
        String defaultConfigName = app.get(AllKeyword.APP.DEFAULT_CONFIG_NAME);
        if (configName == null || configPath == null || configName.equals(defaultConfigName)) {
            return;
        }
        String localFolder = String.format("%s/%s", setting.getTempFolder(), System.currentTimeMillis());
        File configFolder = downLoadFromFTP(localFolder, configPath, configMD5);
        if (configFolder == null) {
            return;
        }
        this.fileService.moveFolder(configFolder, localProject);
        this.fileService.deleteFolder(localFolder);
    }

    private void downLoadDefaultConfig(HashMap<String, String> app, File localProject) throws Exception {
        String defaultConfigPath = app.get(AllKeyword.APP.DEFAULT_CONFIG_PATH);
        String defaultConfigMD5 = app.get(AllKeyword.APP.DEFAULT_CONFIG_MD5);
        String localFolder = String.format("%s/%s", setting.getTempFolder(), System.currentTimeMillis());
        File defaultConfigFolder = downLoadFromFTP(localFolder, defaultConfigPath, defaultConfigMD5);
        if (defaultConfigFolder == null) {
            return;
        }
        this.fileService.moveFolder(defaultConfigFolder, localProject);
        this.fileService.deleteFolder(localFolder);
    }

    private void downLoadApp(HashMap<String, String> app, File localProject) throws Exception {
        String appPath = app.get(AllKeyword.APP.APP_PATH);
        String appMD5 = app.get(AllKeyword.APP.APP_MD5);
        String localFolder = String.format("%s/%s", setting.getTempFolder(), System.currentTimeMillis());
        File appFolder = downLoadFromFTP(localFolder, appPath, appMD5);
        if (appFolder == null) {
            return;
        }
        this.fileService.moveFolder(appFolder, localProject);
        this.fileService.deleteFolder(localFolder);
    }

    private File downLoadFromFTP(String localFolder, String path, String MD5) throws Exception {
        File zipTempFolder = new File(String.format("%s/%s", localFolder, path));
        if (!this.ftpClient.downloadFile(path, zipTempFolder.getPath())) {
            throw new Exception(String.format("download %s from FTP failed!", path));
        }
        if (!this.md5.isTrueMD5(zipTempFolder, MD5)) {
            this.fileService.deleteFolder(localFolder);
            throw new Exception(String.format("check MD5 of %s Failed!", path));
        }
        return zipTempFolder;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Application.AppEntity;

import FileTool.FileService;
import FileTool.MD5;
import FileTool.Zip;
import Model.Source.Setting;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Administrator
 */
class SourceRunFile {

    private final File appFolder;
    private File runFile;
    private final List<File> appFiles;
    private final FileService fileService;
    private final String appFileName;
    private final Zip zip;
    private final MD5 md5;
    private final Setting setting;

    SourceRunFile(String sourceFolder, String name) {
        this.appFolder = new File(sourceFolder);
        this.appFiles = new ArrayList<>();
        this.fileService = new FileService();
        this.appFileName = name;
        this.md5 = new MD5();
        this.zip = new Zip();
        this.setting = Setting.getInstance();
    }

    File getRunFile() {
        return this.runFile;
    }

    Icon getIcon() {
        String iconPath = String.format("%s/%s", this.appFolder, this.setting.getAppIconPath());
        if (new File(iconPath).exists()) {
            return new ImageIcon(iconPath);
        }else{
            return FileSystemView.getFileSystemView().getSystemIcon(runFile);
        }
    }

    void setAppSourceFolder(File source) throws Exception {
        copyToAppFolder(source);
        getAllOriginFiles();
        for (File appFile : appFiles) {
            if (appFile.getName().equals(this.appFileName)) {
                this.runFile = appFile;
                return ;
            }
        }
        this.runFile = null;
    }

    String getMD5() throws IOException, NoSuchAlgorithmException {
        return this.md5.MD5(this.appFiles);
    }

    private void getAllOriginFiles() {
        this.appFiles.clear();
        this.appFiles.addAll(this.fileService.getAllFile(appFolder));
    }

    private void copyToAppFolder(File source) throws IOException {
        this.fileService.deleteFolder(this.appFolder);
        this.fileService.copyFilesInDirectory(source, appFolder);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Application.AppEntity;

import FileTool.FileService;
import FileTool.MD5;
import FileTool.Zip;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
class Backup {

    private final File backupFolder;
    private final FileService fileService;
    private final Zip zip;
    private final MD5 md5;

    Backup(String backupFolder) {
        this.backupFolder = new File(backupFolder);
        this.fileService = new FileService();
        this.md5 = new MD5();
        this.zip = new Zip();
    }

    void clear() {
        this.fileService.deleteFolder(this.backupFolder);
    }

    void setAppBackupFolder(File[] zipFiles) throws Exception {
        for (File zipFile : zipFiles) {
            if (!this.zip.unzip(zipFile, this.backupFolder)) {
                throw new Exception(String.format(" %s\r\nUnzip backup folder failed!",
                      zipFile));
            }
        }
    }

    public File getFolder() {
        return backupFolder;
    }
    
    String getMD5() throws IOException, NoSuchAlgorithmException {
        return this.md5.MD5(this.backupFolder);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Communicate.Cmd.Cmd;
import Communicate.FtpClient.FtpClient;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class Servants {

    private final FtpClient ftpClient;
    private final Cmd cmd;
    private String ftpHost;

    public Servants() {
        this.ftpClient = new FtpClient();
        this.cmd = new Cmd();
    }

    public boolean runCmd(String command) {
        return cmd.sendCommand(command);
    }

    public void ftpDownload(Map<String, String> paths) {
        for (String key : paths.keySet()) {
            ftpDownload(key, paths.get(key));
        }
    }
    
    public void ftpDownload(String ftpPath, String localFile) {
        ftpClient.connect(ftpPath, 0);
        if(ftpClient.downloadFile(ftpPath, localFile)){
            System.out.println(ftpPath+" "+localFile);
        }
    }
}

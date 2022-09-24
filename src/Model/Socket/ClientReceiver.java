/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Socket;

import FileTool.FileService;
import Model.Loger;
import Model.Servants;
import Unicast.commons.Actions.FileTransfer;
import Unicast.commons.Actions.Object.ObjectPackage;
import Unicast.commons.Actions.simplePackage;
import Unicast.commons.Interface.IObjectReceiver;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class ClientReceiver implements IObjectReceiver<simplePackage> {
   
    private final FileService fileService;
    private final Servants servants;

    public ClientReceiver(Servants servants) {
        this.fileService = new FileService();
        this.servants = servants;
    }

    @Override
    public void receiver(simplePackage pkg) {
        switch (pkg.getAction()) {
            case WHO_ARE_U -> {
                this.servants.sendMyName();
            }
            
            case RUN -> {
                ObjectPackage<String> command = (ObjectPackage<String>) pkg;
                servants.runCmd(command.getdata());
            }
            
            case MESSAGE -> {
                ObjectPackage<String> command = (ObjectPackage<String>) pkg;
                JOptionPane.showMessageDialog(null, command.getdata());
            }
            
            case GOOD_BYE -> {
                System.out.println("bye bye");
            }
            
            case FILE_TRANSFER -> {
                FileTransfer fileTransfer = (FileTransfer) pkg;
                byte[] data = fileTransfer.getData();
                String path = fileTransfer.getFilePath();
                long length = fileTransfer.getLength();
                if (path == null || length != data.length) {
                    return;
                }
                Loger.getInstance().addLog("FILE_TRANSFER", String.format("%s - %s", path, 
                this.fileService.saveFile(path, data)));
            }
            
            case DOWN_LOAD -> {
                ObjectPackage<Map<String, String>> fileTransfer = (ObjectPackage<Map<String, String>>) pkg;
                servants.ftpDownload(fileTransfer.getdata());
            }

        }
    }

}

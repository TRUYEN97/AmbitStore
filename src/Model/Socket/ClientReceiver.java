/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Socket;

import FileTool.FileService;
import Model.PcInformation;
import Model.Servants;
import Unicast.commons.Actions.FileTransfer;
import Unicast.commons.Actions.Object.MyName;
import Unicast.commons.Actions.Object.ObjectPackage;
import Unicast.commons.Actions.simplePackage;
import Unicast.commons.Interface.ISend;
import Unicast.commons.Interface.IObjectReceiver;
import View.UI;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class ClientReceiver implements IObjectReceiver<simplePackage> {

    private ISend<simplePackage> handler;
    private final UI display;
    private final FileService fileService;
    private final Servants servants;

    public ClientReceiver(Servants servants, UI display) {
        this.display = display;
        this.fileService = new FileService();
        this.servants = servants;
    }

    @Override
    public void receiver(simplePackage pkg) {
        switch (pkg.getAction()) {
            case WHO_ARE_U -> {
                PcInformation pcInfo = PcInformation.getInstance();
                this.handler.send(new MyName(
                        pcInfo.getComputerName(),
                        pcInfo.getSystemName(),
                        pcInfo.getLine(),
                        pcInfo.getAllHardwareAddress()));
            }
            
            case RUN -> {
                ObjectPackage<String> command = (ObjectPackage<String>) pkg;
                servants.runCmd(command.getdata());
            }
            
            case GOOD_BYE -> {
                System.exit(0);
            }
            
            case FILE_TRANSFER -> {
                FileTransfer fileTransfer = (FileTransfer) pkg;
                byte[] data = fileTransfer.getData();
                String path = fileTransfer.getFilePath();
                long length = fileTransfer.getLength();
                if (path == null || length != data.length) {
                    return;
                }
                this.fileService.saveFile(path, data);
            }
            
            case DOWN_LOAD -> {
                ObjectPackage<Map<String, String>> fileTransfer = (ObjectPackage<Map<String, String>>) pkg;
                servants.ftpDownload(fileTransfer.getdata());
            }

        }
    }

    @Override
    public void setHandler(ISend<simplePackage> handler) {
        this.handler = handler;
    }

}

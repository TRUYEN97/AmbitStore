/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Client;

import Communicate.Cmd.Cmd;
import FileTool.FileService;
import Model.PcInformation;
import Unicast.commons.Actions.FileTransfer;
import Unicast.commons.Actions.Object.List.ListPackage;
import Unicast.commons.Actions.Object.MyName;
import Unicast.commons.Actions.Object.ObjectPackage;
import Unicast.commons.Actions.SimplePackage;
import Unicast.commons.Interface.ISend;
import Unicast.commons.Interface.IObjectReceiver;
import View.UI;

/**
 *
 * @author Administrator
 */
public class ClientReceiver implements IObjectReceiver<SimplePackage> {

    private ISend<SimplePackage> handler;
    private final UI display;
    private final FileService fileService;

    public ClientReceiver(UI display) {
        this.display = display;
        this.fileService = new FileService();
    }

    @Override
    public void receiver(SimplePackage pkg) {
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
                ObjectPackage<String> list = (ObjectPackage<String>) pkg;
                new Cmd().sendCommand(list.getdata());
            }
            case FILE_TRANSFER -> {
                FileTransfer fileTransfer = (FileTransfer) pkg;
                byte[] data = fileTransfer.getData();
                String path = fileTransfer.getFilePath();
                if (path == null) {
                    return;
                }
                this.fileService.saveFile(path, data);
            }
            case DOWN_LOAD -> {
                ListPackage<String> fileTransfer = (ListPackage<String>) pkg;
                
            }
            default ->
                throw new AssertionError();
        }
    }

    @Override
    public void setHandler(ISend<SimplePackage> handler) {
        this.handler = handler;
    }

}

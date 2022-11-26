/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.Application.AppEntity;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrator
 */
public interface IappParameter {

    String getAppName();

    String getProjectName();

    Icon getIcon();
    
    String getDescription();

    void runApp();

    boolean isRuning();
    
    boolean shutdown();
}

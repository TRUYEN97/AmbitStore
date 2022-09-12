/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.Image;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class AppParamater {

    private final Image icon;
    private final String name;
    private final File appPath;

    public AppParamater(Image icon,String name, File appPath) {
        this.icon = icon;
        this.name = name;
        this.appPath = appPath;
    }
    
    public Image getIcon() {
        return icon;
    }

    public String getAppName() {
        return name;
    }

    public File getAppFile() {
        if(appPath == null || !appPath.exists() || appPath.isDirectory()){
            return null;
        }
        return appPath;
    }
}

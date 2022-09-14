/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Control.Engine;
import Model.Source.Setting;
import Robot.MyControl;
import Robot.W32API.HWND;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class Main {

    public static void main(String[] args) {

        String version = Setting.getInstance().getVersion();
        String title = String.format("AmbitStore - %s", version == null ? "" : version);
        MyControl control = new MyControl();
        HWND hwnd = control.findWindows(title);
        if (hwnd != null) {
            return;
        }
        try {
            new Engine(title).run();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

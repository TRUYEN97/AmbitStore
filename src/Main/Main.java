/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Control.Engine;
import Model.Loger;
import Model.MyProccessHandle;
import Model.Source.Setting;
import Robot.MyControl;
import Robot.W32API.HWND;
import Time.TimeBase;

/**
 *
 * @author Administrator
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        String version = Setting.getInstance().getVersion();
        String title = String.format("AmbitStore - %s", version == null ? "" : version);
        MyControl control = new MyControl();
        HWND hwnd = control.findWindows(title);
        if (hwnd != null) {
            return;
        }
        try {
            String filePath = String.format("log/%s.txt", new TimeBase(TimeBase.UTC).getDate());
            Loger.getInstance().init(filePath);
            new Engine(title).run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

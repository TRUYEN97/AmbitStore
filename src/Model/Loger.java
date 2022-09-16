/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import MyLoger.MyLoger;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class Loger {

    private static volatile Loger log;
    private final MyLoger myLoger;

    public Loger() {
        this.myLoger = new MyLoger();
    }

    public static Loger getInstance() {
        Loger myLog = Loger.log;
        if (myLog == null) {
            synchronized (Loger.class) {
                myLog = Loger.log;
                if (myLog == null) {
                    Loger.log = myLog = new Loger();
                }
            }
        }
        return myLog;
    }

    public void init(String filePath) throws IOException {
        this.myLoger.begin(new File(filePath), true);
    }

    public void addLog(String log) {
        try {
            this.myLoger.addLog(log);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addLog(String key, String log) {
        try {
            this.myLoger.addLog(key, log);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

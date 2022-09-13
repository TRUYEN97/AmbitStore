/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.Setting;
import View.UI;

/**
 *
 * @author Administrator
 */
public class Engine {
    private final UI ui;
    private final Core core;

    public Engine() {
        Setting.getInstance();
        this.ui = new UI();
        this.core = new Core(this.ui);
    }

    public void run() {
        this.core.run();
        this.ui.display();
    }
    
}

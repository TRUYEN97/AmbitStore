/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Source;

import FileTool.FileService;
import Model.AllKeyword;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.nio.file.FileSystemNotFoundException;

/**
 *
 * @author Administrator
 */
public class Setting {

    private static volatile Setting instance;
    private final JSONObject setting;
    private final FileService fileService;

    private Setting() {
        this.setting = new JSONObject();
        this.fileService = new FileService();
        init(new File("bin/Config/setting.json"));
    }
    
    public static Setting getInstance()
    {
        Setting ins = Setting.instance;
        if (ins == null) {
            synchronized (Setting.class) {
                ins = Setting.instance;
                if (ins == null) {
                    Setting.instance = ins = new Setting();
                }
            }
        }
        return ins;
    }

    private void init(File file) {
        if (!file.exists()) {
            throw new FileSystemNotFoundException(file.getPath());
        }
        try {
            setting.clear();
            setting.putAll(JSONObject.parseObject(fileService.readFile(file)));
        } catch (Exception e) {
            throw new RuntimeException("init setting failed");
        }
    }

    public Integer getInteger(String key) {
        return this.setting.getInteger(key);
    }
    
    public Integer getGirdRow() {
        return this.setting.getInteger(AllKeyword.GRID_ROW);
    }
    
    public Integer getGirdColumn() {
        return this.setting.getInteger(AllKeyword.GRID_COLUMN);
    }
    
    public String getString(String key) {
        return this.setting.getString(key);
    }

    public String getVersion() {
        return getString(AllKeyword.VERSION);
    }

    public String getStoreFolder() {
        return getString(AllKeyword.STORE_FOLDER);
    }

    public String getMyIconPath() {
        return getString(AllKeyword.MY_ICON_PATH);
    }
    
    public String getAppIconPath() {
        return getString(AllKeyword.APP_ICON_PATH);
    }
    
    public String getRunFile() {
        return getString(AllKeyword.RUN_FILE);
    }

    public String getTempFolder() {
        return getString(AllKeyword.TEMP_FOLDER);
    }

    public String getRunCommand(String afterDoc) {
        JSONObject appRunCmd = this.setting.getJSONObject(AllKeyword.APP_RUN_COMMAND);
        String cmd = appRunCmd.getString(afterDoc);
        return cmd == null? "": cmd;
    }

    public int getPortStoreServer() {
        return setting.getInteger(AllKeyword.SERVER.STORE_SERVER_PORT);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Administrator
 */
public final class AllKeyword {

    public static class FTP {

        private FTP() {

        }

        public static final String IP = "ftp ip";
        public static final String PORT = "ftp port";
        public static final String PASS = "ftp pass";
        public static final String USER = "ftp user";

    }

    public static class APP {

        private APP() {

        }

        public static final String PROJECT_NAME = "Project name";
        public static final String APP_NAME = "App name";
        public static final String APP_FILE_NAME = "App file name";
        public static final String APP_MD5 = "App MD5";
        public static final String APP_DESCRIPTION = "App discription";
        public static final String APP_PATH = "App path";
        public static final String DEFAULT_CONFIG_NAME = "Defautl config name";
        public static final String DEFAULT_CONFIG_MD5 = "Default config MD5";
        public static final String DEFAULT_CONFIG_DESCRIPTION = "Default config Discription";
        public static final String DEFAULT_CONFIG_PATH = "Default config path";
        public static final String CONFIG_NAME = "Config name";
        public static final String CONFIG_MD5 = "Config MD5";
        public static final String CONFIG_DESCRIPTION = "Config Discription";
        public static final String CONFIG_PATH = "Config path";

    }

    private AllKeyword() {
    }
    public static final String APP_RUN_COMMAND = "app_run_cmd";
    public static final String VERSION = "version";
    public static final String STORE_FOLDER = "store";
    public static final String TEMP_FOLDER = "temp";
    public static final String RUN_FILE = "run_app";
    public static final String APP_ICON_PATH = "app_icon";
    public static final String MY_ICON_PATH = "my_icon";
    public static final String GRID_ROW = "grid_row";
    public static final String GRID_COLUMN = "grid_column";

    public static class SERVER {

        private SERVER() {
        }
        public static final String SERVER_HOST = "server_host";
        public static final String SERVER_PORT = "server_port";
        public static final String STORE_SERVER_PORT = "store_server_port";
    }
}

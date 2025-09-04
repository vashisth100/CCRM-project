package edu.ccrm.config;

public class AppConfig {
    private static AppConfig instance;
    private String dataFolder = "data";

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public void loadConfig() {
        System.out.println("Loaded AppConfig. Data folder: " + dataFolder);
    }
    public String getDataFolder() {
        return dataFolder;
    }
}

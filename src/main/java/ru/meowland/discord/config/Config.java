package ru.meowland.discord.config;

import arc.files.Fi;
import arc.struct.ObjectMap;
import jdk.incubator.jpackage.internal.Log;
import ru.meowland.MeowlandPlugin;

public class Config {
    public static final String fileName = "config.properties";
    public static final Fi file = MeowlandPlugin.pluginDir.child(fileName);

    private static ObjectMap<String, String> config;

    public static void init(){
        if(!file.exists()){
            MeowlandPlugin.pluginDir.mkdirs();
            ResourceUtil.copy(fileName, file);

            Log.info("Конфиги сексесфул сгенерированы  в " + file.path());
        }
    }
    public static String get(String key){
        return config.get(key);
    }
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}

package ru.meowland.config;

import arc.Core;
import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.util.Log;
import arc.util.io.PropertiesUtils;
import mindustry.gen.Player;
import org.yaml.snakeyaml.Yaml;
import ru.meowland.MeowlandPlugin;
import ru.meowland.commands.AdminCommands;
import ru.meowland.utils.ResourceUtil;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Bundle {
    /** @author Fatonn Developer */



    public static final String[] langList = {"en_US", "ru_RU", "zn_CN"};
    public static final Fi langDir = MeowlandPlugin.pluginDir.child("lang");

    public static String selectedLang;
    public static String nyaLang;
    public static Fi file;
    private static Fi nyaFile;
    private static ObjectMap<String, String> nyaProperties;

    private static ObjectMap<String, String> properties;

    public static void init(){
        Log.info("Meowland plugin init");
        generate();

        selectedLang = Config.get("language");
        file = langDir.child(selectedLang + ".properties");

        properties = new ObjectMap<>();
        nyaProperties = new ObjectMap<>();
        PropertiesUtils.load(
                properties, file.reader()
        );
    }

    private static void generate(){
        for (String lang : langList){
            final String langPath = "lang/" + lang + ".properties";
            final Fi file = MeowlandPlugin.pluginDir.child(langPath);

            if(file.exists()){
                continue;
            }
            ResourceUtil.copy(langPath, file);
        }
    }

    public static String get(String key){
        return properties.get(key);
    }

    public static String get(String key, Player player){
        if (player.locale.equals("ru")){
            nyaLang = "ru_RU";
        } else {
            nyaLang = "en_US";
        }
        nyaFile = langDir.child(nyaLang + ".properties");

        return nyaProperties.get(key);
    }



}



package ru.meowland.config;

import arc.Core;
import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.util.Log;
import arc.util.io.PropertiesUtils;
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



    public static final String[] langList = {"en_US", "ru_RU"};
    public static final Fi langDir = MeowlandPlugin.pluginDir.child("lang");

    public static String seelctedLang;
    public static Fi file;

    private static ObjectMap<String, String> properties;

    public static void init(){
        Log.info("init b");
        generate();

        seelctedLang = Config.get("lang");
        file = langDir.child(seelctedLang + ".properties");

        properties = new ObjectMap<>();
        PropertiesUtils.load(
                properties, file.reader()
        );
    }

    public static void generate(){
        for (String lang : langList){
            Log.info("meow");
            final String langPath = "land/" + lang + ".properties";
            final Fi file = MeowlandPlugin.pluginDir.child(langPath);

            if(file.exists()){
                continue;
            }
            ResourceUtil.copy(langPath, file);
        }
    }

    public static String get(String key, String... replace){
        String value = properties.get(key);

        int i = 0;
        for(String to : replace){
            value = value.replace("{"+i+"}",to);
            i++;
        }
        return value;
    }

}



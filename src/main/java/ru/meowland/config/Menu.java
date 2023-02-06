package ru.meowland.config;

import arc.files.Fi;
import arc.util.Log;
import mindustry.gen.Player;
import ru.meowland.MeowlandPlugin;
import ru.meowland.utils.ResourceUtil;

public class Menu {
    public static final String[] langList = {"ru", "en"};
    private static Fi file;

    public static void init(){
        Log.info("Menu config init");
        generate();
    }

    private static void generate(){
        for(String lang : langList){
            final String langPatch = "lang/menu_" + lang + ".txt";
            final Fi file = MeowlandPlugin.pluginDir.child(langPatch);
            if(file.exists()){
                continue;
            }
            ResourceUtil.copy(langPatch, file);
        }
    }

    public static String get(Player player){
        String value;
        if(player.locale.equals("ru")){
            final String langPatch = "lang/menu_" + "ru" + ".txt";
            file = MeowlandPlugin.pluginDir.child(langPatch);
            value = file.readString();
        } else {
            final String langPatch = "lang/menu_" + "en" + ".txt";
            file = MeowlandPlugin.pluginDir.child(langPatch);
            value = file.readString();
        }
        return value;
    }

}

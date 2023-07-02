package ru.meowland.config;

import arc.Core;
import arc.util.Log;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Config {
    private static final String config =
            "\n#Plugin language en_US/ru_RU/zh_CN"
            + "\nlanguage: en_US"
            + "\ndiscord_link: https://discord.gg/enJVFYcK"
            + "\n#Discord integration settings"
            + "\nserver_name: server name"
            + "\nbot_enable: true"
            + "\nbot_token: token"
            + "\nchannel_id: id"
            + "\nadmin_role: id"
            + "\nadmin_channel: id"
            + "\nbans_channel: id"
            //+ "\nbot_prefix: m!"
            + "\n#Mindustry settings"
            + "\n#Permissions."
            + "\n#If it is true then the players will be able to use the command."
            + "\n#If it is false then only admins will can able to use the command."
            + "\ndespw: false"
            + "\nspawn: false"
            + "\nteam: false"
            + "\nshiza: true"
            + "\nrtv: true"
            + "\nwave: true"
            + "\nspawncore: false"
            + "\njs: false"
            + "\neffect: false"
            + "\nsetblock: false"
            + "\nadvertisement: false"
            + "\n"
            + "\n#Commands settings"
            + "\nspawn-limit: 15"
            + "\nhistory_enabled: true"
            + "\n"
            + "\n#Data base"
            + "\n"
            + "\n";
    private static Map<String, Object> obj;

    public void loadConfig() {
        if(!Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").exists()){
            Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").writeString(config);
            Log.info("Meowland: config created");
        }else {
            Log.info("Meowland: config loaded");
        }

        Log.info("Meowland: patch to config: " + Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").toString());
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
    }

    public Config() {
        this.obj = obj;
    }

    public static String get(String meow){
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        return obj.get(meow).toString();
    }




}


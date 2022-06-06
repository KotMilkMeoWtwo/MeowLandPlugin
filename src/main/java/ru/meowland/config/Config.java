package ru.meowland.config;

import arc.Core;
import arc.util.Log;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Config {
    private static String config = ""
            + "\n#Plugin language en_US/ru_RU"
            + "\nlanguage: en_US"
            + "\n#Discord integration settings"
            + "\nenable: true"
            + "\nwebhook_url: url"
            + "\nchannel_id: id"
            + "\navatar_url: url"
            + "\nserver_name: server name"
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
            + "\n#Commands settings"
            + "\nspawn-limit: 15"
            + "\n"
            + "\n"
            + "\n"
            + "\n"
            + "\n";
    private static Map<String, Object> obj;

    public String webhook_url;
    public String channel_id;
    public String avatar_url;
    public String server_name;
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
        webhook_url = obj.get("webhook_url").toString();
        channel_id = obj.get("channel_id").toString();
        avatar_url = obj.get("avatar_url").toString();
        server_name = obj.get("server_name").toString();
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


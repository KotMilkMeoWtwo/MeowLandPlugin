package ru.meowland.config;

import arc.Core;
import arc.util.Log;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Config {
    private static String config = ""
            + "#Discord integration settings"
            + "\nenable: true"
            + "\nwebhook_url: url"
            + "\nchannel_id: id"
            + "\navatar_url: url"
            + "\nserver_name: server name"
            + "\n"
            + "\n";
    private Map<String, Object> obj;

    public String webhook_url;
    public String channel_id;
    public String avatar_url;
    public String server_name;
    public void loadConfig() {
        if(!Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").exists()){
            Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").writeString(config);
            Log.info("Meowland: Конфиг сгенерирован");
        }else {
            Log.info("Meowland: Конфиг загружен");
        }

        Log.info("Meowland: Путь к конфигу MeowLand: " + Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").toString());
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


}


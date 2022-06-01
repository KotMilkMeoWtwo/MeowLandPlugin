package ru.meowland.config;

import arc.Core;
import arc.util.Log;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Config {
    private static String config = ""
            + "webhook_url: url"
            + "\nchannel_id: id"
            + "\navatar_url: url"
            + "\nserver_name: server name";
    private Map<String, Object> obj;

    public String webhook_url;
    public String channel_id;
    public String avatar_url;
    public String server_name;
    public void loadConfig() {
        if(!Core.settings.getDataDirectory().child("config.yml").exists()){
            Core.settings.getDataDirectory().child("config.yml").writeString(config);
            Log.info("Конфиг сгенерирован");
        }else {
            Log.info("Конфиг загружен");
        }

        Log.info("Путь к конфигу MeowLand: " + Core.settings.getDataDirectory().child("config.yml").toString());
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("config.yml").readString()));
        webhook_url = obj.get("webhook_url").toString();
        channel_id = obj.get("channel_id").toString();
        avatar_url = obj.get("avatar_url").toString();
        server_name = obj.get("server_name").toString();
    }

    public Config() {
        this.obj = obj;
    }


}


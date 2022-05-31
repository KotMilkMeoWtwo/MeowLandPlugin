package ru.meowland.discord.config;

import arc.Core;
import arc.util.Log;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Config {
    private static String config = "webhook_url: url"+
            "\nchannel_id: id";
    private Map<String, Object> obj;

    public String webhook_url;

    public void loadConfig() {
        if(!Core.settings.getDataDirectory().child("config.yml").exists()){
            Core.settings.getDataDirectory().child("config.yml").writeString(config);
            Log.info("Конфиг сгенерирован");
        }else {
            Log.info("Конфиг загружен");
        }

        Log.info(Core.settings.getDataDirectory().child("config.yml").toString());
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("config.yml").readString()));
        webhook_url = obj.get("webhook_url").toString();
    }

    public Config() {
        this.obj = obj;
    }


}


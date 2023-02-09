package ru.meowland.discord;

import arc.Core;
import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.net.Administration;
import org.yaml.snakeyaml.Yaml;

import ru.meowland.config.Bundle;
import ru.meowland.config.Config;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

public class WebhookPlayerJoin {

    private String webhook_url;
    private String server_name;
    private String avatar_url;
    private String channel_id;
    private String enable;

    public void join(){
        Yaml yml = new Yaml();
        Map<String, Object> obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        webhook_url = obj.get("webhook_url").toString();
        channel_id = obj.get("channel_id").toString();
        avatar_url = obj.get("avatar_url").toString();
        server_name = obj.get("server_name").toString();
        Events.on(EventType.PlayerJoin.class, event ->{
            Player player = event.player;
            Administration.Config.showConnectMessages.set(false);

            String jsonBrut = "";
            jsonBrut += "{\"embeds\": "
                    + " \n["
                    + "     \n{"
                    + "         \n\"author\": {"
                    + "         \n\"name\": \"" + player.name + "\","
                    + "         \n\"icon_url\": \"https://github.com/Anuken/Mindustry/blob/master/core/assets-raw/sprites/units/gamma.png?raw=true\""
                    + "     \n},"
                    + "     \n\"description\": \"Join\","
                    + "     \n\"color\": 3211008"
                    + "     \n}"
                    + " \n],"
                    +"\"username\": \""+ server_name +"\","
                    + "\"title\": \""+ player.name +"\","
                    + "\"channel_id\": \""+ channel_id +"\","
                    + "\"avatar_url\": \""+ avatar_url +"\""
                    + "}";
            try {
                if(Config.get("webhook_enable").equals("true")){
                    URL url = new URL(webhook_url);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.addRequestProperty("Content-Type", "application/json");
                    con.addRequestProperty("meow", "nya");
                    con.setDoOutput(true);
                    con.setRequestMethod("POST");
                    OutputStream stream = con.getOutputStream();
                    stream.write(jsonBrut.getBytes());
                    stream.flush();
                    stream.close();
                    con.getInputStream().close();
                    con.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}

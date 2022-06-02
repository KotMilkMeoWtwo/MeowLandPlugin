package ru.meowland.discord;

import arc.Core;
import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Player;
import mindustry.net.Administration;
import org.yaml.snakeyaml.Yaml;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

public class ServerLoaded {

    private Map<String, Object> obj;
    private String enable;
    private String channel_id;
    private String avatar_url;
    private String webhook_url;
    private String server_name;

    public void serverLoad(){
        Events.on(EventType.ServerLoadEvent.class, event ->{
            Yaml yml = new Yaml();
            obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
            enable = obj.get("enable").toString();
            channel_id = obj.get("channel_id").toString();
            avatar_url = obj.get("avatar_url").toString();
            webhook_url = obj.get("webhook_url").toString();
            server_name = obj.get("server_name").toString();
            Administration.Config.showConnectMessages.set(false);
            String jsonBrut = "";

            jsonBrut += "{\"embeds\": "
                    + " \n[{"
                    + "     \n\"description\": \"loaded\","
                    + "     \n\"color\": \"3211008\""
                    + "     \n}"
                    + " \n],"
                    +"\"username\": \""+ server_name +"\","
                    + "\"title\": \"server\","
                    + "\"channel_id\": \""+ channel_id +"\","
                    + "\"avatar_url\": \""+ avatar_url +"\""
                    + "}";
            try {
                if(enable.equals("true")){
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

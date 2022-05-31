package ru.meowland.discord;

import arc.Core;
import arc.Events;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.net.Administration;
import org.yaml.snakeyaml.Yaml;
import ru.meowland.discord.config.Config;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

public class PlayerLeave {
    private String webhookUrl;
    private Map<String, Object> obj;
    public void leave(){
        Events.on(EventType.PlayerLeave.class, event ->{
            Yaml yml = new Yaml();
            obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("config.yml").readString()));
            webhookUrl = obj.get("webhook_url").toString();
            Player player = event.player;
            Administration.Config.showConnectMessages.set(false);
            Call.sendMessage("[lime]Игрок [#B](" + player.name + "[#B]) [lime]зашёл");
            String jsonBrut = "";
            jsonBrut += "{\"embeds\": [{"
                    + "\"title\": \""+ player.name +"\","
                    + "\"description\": \"Вышел\","
                    + "\"color\": 9109504"
                    + "}],"
                    +"\"username\": \""+ player.name +"\"}";
            try {
                URL url = new URL(webhookUrl);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

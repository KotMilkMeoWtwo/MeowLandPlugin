package ru.meowland.discord;

import arc.Core;
import arc.Events;
import arc.files.Fi;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.net.Administration;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

public class PlayerJoin {
    private String webhookUrl = "fOtE9u4wfalxSNVOdO1HgEAClKR0azn";
    public void join(){
        Events.on(EventType.PlayerConnect.class, event ->{
            Player player = event.player;
            Administration.Config.showConnectMessages.set(false);
            Call.sendMessage("[lime]Игрок [#B](" + player.name + "[#B]) [lime]зашёл");
            String jsonBrut = "";
            jsonBrut += "{\"embeds\": [{"
                    + "\"title\": \""+ player.name +"\","
                    + "\"description\": \"Зашёл\","
                    + "\"color\": 15258703"
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

package ru.meowland.discord;

import arc.Events;
import arc.struct.ObjectMap;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import ru.meowland.config.Config;

import javax.security.auth.login.LoginException;

public class Bot {

    public void bot() throws LoginException {
        if(Config.get("bot_enable").equals("true")){
            Log.info("Meowland: bot started");
            JDABuilder builder = JDABuilder.createDefault(Config.get("bot_token"));
            builder.setActivity(Activity.watching(String.valueOf( (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory())/1024/1024 + "Mb/" + Runtime.getRuntime().maxMemory()/1024/1024 + "Mb")));
            builder.build();
            Events.on(EventType.PlayerConnect.class, event ->{
                Player player = event.player;
                TextChannel channel =  Configя.getChannel("channel_id");
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(player.name);
                embed.setTitle(Config.get("server_name"));
                embed.addField("meow", "test", true);
                channel.sendMessageEmbeds(embed.build());
            });
        }
    }
}

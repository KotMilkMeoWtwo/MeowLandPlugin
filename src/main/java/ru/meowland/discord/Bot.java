package ru.meowland.discord;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.GuildMessageChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {


    public void bot() throws LoginException {
        if(Config.get("bot_enable").equals("true")){
            Log.info("Meowland: bot started");
            JDA jda = JDABuilder.createDefault(Config.get("bot_token")).build();
            JDABuilder builder = JDABuilder.createDefault(Config.get("bot_token")).addEventListeners(new Bot());
            builder.setActivity(Activity.watching(String.valueOf( (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory())/1024/1024 + "Mb/" + Runtime.getRuntime().maxMemory()/1024/1024 + "Mb")));
            builder.build();
            Events.on(EventType.PlayerConnect.class, event ->{
                Player player = event.player;
                GuildMessageChannel ch = jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id"));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(Config.get("server_name"));
                eb.addField(player.name, Bundle.get("discord.join"),  false);
                eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()+1), false);
                eb.setColor(3211008);
                ch.sendMessageEmbeds(eb.build()).queue();
            });
            Events.on(EventType.PlayerLeave.class, event -> {
                Player player = event.player;
                GuildMessageChannel ch = jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id"));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(Config.get("server_name"));
                eb.addField(player.name, Bundle.get("discord.leave"), false);
                eb.setColor(9109504);
                eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()-1), false);
                ch.sendMessageEmbeds(eb.build()).queue();
            });
            Events.on(EventType.PlayerChatEvent.class, event ->{
                Player player = event.player;
                GuildMessageChannel ch = jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id"));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(Config.get("server_name"));
                eb.addField(player.name, Bundle.get("discord.send") + event.message, false);
                eb.setColor(15258703);
                eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()), false);
                ch.sendMessageEmbeds(eb.build()).queue();
            });
        }
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Message msg = event.getMessage();
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "send") && !msg.getAuthor().isBot()){
            MessageChannel channel = event.getChannel();
            channel.sendMessage(Bundle.get("discord.sends")).queue();
            Call.sendMessage("[blue]Discord[] " + msg.getAuthor().toString().replace("U:", "").replaceAll("[\\(1234567890\\)]", "") + ": " + msg.getContentRaw().replace(Config.get("bot_prefix") + "send", ""));
        }
    }
}

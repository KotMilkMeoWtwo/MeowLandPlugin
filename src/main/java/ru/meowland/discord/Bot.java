package ru.meowland.discord;

import arc.Events;
import arc.util.Log;
import mindustry.core.NetServer;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;

import javax.security.auth.login.LoginException;
import java.util.Objects;

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

        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "players") && !msg.getAuthor().isBot()){
            MessageChannel channel = event.getChannel();
            StringBuilder builder = new StringBuilder();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(Config.get("server_name"));
            eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()), false);
            Groups.player.each(p ->{
                builder.append(p.name).append(" uuid: ").append(p.uuid()).append(" admin: "+ p.admin()).append("\n");
            });
            eb.addField(Bundle.get("discord.players"), builder.toString(), false);
            channel.sendMessageEmbeds(eb.build()).queue();
        }
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "ban") && !msg.getAuthor().isBot() && event.isFromType(ChannelType.PRIVATE)){
            NetServer server = new NetServer();
            MessageChannel channel = event.getChannel();
            server.admins.banPlayer(msg.getContentRaw().replace(Config.get("bot_prefix") + "ban", ""));
            channel.sendMessage(Bundle.get("commands.successful"));
        }
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "unban") && !msg.getAuthor().isBot() && event.isFromType(ChannelType.PRIVATE)){
            NetServer server = new NetServer();
            MessageChannel channel = event.getChannel();
            server.admins.banPlayer(msg.getContentRaw().replace(Config.get("bot_prefix") + "unban", ""));
            channel.sendMessage(Bundle.get("commands.successful"));
        }
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "help") && !msg.getAuthor().isBot()){
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(Bundle.get("discord.help"));
            eb.addField("1", "m!players - <later>", false);
            eb.addField("2.", "m!players", false);
            eb.addField("3.", "m!ban", false);
            eb.addField("4.", "m!unban", false);
            eb.addField("5.", "m!add_map", false);
        }
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "add_map") && !msg.getAuthor().isBot()){

        }

    }
}

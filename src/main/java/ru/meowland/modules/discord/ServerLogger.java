package ru.meowland.modules.discord;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;

public class ServerLogger {
    public static void init(){
        Log.info(Config.get("channel_id"));
        Events.on(EventType.PlayerJoin.class, event ->{
            Player player = event.player;
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(Config.get("server_name"));
            eb.addField(player.name, Bundle.get("discord.join"),  false);
            eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()), false);
            eb.setColor(3211008);
            GuildMessageChannel ch = Bot.jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id").replaceAll(" ", ""));
            assert ch != null;
            ch.sendMessageEmbeds(eb.build()).queue();
        });
        Events.on(EventType.PlayerLeave.class, event -> {
            Player player = event.player;
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(Config.get("server_name"));
            eb.addField(player.name, Bundle.get("discord.leave"), false);
            eb.setColor(9109504);
            eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()-1), false);
            GuildMessageChannel ch = Bot.jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id").replaceAll(" ", ""));
            assert ch != null;
            ch.sendMessageEmbeds(eb.build()).queue();
        });
        Events.on(EventType.PlayerChatEvent.class, event ->{
            Player player = event.player;
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(Config.get("server_name"));
            eb.addField(player.name, Bundle.get("discord.send") + event.message, false);
            eb.setColor(15258703);
            eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()), false);
            GuildMessageChannel ch = Bot.jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id").replaceAll(" ", ""));
            assert ch != null;
            ch.sendMessageEmbeds(eb.build()).queue();
        });
    }
}

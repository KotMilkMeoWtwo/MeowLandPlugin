package ru.meowland.discord.commands;

import arc.util.Log;
import mindustry.gen.Groups;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.discord.Bot;

import java.util.ArrayList;
import java.util.List;

public class Command extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equals(Config.get("server_name").toLowerCase())){
            OptionMapping optionMapping = event.getOption("cmd");
            String type = optionMapping.getAsString();
            switch (type){
                case "send" -> {

                }
                case "players" -> {
                    StringBuilder builder = new StringBuilder();
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle(Config.get("server_name"));
                    eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()), false);
                    Groups.player.each(p ->{
                        builder.append(p.name).append(" uuid: ").append(p.uuid()).append(" admin: ").append(p.admin()).append("\n");
                    });
                    eb.addField(Bundle.get("discord.players"), builder.toString(), false);
                    event.replyEmbeds(eb.build()).queue();
                }
                case "ban" -> {

                }
                case "unban" -> {

                }
                case "add_map" -> {

                }
                case "maps" -> {

                }
            }

        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        OptionData optionData = new OptionData(OptionType.STRING, "cmd", "cmd what bot will do", true)
                .addChoice("Send message to server", "send")
                .addChoice("List of players", "players")
                .addChoice("Ban player", "ban")
                .addChoice("Unban player", "unban")
                .addChoice("Add map to server", "add_map")
                .addChoice("List maps of server", "maps");
        Bot.jda.upsertCommand(Config.get("server_name").toLowerCase(), "all cmds").addOptions(optionData).queue();
    }
}

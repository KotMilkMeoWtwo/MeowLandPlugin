package ru.meowland.discord.commands;

import mindustry.core.NetServer;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.discord.Bot;

import java.util.Objects;

public class Command extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getFullCommandName().replace(Config.get("server_name").toLowerCase() + " ", "");
        switch (command){
            case "send" -> {
                OptionMapping optionMapping = event.getOption("input");
                assert optionMapping != null;
                String input = optionMapping.getAsString();
                Call.sendMessage("[blue]Discord[] " + Objects.requireNonNull(event.getMember()).getEffectiveName() + ": " + input);
                event.reply(Bundle.get("successful")).queue();
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
                event.reply(Bundle.get("successful")).queue();
            }
            case "ban" -> {
                OptionMapping uuid = event.getOption("uuid");
                OptionMapping reason = event.getOption("reason");
                assert uuid != null;
                assert reason != null;
                String uuidS = uuid.getAsString();
                String reasonS = reason.getAsString();
                NetServer server = new NetServer();
                server.admins.banPlayerID(uuidS);
                Groups.player.find(p -> p.uuid().equals(uuidS)).kick(reasonS, 0);
                event.reply(Bundle.get("successful")).queue();
            }
            case "unban" -> {
                OptionMapping uuid = event.getOption("uuid");
                assert  uuid != null;
                String uuidS = uuid.getAsString();
                NetServer server =  new NetServer();
                server.admins.unbanPlayerID(uuidS);
                event.reply(Bundle.get("successful")).queue();
            }
            case "add_map" -> {

            }
            case "maps" -> {

            }
        }
        /*
        if(command.equals(Config.get("server_name") + "send".toLowerCase())){
            OptionMapping optionMapping = event.getOption("input");
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
         */
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        /*
        OptionData optionData = new OptionData(OptionType.STRING, "cmd", "cmd what bot will do", true)
                .addChoice("Send message to server", "send")
                .addChoice("List of players", "players")
                .addChoice("Ban player", "ban")
                .addChoice("Unban player", "unban")
                .addChoice("Add map to server", "add_map")
                .addChoice("List maps of server", "maps");
         */
        OptionData send = new OptionData(OptionType.STRING, "input", "message what u want to send", true);
        OptionData banUUID = new OptionData(OptionType.STRING, "uuid", "uuid player what u want to ban", true);
        OptionData banReason = new OptionData(OptionType.STRING, "reason", "reason of the ban", true);
        OptionData unbanUUID = new OptionData(OptionType.STRING, "uuid", "uuid player what u wont to unban", true);

        SubcommandData subSend = new SubcommandData("send", "send message to server").addOptions(send);
        SubcommandData subPlayers = new SubcommandData("players", "List of players");
        SubcommandData subBan = new SubcommandData("ban", "ban player").addOptions(banUUID, banReason);
        SubcommandData subUnban = new SubcommandData("unban", "unban player").addOptions(unbanUUID);

        Bot.jda.upsertCommand(Commands.slash(Config.get("server_name").toLowerCase(), "all cmds")
                .addSubcommands(subSend)
                .addSubcommands(subPlayers)
                .addSubcommands(subBan)
                .addSubcommands(subUnban))
                .queue();
    }
}

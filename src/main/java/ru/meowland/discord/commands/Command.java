package ru.meowland.discord.commands;

import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.core.NetServer;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.maps.Map;
import mindustry.maps.Maps;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.platform.unix.aix.AixHardwareAbstractionLayer;
import oshi.hardware.platform.unix.openbsd.OpenBsdCentralProcessor;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.discord.Bot;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
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
                event.reply(Bundle.get("discord.successful")).queue();
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
                event.reply(Bundle.get("discord.successful")).queue();
            }
            case "ban" -> {
                OptionMapping uuid = event.getOption("uuid");
                OptionMapping reason = event.getOption("reason");
                assert uuid != null;
                assert reason != null;
                String uuidS = uuid.getAsString();
                String reasonS = reason.getAsString();
                NetServer server = Vars.netServer;
                server.admins.banPlayerID(uuidS);
                Groups.player.find(p -> p.uuid().equals(uuidS)).kick(reasonS, 0);
                event.reply(Bundle.get("discord.successful")).queue();
            }
            case "unban" -> {
                OptionMapping uuid = event.getOption("uuid");
                assert  uuid != null;
                String uuidS = uuid.getAsString();
                NetServer server = Vars.netServer;
                server.admins.unbanPlayerID(uuidS);
                event.reply(Bundle.get("discord.successful")).queue();
            }
            case "add_map" -> {
                Maps maps = Vars.maps;
                OptionMapping map = event.getOption("map");
                assert  map != null;
                Message.Attachment mapAsAttachment = map.getAsAttachment();
                if(!mapAsAttachment.getFileName().contains(".msav"))  event.reply("Map must be .msav").queue();
                mapAsAttachment.getProxy().downloadToFile(new File("config/maps/" + mapAsAttachment.getFileName()));
                maps.reload();
                event.reply(Bundle.get("discord.successful")).queue();
            }
            case "maps" -> {
                Maps maps = Vars.maps;
                if(maps.customMaps().isEmpty()) Log.info("empty");
                Seq<Map> all = new Seq<>();
                all.addAll(maps.customMaps());
                StringBuilder stringBuilder = new StringBuilder();
                for(Map map : all){
                    stringBuilder.append(map.name()).append("\n");
                }
                maps.reload();
                event.reply(stringBuilder.toString()).queue();
            }
            case "info" -> {
                SystemInfo systemInfo = new SystemInfo();

                EmbedBuilder eb = new EmbedBuilder();

                DecimalFormat decimalFormat = new DecimalFormat("#.#");

                eb.setTitle(Config.get("server_name"));
                eb.addField(new MessageEmbed.Field("OS", systemInfo.getOperatingSystem().toString(), true));
                eb.addField(new MessageEmbed.Field("Total ram", Runtime.getRuntime().maxMemory() / 1024 / 1024 +  " mb", true));
                eb.addField(new MessageEmbed.Field("Free ram", Runtime.getRuntime().freeMemory() / 1024 / 1024 + " mb", true));
                eb.addField(new MessageEmbed.Field("Used ram", (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1024 /1024 + " mb", true));
                eb.addField(new MessageEmbed.Field("CPU load", decimalFormat.format(systemInfo.getHardware().getProcessor().getSystemCpuLoad(1000) * 100) + "%", true));
                event.replyEmbeds(eb.build()).queue();
            }
        }
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
        OptionData subMap = new OptionData(OptionType.ATTACHMENT, "map", "map what u want add", true);

        SubcommandData subSend = new SubcommandData("send", "Send message to server").addOptions(send);
        SubcommandData subPlayers = new SubcommandData("players", "List of players");
        SubcommandData subBan = new SubcommandData("ban", "Ban player").addOptions(banUUID, banReason);
        SubcommandData subUnban = new SubcommandData("unban", "Unban player").addOptions(unbanUUID);
        SubcommandData subAddMap = new SubcommandData("add_map", "Add map to the server").addOptions(subMap);
        SubcommandData subMaps = new SubcommandData("maps", "List maps on the sever");
        SubcommandData subInfo = new SubcommandData("info", "Server info");

        Bot.jda.upsertCommand(Commands.slash(Config.get("server_name").toLowerCase(), "all cmds")
                        .addSubcommands(subSend)
                        .addSubcommands(subPlayers)
                        .addSubcommands(subBan)
                        .addSubcommands(subUnban)
                        .addSubcommands(subAddMap)
                        .addSubcommands(subMaps)
                        .addSubcommands(subInfo))
                .queue();
    }
}

package ru.meowland.commands;

import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.api.utils.messages.MessageData;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.discord.Bot;

import java.util.HashSet;

public class PlayerCommands {
    private static double ratio = 0.6;
    private HashSet<String> rvotes = new HashSet<>();
    private HashSet<String> wvotes = new HashSet<>();

    public void rtv(String[] args, Player player){
        if((Config.get("rtv").equals("false") && player.admin) || Config.get("rtv").equals("true")){
            this.rvotes.add(player.uuid());
            int cur = this.rvotes.size();
            int req = (int) Math.ceil(ratio * Groups.player.size());
            Call.sendMessage("[navy]RTV[]: " + player.name + Bundle.get("commands.votes") + "[green]" + cur + ". " + Bundle.get("commands.votes.for") + " [green]" + req);
            if(cur < req){
                return;
            }
            this.rvotes.clear();
            Call.sendMessage("[navy]RTV[]: " + Bundle.get("command.rtv.successful"));
            Events.fire(new EventType.GameOverEvent(Team.crux));
        }else{
            player.sendMessage(Bundle.get("commands.permission-denied"));
        }
    }
    public void wave(String[] args, Player player){
        if((Config.get("wave").equals("false") && player.admin) || Config.get("wave").equals("true")){
            this.wvotes.add(player.uuid());
            int cur = this.wvotes.size();
            int req = (int) Math.ceil(ratio * Groups.player.size());
            Call.sendMessage("[#39d4e6]Wave: " + player.name + Bundle.get("commands.votes") + "[green]" + cur + ". " + Bundle.get("commands.votes.for") + " [green]" + req);
            if(cur < req){
                return;
            }
            this.wvotes.clear();
            Call.sendMessage("[#39d4e6]Wave: " + Bundle.get("commands.wave.successful"));
            Vars.logic.skipWave();
        }else{
            player.sendMessage(Bundle.get("commands.permission-denied"));
        }
    }

    public void request(String[] args, Player player){

        TextChannel channel = Bot.jda.getTextChannelById(Config.get("admin_channel"));
        MessageEmbed eb = new EmbedBuilder()
                .setTitle("Admin request")
                .addField("Nickname", player.name, false)
                .addField("UUID", player.uuid(), false)
                .addField("IPv4", player.ip(), false)
                .build();

        Button accept = Button.success("accept", "accept");
        Button deny = Button.danger("deny", "deny");

        MessageCreateData messageData = new MessageCreateBuilder()
                .setEmbeds(eb)
                .setActionRow(accept)
                .addActionRow(deny)
                .build();

        assert channel != null;
        channel.sendMessage(messageData).queue();
    }
}

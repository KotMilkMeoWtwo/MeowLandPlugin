package ru.meowland.commands;

import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;

import java.util.HashSet;
import java.util.Map;

public class PlayerCommands {
    private static double ratio = 0.6;
    private HashSet<String> rvotes = new HashSet<>();
    private HashSet<String> wvotes = new HashSet<>();
    public void shiza(String[] args, Player player){
        String textshizi = String.join(" ", args);
        if((Config.get("shiza").equals("false") && player.admin) || Config.get("shiza").equals("true")){
            player.sendMessage(Bundle.get("commands.shiza") + textshizi);
        }else{
            player.sendMessage(Bundle.get("commands.permission-denied"));
            return;
        }
    }
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
            Call.sendMessage("[navy]RTV[]: голоса приняты, смена карты");
            Events.fire(new EventType.GameOverEvent(Team.crux));
        }else{
            player.sendMessage(Bundle.get("commands.permission-denied"));
            return;
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
            return;
        }

    }
}

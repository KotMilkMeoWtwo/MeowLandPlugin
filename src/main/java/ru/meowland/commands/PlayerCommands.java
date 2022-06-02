package ru.meowland.commands;

import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;

import java.util.HashSet;

public class PlayerCommands {
    private static double ratio = 0.6;
    private HashSet<String> votes = new HashSet<>();
    public void shiza(String[] args, Player player){
        player.sendMessage(args[0]);
    }
    public void rtv(String[] args, Player player){
        this.votes.add(player.uuid());
        int cur = this.votes.size();
        int req = (int) Math.ceil(ratio * Groups.player.size());
        Call.sendMessage("[navy]RTV[]: " + player.name + "[]голосует за смену карты [green]" + cur + "[] голос(ов;a), из [green]" + req);
        if(cur < req){
            return;
        }
        this.votes.clear();
        Call.sendMessage("[navy]RTV[]: голоса приняты, смена карты");
        Events.fire(new EventType.GameOverEvent(Team.crux));
    }
}

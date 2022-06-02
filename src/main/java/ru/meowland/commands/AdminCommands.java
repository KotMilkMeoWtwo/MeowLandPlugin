package ru.meowland.commands;

import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;
import mindustry.world.Block;

public class AdminCommands{
    public void despw(String[] args, Player player){
        if(player.admin){
            Groups.unit.each(Unitc::kill);
            player.sendMessage("Все юниты померли");
        }
        else {
            player.sendMessage("[red]Ты не админ");
            return;
        }
    }
    public void spawn(String[] args, Player player){
        if(player.admin){
            UnitType unit = Vars.content.units().find(b -> b.name.equals(args[0]));
            int count;
            try {
                count = Integer.parseInt(args[1]);
            }catch (NumberFormatException e){
                player.sendMessage("[red]Число должно быть числом");
                return;
            }
            Team team;
            if (args[2].equals("sharded")) {
                team = Team.sharded;
            } else if(args[2].equals("blue")){
                team = Team.blue;
            } else if(args[2].equals("crux")){
                team = Team.crux;
            } else if (args[2].equals("derelict")) {
                team = Team.derelict;
            } else if (args[2].equals("green")) {
                team = Team.green;
            } else if(args[2].equals("purple")){
                team = Team.purple;
            } else {
                player.sendMessage("Есть команды: [yellow]sharded[], [blue]blue[], [red]crux[], [gray]derelict[], [green]green[], [purple]purple[].");
                return;
            }
            if(count > 15){
                player.sendMessage("Лимит для спавна 15 юнитов");
                return;
            }
            if(unit != null){
                for (int i = 0; count > i; i++) {
                    Unit unit1 = unit.spawn(team, player.x, player.y);
                }
                player.sendMessage("[green]Сексесфул");
            }else {
                player.sendMessage("Есть юниты: [accent]dagger, mace, fortress, scepter, reign, nova, pulsar, quasar, vela, corvus, crawler, atrax, spiroct, arkyid, toxopid, mono, poly, mega, quad, oct, flare, eclipse, horizon, zenith, antumbra, risso, minke, bryde, sei, omura");
            }


        }
        else {
            player.sendMessage("[red]Ты не админ");
            return;
        }
    }
    public void team(String[] args, Player player){
        Team team;
        if(player.admin) {
            if (args[0].equals("sharded")) {
                team = Team.sharded;
            } else if (args[0].equals("blue")) {
                team = Team.blue;
            } else if (args[0].equals("crux")) {
                team = Team.crux;
            } else if (args[0].equals("derelict")) {
                team = Team.derelict;
            } else if (args[0].equals("green")) {
                team = Team.green;
            } else if (args[0].equals("purple")) {
                team = Team.purple;
            } else {
                player.sendMessage("Есть команды: [yellow]sharded[], [blue]blue[], [red]crux[], [gray]derelict[], [green]green[], [purple]purple[].");
                return;
            }
            player.team(team);
        }else{
            player.sendMessage("[red]Ты не админ");
            return;
        }
    }

}

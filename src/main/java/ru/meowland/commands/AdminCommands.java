package ru.meowland.commands;

import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.mod.Plugin;
import mindustry.type.UnitType;
import mindustry.world.Block;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import mindustry.mod.*;

import static mindustry.Vars.*;


@SuppressWarnings("unused")
public class AdminCommands extends Plugin{

    public void despw(String[] args, Player player){
        if((Config.get("despw").equals("false") && player.admin) || Config.get("despw").equals("true")){
            Groups.unit.each(Unitc::kill);
            player.sendMessage(Bundle.get("commands.successful"));
        }
        else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
        }
    }
    public void spawn(String[] args, Player player){
        if((Config.get("spawn").equals("false") && player.admin) || Config.get("spawn").equals("true")){
            UnitType unit = Vars.content.units().find(b -> b.name.equals(args[0]));
            int count;
            try {
                count = Integer.parseInt(args[1]);
            }catch (NumberFormatException e){
                player.sendMessage(Bundle.get("commands.int-is-no-int"));
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
                player.sendMessage(Bundle.get("commands.teams"));
                return;
            }
            if(count > Config.getInt(Integer.parseInt("spawn-limit"))){
                player.sendMessage(Bundle.get("commands.spawn.limit") + Config.get("spawn-limit"));
                return;
            }
            if(unit != null){
                for (int i = 0; count > i; i++) {
                    Unit unit1 = unit.spawn(team, player.x, player.y);
                }
                player.sendMessage(Bundle.get("commands.successful"));
            }else {
                player.sendMessage(Bundle.get("commands.units"));
            }


        } else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
            return;
        }
    }
    public void team(String[] args, Player player){
        Team team;

        if((Config.get("team").equals("false") && player.admin) || Config.get("team").equals("true")){
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
                player.sendMessage(Bundle.get("commands.teams"));
                return;
            }
            player.sendMessage(Bundle.get("commands.successful"));
            player.team(team);
        }else{
            player.sendMessage(Bundle.get("commands.permission-denied"));
            return;
        }
    }
    public void spawncore(String[] args, Player player){
        if((Config.get("spawncore").equals("false") && player.admin) || Config.get("spawncore").equals("true")){
            Block core;
            if (args[0].equals("small")){
                core = Blocks.coreShard;
            } else if (args[0].equals("medium")) {
                core = Blocks.coreFoundation;
            } else if (args[0].equals("large")) {
                core = Blocks.coreNucleus;
            }else {
                core = Blocks.coreShard;
            }
            player.sendMessage(Bundle.get("commands.successful"));

            Call.constructFinish(player.tileOn(), core, player.unit(), (byte)0, player.team(), false);
        }else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
            return;
        }
    }


    public void js(String[] args, Player player){
        if(Config.get("js").equals("false") && player.admin || Config.get("js").equals("true")){
            String output = mods.getScripts().runConsole(args[0]);
            player.sendMessage("> " + (isError(output) ? "[yellow]" + output : output));
        }else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
        }
    }

    private boolean isError(String output){
        try {
            String errorName = output.substring(0, output.indexOf(' ') - 1);
            Class.forName("org.mozilla.javascript." + errorName);
            return true;
        }catch (Throwable e){
            return false;
        }
    }

}

package ru.meowland.commands;

import arc.Core;
import arc.files.Fi;
import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.UnitType;
import mindustry.world.Block;
import org.yaml.snakeyaml.Yaml;
import ru.meowland.MeowlandPlugin;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminCommands{

    private String teamm, spawn, despw, spawncore;
    private Map<String, Object> obj;

    public void despw(String[] args, Player player){
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        despw = obj.get("despw").toString();

        if((despw.equals("false") && player.admin) || despw.equals("true")){
            Groups.unit.each(Unitc::kill);
            player.sendMessage(Bundle.get("commands.successful"));
        }
        else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
        }
    }
    public void spawn(String[] args, Player player){
        //Yaml yml = new Yaml();
        //obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        //spawn = obj.get("spawn").toString();
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
            if(count > 15){
                player.sendMessage(Bundle.get("commands.spawn.limit" + Config.get("spawn-limit")));
                return;
            }
            if(unit != null){
                for (int i = 0; count > i; i++) {
                    Unit unit1 = unit.spawn(team, player.x, player.y);
                }
                player.sendMessage(Bundle.get("commands.successful"));
            }else {
                player.sendMessage("Есть юниты: [accent]dagger, mace, fortress, scepter, reign, nova, pulsar, quasar, vela, corvus, crawler, atrax, spiroct, arkyid, toxopid, mono, poly, mega, quad, oct, flare, eclipse, horizon, zenith, antumbra, risso, minke, bryde, sei, omura");
            }


        } else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
            return;
        }
    }
    public void team(String[] args, Player player){
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        Team team;
        teamm = obj.get("team").toString();

        if((teamm.equals("false") && player.admin) || teamm.equals("true")){
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
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        spawncore = obj.get("spawncore").toString();
        if((spawncore.equals("false") && player.admin) || spawncore.equals("true")){
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

}

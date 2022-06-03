package ru.meowland.commands;

import arc.Core;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.UnitType;
import mindustry.world.Block;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class AdminCommands{

    private String teamm, spawn, despw, spawncore;
    private Map<String, Object> obj;

    public void despw(String[] args, Player player){
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        despw = obj.get("despw").toString();

        if((despw.equals("false") && player.admin) || despw.equals("true")){
            Groups.unit.each(Unitc::kill);
            player.sendMessage("Все юниты померли");
        }
        else {
            player.sendMessage("[red]Ты не админ");
            return;
        }
    }
    public void spawn(String[] args, Player player){
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        spawn = obj.get("spawn").toString();
        if((spawn.equals("false") && player.admin) || spawn.equals("true")){
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


        } else {
            player.sendMessage("[red]Ты не админ");
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
                player.sendMessage("Есть команды: [yellow]sharded[], [blue]blue[], [red]crux[], [gray]derelict[], [green]green[], [purple]purple[].");
                return;
            }
            player.sendMessage("[green]Сексесфул");
            player.team(team);
        }else{
            player.sendMessage("[red]Ты не админ");
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
            player.sendMessage("[green]Сексесфул");

            Call.constructFinish(player.tileOn(), core, player.unit(), (byte)0, player.team(), false);
        }else {
            player.sendMessage("[red]Ты не админ");
            return;
        }



    }

}

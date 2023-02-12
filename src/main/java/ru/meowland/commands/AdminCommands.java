package ru.meowland.commands;

import arc.func.Floatc2;
import arc.math.Angles;
import arc.math.geom.Geometry;
import arc.util.Log;
import arc.util.Structs;
import arc.util.Timer;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.gen.Unitc;
import mindustry.mod.Plugin;
import mindustry.type.UnitType;
import mindustry.world.Block;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;

import java.time.LocalTime;

import static mindustry.Vars.content;
import static mindustry.Vars.mods;


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
    public void spawn(String[] args, Player player) {
        if ((Config.get("spawn").equals("false") && player.admin) || Config.get("spawn").equals("true")) {
            UnitType unit = content.units().find(b -> b.name.equalsIgnoreCase(args[0]));
            int count = 1;
            if (args.length > 1){
                try {
                    count = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(Bundle.get("commands.int-is-no-int"));
                    return;
                }
            } 

            Team team = args.length > 2 ? Structs.find(Team.baseTeams, t -> t.name.equalsIgnoreCase(args[2])) : player.team();
            if (team == null) {
                player.sendMessage(Bundle.get("commands.teams"));
                return;
            }

            if (count > Integer.parseInt(Config.get("spawn-limit"))) {
                player.sendMessage(Bundle.get("commands.spawn.limit") + Config.get("spawn-limit"));
                return;
            }

            if (unit != null) {
                for (int i = 0; count > i; i++) {
                    unit.spawn(team, player.x, player.y);
                }
                player.sendMessage(Bundle.get("commands.successful"));
            } else {
                player.sendMessage(Bundle.get("commands.units"));
            }

        } else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
            return;
        }
    }

    public void team(String[] args, Player player){
        if((Config.get("team").equals("false") && player.admin) || Config.get("team").equals("true")){
            Team team = args.length > 0 ? Structs.find(Team.baseTeams, t -> t.name.equalsIgnoreCase(args[0])) : player.team(); 
            if (team == null) {
                player.sendMessage(Bundle.get("commands.teams"));
                return;
            }
            player.sendMessage(Bundle.get("commands.successful"));
            player.team(team);
        } else {
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

    public void setblock(String[] args, Player player){
        if((Config.get("setblock").equals("false") && player.admin) || Config.get("setblock").equals("true")){
            Block block  = content.blocks().find(b -> b.name.equalsIgnoreCase(args[0]));
            if(block != null){
                Call.constructFinish(player.tileOn(), block, player.unit(), (byte)0, player.team(), false);
                player.sendMessage(Bundle.get("commands.successful"));
            }
        } else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
            return;
        }
    }
    public void advertisement(String[] args, Player player){
        if((Config.get("advertisement").equals("false") && player.admin) || Config.get("advertisement").equals("true")){
            Call.infoMessage(Bundle.get("commands.advertisement.title") + "\n\n\n\n\n[]" + args[0]);
        } else {
            player.sendMessage(Bundle.get("command.permission-denied"));
            return;
        }
    }

    public void effect(String[] args, Player player){
        if((Config.get("effect").equals("false") && player.admin) || Config.get("effect").equals("true")){
            Floatc2 runner = (x, y) -> {
                Call.effect(Fx.freezing, player.x + x, player.y + y, Angles.angle(x, y), Tmp.c1.rand());
            };
            player.sendMessage(Bundle.get("commands.successful"));

            Timer.schedule(() -> {
                draw(6, 3, 30, 0, runner);
            }, 0, 0.05f);
        } else {
            player.sendMessage(Bundle.get("commands.permission-denied"));
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

    public static void draw(int sides, int step,  float radius, int angle, Floatc2 cons) {
        Tmp.v1.set(1, 1).setLength(radius);
        Tmp.v2.set(1,1).setLength(radius);

        for(var i = 0; i < sides; i++){
            Tmp.v1.setAngle(360 / sides * i + 90).rotate(angle);
            Tmp.v2.setAngle(360 / sides * (i + 1) + 90).rotate(angle);

            Geometry.iterateLine(0, Tmp.v1.x, Tmp.v1.y, Tmp.v2.x, Tmp.v2.y, step, cons);
        }
    };


}

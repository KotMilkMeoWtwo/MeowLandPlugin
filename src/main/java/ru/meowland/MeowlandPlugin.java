package ru.meowland;

import arc.files.Fi;
import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import ru.meowland.commands.AdminCommands;
import ru.meowland.commands.PlayerCommands;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.config.Menu;
import ru.meowland.discord.*;
import ru.meowland.events.JoinEvent;
import ru.meowland.events.LeaveEvent;
import useful.menu.DynamicMenus;

import javax.security.auth.login.LoginException;

public class MeowlandPlugin extends Plugin{


    AdminCommands adminCommands = new AdminCommands();

    PlayerCommands playerCommands = new PlayerCommands();
    public static final Fi pluginDir = new Fi("./config/mods/MeowLand");

    public MeowlandPlugin() {
    }

    @Override
    public void init(){
        Config con = new Config();
        con.loadConfig();
        Bundle.init();
        Menu.init();
        if(Config.get("bot_enable").equals("true")) Bot.init();
        new JoinEvent();
        new LeaveEvent();
        Log.info("Meowland: plugin started");
        if(Config.get("webhook_enable").equals("true")){
            Log.info("Meowland: discord webhook is enable");
        }else {
            Log.info("Meowland: discord webhook is disable");
        }
    }
    @Override
    public void registerClientCommands(CommandHandler handler){
        AdminCommands adminCommands = new AdminCommands();
        handler.<Player>register("rtv", Bundle.get("command.rtv.dsc"), playerCommands::rtv);
        handler.<Player>register("wave", Bundle.get("command.wave.desc"), playerCommands::wave);
        handler.<Player>register("request", "secret", playerCommands::request);

        handler.<Player>register("js", "<code...>", "run js code", adminCommands::js);
        handler.<Player>register("despw",  Bundle.get("command.despw.dsc"), adminCommands::despw);
        handler.<Player>register("spawn", Bundle.get("command.spawn.usage"), Bundle.get("command.spawn.desc"), adminCommands::spawn);
        handler.<Player>register("team", Bundle.get("command.team.usage"), Bundle.get("command.team.desc"), adminCommands::team);
        handler.<Player>register("spawncore", Bundle.get("command.spawncore.usage"), Bundle.get("command.spawncore.desc"), adminCommands::spawncore);
        handler.<Player>register("setblock", Bundle.get("command.setblock.usage"), Bundle.get("command.setblock.desc"), adminCommands::setblock);
        handler.<Player>register("advertisement", Bundle.get("command.advertisement.usage"), Bundle.get("command.advertisement.desc"), adminCommands::advertisement);
        handler.<Player>register("effect", Bundle.get("command.effect.desc"), adminCommands::effect);
    }
}
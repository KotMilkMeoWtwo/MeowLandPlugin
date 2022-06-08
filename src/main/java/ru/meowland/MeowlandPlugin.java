package ru.meowland;

import arc.files.Fi;
import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import ru.meowland.commands.AdminCommands;
import ru.meowland.commands.PlayerCommands;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.discord.PlayerJoin;
import ru.meowland.discord.PlayerLeave;
import ru.meowland.discord.PlayerMessage;
import ru.meowland.discord.ServerLoaded;

public class MeowlandPlugin extends Plugin {


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

        PlayerJoin join = new PlayerJoin();
        join.join();
        PlayerLeave leave = new PlayerLeave();
        leave.leave();
        PlayerMessage message = new PlayerMessage();
        message.message();
        ServerLoaded loaded = new ServerLoaded();
        loaded.serverLoad();
        Log.info("Meowland: plugin started");
        if(Config.get("enable").equals("true")){
            Log.info("Meowland: discord integration is enable");
        }else {
            Log.info("Meowland: discord integration is disable");
        }
    }
    @Override
    public void registerClientCommands(CommandHandler handler){
        AdminCommands adminCommands = new AdminCommands();
        handler.<Player>register("shiza", Bundle.get("command.shiza.usage"), Bundle.get("command.shiza.dsc"), playerCommands::shiza);
        handler.<Player>register("rtv", Bundle.get("command.rtv.dsc"), playerCommands::rtv);
        handler.<Player>register("wave", Bundle.get("command.wave.desc"), playerCommands::wave);


        handler.<Player>register("despw",  Bundle.get("command.despw.dsc"), adminCommands::despw);
        handler.<Player>register("spawn", Bundle.get("command.spawn.usage"), Bundle.get("command.spawn.desc"), adminCommands::spawn);
        handler.<Player>register("team", Bundle.get("command.team.usage"), Bundle.get("command.team.desc"), adminCommands::team);
        handler.<Player>register("spawncore", Bundle.get("command.spawncore.usage"), Bundle.get("command.spawncore.desc"), adminCommands::spawncore);

    }


}
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
import ru.meowland.discord.*;

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
        Bot bot = new Bot();
        try {
            bot.bot();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        WebhookPlayerJoin join = new WebhookPlayerJoin();
        join.join();
        WebhookPlayerLeave leave = new WebhookPlayerLeave();
        leave.leave();
        WebhookPlayerMessage message = new WebhookPlayerMessage();
        message.message();
        WebhookServerLoaded loaded = new WebhookServerLoaded();
        loaded.serverLoad();
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
        handler.<Player>register("shiza", Bundle.get("command.shiza.usage"), Bundle.get("command.shiza.dsc"), playerCommands::shiza);
        handler.<Player>register("rtv", Bundle.get("command.rtv.dsc"), playerCommands::rtv);
        handler.<Player>register("wave", Bundle.get("command.wave.desc"), playerCommands::wave);


        handler.<Player>register("js", "<code>", "run js code", adminCommands::js);
        handler.<Player>register("despw",  Bundle.get("command.despw.dsc"), adminCommands::despw);
        handler.<Player>register("spawn", Bundle.get("command.spawn.usage"), Bundle.get("command.spawn.desc"), adminCommands::spawn);
        handler.<Player>register("team", Bundle.get("command.team.usage"), Bundle.get("command.team.desc"), adminCommands::team);
        handler.<Player>register("spawncore", Bundle.get("command.spawncore.usage"), Bundle.get("command.spawncore.desc"), adminCommands::spawncore);

    }
}
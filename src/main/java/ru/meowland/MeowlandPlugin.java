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
        handler.<Player>register("shiza", "<text...>", "позволяет разговаривать с самим собой. никто, кроме консоли, не узанет, что ты тут пишешь", playerCommands::shiza);
        handler.<Player>register("rtv", "Проголосовать за смену карты", playerCommands::rtv);
        handler.<Player>register("wave", "Запускает волну (после голосования)", playerCommands::wave);


        handler.<Player>register("despw", "", "Убивает всех юнитов на сервере", adminCommands::despw);
        handler.<Player>register("spawn", "<Unit> <Count> <Team>", "Спавнит нужного юнита", adminCommands::spawn);
        handler.<Player>register("team", "<Team>", "меняет команду", adminCommands::team);
        handler.<Player>register("spawncore", "<small|medium|large>", "Спавнит ядро", adminCommands::spawncore);

    }


}
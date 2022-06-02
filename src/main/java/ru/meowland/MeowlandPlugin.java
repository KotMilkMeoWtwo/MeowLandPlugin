package ru.meowland;

import arc.Core;
import arc.Events;
import arc.files.Fi;
import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.mod.Plugin;
import mindustry.type.UnitType;
import org.yaml.snakeyaml.Yaml;
import ru.meowland.commands.AdminCommands;
import ru.meowland.commands.PlayerCommands;
import ru.meowland.discord.PlayerJoin;
import ru.meowland.discord.PlayerLeave;
import ru.meowland.config.Config;
import ru.meowland.discord.PlayerMessage;

import java.util.HashSet;
import java.util.Map;
import java.util.logging.Handler;

public class MeowlandPlugin extends Plugin {

    private String enable;
    private Map<String, Object> obj;


    AdminCommands adminCommands = new AdminCommands();
    PlayerCommands playerCommands = new PlayerCommands();
    @Override
    public void init(){
        Config con = new Config();
        con.loadConfig();
        PlayerJoin join = new PlayerJoin();
        join.join();
        PlayerLeave leave = new PlayerLeave();
        leave.leave();
        PlayerMessage message = new PlayerMessage();
        message.message();
        Log.info("Meowland: plugin started");
        if(enable.equals("true")){
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

        handler.<Player>register("despw", "", "Убивает всех юнитов на сервере", adminCommands::despw);
        handler.<Player>register("spawn", "<Unit> <Count> <Team>", "Спавнит нужного юнита", adminCommands::spawn);
        handler.<Player>register("team", "<Team>", "меняет команду", adminCommands::team);

    }


}
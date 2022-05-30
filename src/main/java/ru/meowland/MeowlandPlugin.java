package ru.meowland;

import arc.Events;
import arc.util.CommandHandler;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.mod.Plugin;

public class MeowlandPlugin extends Plugin {
    @Override
    public void init(){
        Events.on(EventType.PlayerConnect.class, event ->{
            Player player = event.player;
            Call.sendMessage("[lime]Игрок [#B](" + player.name + "[#B]) [lime]зашёл");
        });
    }
    @Override
    public void registerClientCommands(CommandHandler handler){
        handler.<Player>register("meow", "<text...>", "позволяет разговаривать с самим собой. никто, кроме консоли, не узанет, что ты тут пишешь",  (args, player) -> {
            player.sendMessage(args[0]);
        });
    }
}

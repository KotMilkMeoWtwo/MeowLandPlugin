package ru.meowland;

import arc.Events;
import arc.util.CommandHandler;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.gen.Unitc;
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
        handler.<Player>register("shiza",
                "<text...>",
                "позволяет разговаривать с самим собой. никто, кроме консоли, не узанет, что ты тут пишешь",  (args, player) -> {
            player.sendMessage(args[0]);
        });

        handler.<Player>register("despw", "", "Убивает всех юнитов на сервере", (args, player) -> {
            if(player.admin){
                Groups.unit.each(Unitc::kill);
                player.sendMessage("Все юниты померли");
            }
            else {
                player.sendMessage("[red]Ты не админ");
                return;
            }

        });
    }
}

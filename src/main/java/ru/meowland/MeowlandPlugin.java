package ru.meowland;

import arc.Events;
import arc.util.CommandHandler;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.mod.Plugin;
import mindustry.net.Administration;
import mindustry.net.Administration.Config;
import mindustry.type.UnitType;

import java.util.HashMap;
import java.util.HashSet;

public class MeowlandPlugin extends Plugin {

    private static double ratio = 0.6;
    private HashSet<String> votes = new HashSet<>();
    @Override
    public void init(){
        Events.on(EventType.PlayerConnect.class, event ->{
            Player player = event.player;
            Config.showConnectMessages.set(false);
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
        handler.<Player>register("spawn", "<Unit> <Count> <Team>", "Спавнит нужного юнита", (args, player) ->{
            if(player.admin){
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


            }
            else {
                player.sendMessage("[red]Ты не админ");
                return;
            }
        });
        handler.<Player>register("rtv", "Проголосовать за смену карты", (args, player) ->{
            this.votes.add(player.uuid());
            int cur = this.votes.size();
            int req = (int) Math.ceil(ratio * Groups.player.size());
            Call.sendMessage("[navy]RTV[]: " + player.name + "[]голосует за смену карты [green]" + cur + "[] голос(ов;a), из [green]" + req);
            if(cur < req){
                return;
            }
            this.votes.clear();
            Call.sendMessage("[navy]RTV[]: голоса приняты, смена карты");
            Events.fire(new EventType.GameOverEvent(Team.crux));
        });
    }
}

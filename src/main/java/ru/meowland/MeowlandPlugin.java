package ru.meowland;

import arc.Events;
import arc.files.Fi;
import arc.util.CommandHandler;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.mod.Plugin;
import mindustry.type.UnitType;
import ru.meowland.commands.AdminCommands;
import ru.meowland.discord.PlayerJoin;
import ru.meowland.discord.PlayerLeave;
import ru.meowland.config.Config;
import ru.meowland.discord.PlayerMessage;

import java.util.HashSet;
import java.util.logging.Handler;

public class MeowlandPlugin extends Plugin {

    private String webhookUrl;

    private static double ratio = 0.6;
    private HashSet<String> votes = new HashSet<>();

    @Override
    public void init(){
        PlayerJoin join = new PlayerJoin();
        join.join();
        PlayerLeave leave = new PlayerLeave();
        leave.leave();
        Config con = new Config();
        con.loadConfig();
        webhookUrl = con.webhook_url;
        AdminCommands adminCommands = new AdminCommands();
        PlayerMessage message = new PlayerMessage();
        message.message();
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
package ru.meowland.events;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Mechc;
import mindustry.gen.Player;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.config.Menu;
import useful.menu.DynamicMenus;

public class JoinEvent {

    static {
        DynamicMenus.load();
        Events.on(EventType.ResetEvent.class, event -> DynamicMenus.clear());

        Events.on(EventType.PlayerJoin.class, event -> {
            Player player = event.player;
            Call.sendMessage(Bundle.get("client.connected", player.name));
            Log.info(Bundle.get("server.connected", player.name, player.uuid()));
            Call.openURI(player.con(), Config.get("discord_link"));
            DynamicMenus.menu(event.player, Bundle.get("menu.title", event.player),
                    Menu.get(event.player),
            new String[][]{
                    {Bundle.get("menu.ok", event.player)},
                    {Bundle.get("menu.discord", event.player)}
            }, (p, option) -> {
              if(option == 0){
              }
                    });
        });

    }
}

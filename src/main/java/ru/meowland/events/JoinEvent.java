package ru.meowland.events;

import arc.Events;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Mechc;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.config.Menu;
import useful.menu.DynamicMenus;

public class JoinEvent {

    static {
        DynamicMenus.load();
        Events.on(EventType.ResetEvent.class, event -> DynamicMenus.clear());

        Events.on(EventType.PlayerJoin.class, event -> {
            event.player.sendMessage(event.player.locale);
            DynamicMenus.menu(event.player, Bundle.get("menu.title", event.player),
                    Menu.get(event.player),
            new String[][]{
                    {Bundle.get("menu.ok", event.player)},
                    {Bundle.get("menu.discord", event.player)}
            }, (player, option) -> {
              if(option == 0){
                  player.sendMessage(Bundle.get("menu.ok", player));
              } else if(option == 1){
                  Call.openURI(player.con(), Config.get("discord_link"));
              }
                    });
        });

    }
}

package ru.meowland.events;

import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Player;
import ru.meowland.config.Bundle;
import ru.meowland.config.Config;
import ru.meowland.config.Menu;
import ru.meowland.modules.history.BlockEntry;
import ru.meowland.modules.history.History;
import useful.menu.DynamicMenus;

public class Events {
    static {
        DynamicMenus.load();
        arc.Events.on(EventType.ResetEvent.class, event -> DynamicMenus.clear());

        arc.Events.on(EventType.PlayerJoin.class, event -> {
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
                        if(option == 1){
                            Call.openURI(p.con, Config.get("discord_link"));
                        }
                    });
        });

        arc.Events.on(EventType.WorldLoadEvent.class, event -> {
            History.reset();
            Log.info("History was reseted");
        });

        arc.Events.on(EventType.PlayerLeave.class, event -> {
            Call.sendMessage(Bundle.get("client.disconnected", event.player.name));
            Log.info(Bundle.get("server.disconnected", event.player.name, event.player.uuid()));
        });

        /*
        arc.Events.on(EventType.BlockBuildBeginEvent.class, event -> {
            if(event.unit == null || !event.unit.isPlayer()) return;

            if(History.enabled() && event.tile.build != null)
                History.put(event.tile, new BlockEntry(event));
        });
        arc.Events.on(EventType.TapEvent.class, event -> {
            History.HistoryStack nya = History.get(event.tile.array());
            if(nya == null) return;
            event.player.sendMessage(nya.toString());
        });
         */

    }
}

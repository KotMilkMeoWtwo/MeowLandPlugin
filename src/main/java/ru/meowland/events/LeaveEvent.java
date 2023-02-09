package ru.meowland.events;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.gen.Call;
import ru.meowland.config.Bundle;

public class LeaveEvent {
    static {
        Events.on(EventType.PlayerLeave.class, event -> {
            Call.sendMessage(Bundle.get("client.disconnected", event.player.name));
            Log.info(Bundle.get("server.disconnected", event.player.name, event.player.uuid()));
        });
    }
}

package ru.meowland.modules.history;

import arc.struct.Queue;
import mindustry.Vars;
import mindustry.world.Tile;
import ru.meowland.config.Config;

public class History {

    public static HistoryStack[] history;

    public static boolean enabled(){
        return Boolean.getBoolean(Config.get("history_enabled"));
    }

    public static void reset(){
        history = new HistoryStack[Vars.world.width() * Vars.world.height()];
    }

    public static void put(Tile tile, HistoryEntry entry){
        if(tile == Vars.emptyTile) return;

        tile.getLinkedTiles(other -> {
            HistoryStack stack = get(other.array());
            if (stack == null) return;

            stack.add(entry);
        });
    }

    public static HistoryStack get(int index){
        if(index < 0 || index >= history.length) return null;

        HistoryStack stack = history[index];
        if(stack == null) history[index] = stack = new HistoryStack();
        return stack;
    }

    public static class HistoryStack extends Queue<HistoryEntry> {
        public HistoryStack(){
            super(8);
        }

        @Override
        public void add(HistoryEntry object) {
            if(size >= 8)
                removeFirst();
            super.add(object);
        }
    }

}

package ru.meowland.modules.history;

import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Player;
import mindustry.net.Administration;
import mindustry.world.Block;
import mindustry.world.blocks.ConstructBlock;

public class BlockEntry implements HistoryEntry{

    public final String uuid;
    public final short blockID;
    public final int rotation;
    public final boolean breaking;
    public final long timestamp;

    public BlockEntry(EventType.BlockBuildBeginEvent event){
        this.uuid = event.unit.getPlayer().uuid();
        this.blockID = event.tile.build instanceof ConstructBlock.ConstructBuild build ? build.current.id : event.tile.blockID();
        this.rotation = event.tile.build.rotation;
        this.breaking = event.breaking;
        this.timestamp = Time.millis();
    }


    @Override
    public String getMessage(Player player) {
        Administration.PlayerInfo info = Vars.netServer.admins.getInfo(uuid);
        Block block = Vars.content.block(blockID);

        return "Ломается: " + breaking + ", поворачивается: " + rotation + " " + info.lastName + " блок: " + block.emoji();
    }
}

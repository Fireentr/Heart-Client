package heart.events.impl;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class AirBBEvent {
    public AxisAlignedBB aabb = null;
    public BlockPos pos;
    public Block block;

    public AirBBEvent(AxisAlignedBB aabb, BlockPos pos, Block block) {
        this.aabb = aabb;
        this.pos = pos;
        this.block = block;
    }
}

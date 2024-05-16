package heart.modules.impl.player;

import heart.events.impl.Direction;
import heart.events.impl.TickEvent;
import heart.modules.Category;
import heart.modules.Module;
import heart.modules.settings.impl.BoolSetting;
import heart.modules.settings.impl.DoubleSetting;
import heart.util.PacketUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

public class Scaffold extends Module {
    public Scaffold() {
        super("Scaffold", "Places blocks underneath you.", Category.PLAYER);
        initmodule();
    }

    BlockPos targetBlock = new BlockPos(0, 0, 0);

    BoolSetting downwardSetting = new BoolSetting("Downward", "Allows scaffold to go downward", false);
    BoolSetting keepy =  new BoolSetting("Keep Y", "Keeps scaffold bound to the Y-axis", false);
    DoubleSetting timerBoost = new DoubleSetting("Timer Boost", "Speeds up the timer while scaffolding", 0.1, 5.0, 1.0, 10);

    double startY = 0;
    float startTimer = 0;

    @Override
    public void onEnable() {
        startTimer = mc.timer.timerSpeed;
        startY = Math.floor(mc.thePlayer.posY - 1);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = startTimer;
        super.onDisable();
    }

    @Override
    public void onTick(TickEvent e) {
        mc.timer.timerSpeed = ((float) timerBoost.getValue());
        if(e.direction.equals(Direction.PRE)){
            targetBlock = new BlockPos(Math.floor(mc.thePlayer.posX), keepy.getValue() ? startY : Math.floor(mc.thePlayer.posY - 1 - (downwardSetting.getValue() && Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) ? 0.5 : 0)), Math.floor(mc.thePlayer.posZ));
            if(downwardSetting.getValue())
                mc.gameSettings.keyBindSneak.setPressed(false);

            if(mc.theWorld.isAirBlock(targetBlock)){
                attemptPlaceBlock(targetBlock, true);
            }
        }
        super.onTick(e);
    }

    boolean attemptPlaceBlock(BlockPos pos, boolean attemptSides){
        BlockPos toPlaceOf = pos;
        EnumFacing direction = EnumFacing.NORTH;
        boolean cont = mc.theWorld.isAirBlock(pos);

        for(int i = 0; i <= 5; i++){
            if(cont && mc.theWorld.isAirBlock(pos) && mc.theWorld.getBlockState(pos.offset(EnumFacing.getFront(i))).getBlock().isFullBlock()){
                toPlaceOf = pos.offset(EnumFacing.getFront(i));
                direction = EnumFacing.getFront(i);
                cont = false;
            }
        }

        if(cont){
            if(attemptSides) {
                for (int i = 0; i <= 5; i++) {
                    BlockPos target = pos.offset(EnumFacing.getFront(i));
                    if (cont && attemptPlaceBlock(target, false)) {
                        cont = false;
                    }
                }
            }
            return false;
        }else {
            ItemStack item = mc.thePlayer.getHeldItem();

            PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(toPlaceOf, direction.getOpposite().getIndex(), item, 0, 0, 0));
            item.onItemUse(mc.thePlayer, mc.theWorld, pos, direction.getOpposite(), 0, 0, 0);
            PacketUtil.sendPacketNoEvent(new C0APacketAnimation());


            return true;
        }
    }
}

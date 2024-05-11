package heart.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketUtil {

    public static long ping = 0;

    public static void sendPacket(Packet packet) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacketNoEvent(Packet packet) {
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }

    public static long getCurrentPing() {
        if (Minecraft.getMinecraft().thePlayer == null) return 0;
        if (Minecraft.getMinecraft().isSingleplayer()) return 0;
        if (Minecraft.getMinecraft().thePlayer.ticksExisted % 20 == 0) {
            ping = Minecraft.getMinecraft().getCurrentServerData().pingToServer;
        }
        return ping;
    }
}
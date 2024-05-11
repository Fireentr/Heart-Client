package heart.events.impl;

import heart.events.Cancellable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketEvent extends Cancellable {
    public Origin origin;
    private Packet<?> packet;

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public PacketEvent(Origin origin, Packet packet){
        if (Minecraft.getMinecraft().thePlayer == null) return;
        this.origin = origin;
        this.packet = packet;
    }
}

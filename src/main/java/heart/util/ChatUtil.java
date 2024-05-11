package heart.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {
    public static void notify(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§7[§cHeart§7] §f" + message));
    }
    public static void raw(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(message));
    }
    public static void debug(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("[§cDEBUG§f] " + message));
    }

    public static void sendDiscordMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§7[§9Discord§7] §7" + message));
    }

}

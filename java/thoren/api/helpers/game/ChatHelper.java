package thoren.api.helpers.game;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

/**
 * @author ThorenDragonGem
 */
public class ChatHelper
{
	private static EntityPlayer player;

	/**
	 * Adds a message in the chat.
	 * @param msg the message which you want to reveal in the chat.
	 */
	public static void addChatMsg(String msg)
	{
		player.addChatMessage(new ChatComponentText(msg));
	}
	
	public static ChatComponentTranslation getChatComponentTranslation(String message, Object... format)
	{
		return new ChatComponentTranslation(message, format);
	}
	
	public static ChatComponentText getChatComponent(String message) 
	{
		return new ChatComponentText(message);
	}
}
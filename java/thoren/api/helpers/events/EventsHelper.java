package thoren.api.helpers.events;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import scala.swing.event.Key;
import thoren.api.helpers.game.LogHelper;
import thoren.api.helpers.game.StatTriggersHelper;
import thoren.api.helpers.game.UpdateChecker;

/**
 * @author ThorenDragonGem
 */
public class EventsHelper
{
	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent event)
	{
		List<String> messages = UpdateChecker.getUpdateMessageList();
		if(messages != null && messages.size() > 0)
		{
			try
			{
				for(String message : messages)
				{
					event.player.addChatMessage(new ChatComponentText(message));
				}
			}
			catch(Exception e)
			{
				LogHelper.warning("ThorCore", "The update fail for unknow reasons. This is a big problem! See the stack-trace!");
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event)
	{
		StatTriggersHelper.INSTANCE.notifyCrafting(event.player, event.crafting, event.craftMatrix);
	}
	
	@SubscribeEvent
	public void onItemSmelted(ItemSmeltedEvent event)
	{
		StatTriggersHelper.INSTANCE.notifySmelting(event.player, event.smelting);
	}
	
	@SubscribeEvent
	public void onItemPickedUp(ItemPickupEvent event)
	{
		StatTriggersHelper.INSTANCE.notifyPickup(event.pickedUp, event.player);
	}
}
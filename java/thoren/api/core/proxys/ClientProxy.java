package thoren.api.core.proxys;

import javafx.scene.input.ZoomEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import thoren.api.content.items.tools.ThorBow;
import thoren.api.helpers.events.ZoomEventHelper;

/**
 * @author ThorenDragonGem
 */
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerClientEventHandler()
	{
		FMLCommonHandler.instance().bus().register(new ZoomEventHelper());
	}
	
	@Override
	public void setZoomAmount(ThorBow bow, float zoomAmount)
	{
		ZoomEventHelper.registerBowForZoom(bow, zoomAmount);
	}
}

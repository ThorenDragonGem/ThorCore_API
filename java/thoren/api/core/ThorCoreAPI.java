package thoren.api.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thoren.api.content.inventory.ThorTab;
import thoren.api.core.proxys.CommonProxy;
import thoren.api.helpers.events.EventsHelper;
import thoren.api.helpers.events.KeyBinding;
import thoren.api.helpers.events.KeysHandlerHelper;
import thoren.api.helpers.game.LogHelper;
import thoren.api.thor.ThorMythicCore;

@Mod(modid=ThorInfo.ID, name=ThorInfo.NAME, version=ThorInfo.ID)
public class ThorCoreAPI
{
	@SidedProxy(clientSide = "thoren.api.core.proxys.ClientProxy", serverSide = "thoren.api.core.proxys.CommonProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabs tabThor = new CreativeTabs(CreativeTabs.getNextID(), "thorMythicTab")
	{
		@Override
		public Item getTabIconItem()
		{
			return ThorMythicCore.thorMythicHammer;
		}
	};

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent fmlpreinit)
	{
		LogHelper.info("ThorCore", "ThorCore loading...");
		ThorInfo.setModInfoProperties(fmlpreinit);
		MinecraftForge.EVENT_BUS.register(new EventsHelper());
		MinecraftForge.EVENT_BUS.register(new KeysHandlerHelper());
		FMLCommonHandler.instance().bus().register(new EventsHelper());
		FMLCommonHandler.instance().bus().register(new KeysHandlerHelper());
		KeyBinding.initialize();
		proxy.registerClientEventHandler();
		ThorMythicCore.preInitialize();
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent fmlinit)
	{
		ThorMythicCore.initialize();
	}
	
	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent fmlpostinit)
	{
		ThorMythicCore.postInitialize();
		LogHelper.info("ThorCore", "ThorCore loaded!");
	}
}
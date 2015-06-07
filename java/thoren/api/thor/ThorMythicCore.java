package thoren.api.thor;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import thoren.api.content.blocks.ThorBlock;
import thoren.api.content.blocks.ThorOre;
import thoren.api.content.inventory.ThorCrafting;
import thoren.api.content.items.ThorItem;
import thoren.api.content.items.ThorMaterials;
import thoren.api.content.items.tools.ThorAxe;
import thoren.api.content.items.tools.ThorHoe;
import thoren.api.content.items.tools.ThorPaxade;
import thoren.api.content.items.tools.ThorPaxadoe;
import thoren.api.content.items.tools.ThorPickaxe;
import thoren.api.content.items.tools.ThorSpade;
import thoren.api.core.ThorCoreAPI;
import thoren.api.core.ThorInfo;
import thoren.api.helpers.game.LogHelper;
import thoren.api.helpers.game.ObjectRendererHelper;
import thoren.api.helpers.game.StatTriggersHelper;

public class ThorMythicCore
{
	private static final String ID = ThorInfo.ID;
	public static Item thorMythicDrop;
	public static Item thorMythicShard;
	public static Item thorMythicNugget;
	public static Item thorMythicIngot;
	public static Item thorMythicThunderCore;
	public static Item thorMythicHelm;
	public static Item thorMythicChest;
	public static Item thorMythicLeggs;
	public static Item thorMythicBoots;
	
	public static Item thorMythicHammer;
	public static Item thorMythicSword;
	public static Item thorMythicPickaxe;
	public static Item thorMythicAxe;
	public static Item thorMythicSpade;
	public static Item thorMythicHoe;
	public static Item thorMythicPaxade;
	public static Item thorMythicPaxadoe;
	
	public static Block thorMythicOre;
	public static Block thorMythicBlock;
	
	public static ToolMaterial thorMythicToolMaterial;
	public static ArmorMaterial thorMythicArmorMaterial;
	
	public static Achievement achHammer;
	public static Achievement achIngot;
	
	public static void preInitialize()
	{
		try {Items(); LogHelper.info("ThorCore", "All ThorMythic Items and Materials were loaded successfully!");}
		catch (Exception e) {LogHelper.severe("ThorCore", "There have been an error during the initialization of the ThorMythic Items and Materials. This is a grave problem!"); e.printStackTrace();}
		try {Blocks(); LogHelper.info("ThorCore", "All ThorMythic Blocks were loaded successfully!");}
		catch (Exception e) {LogHelper.severe("ThorCore", "There have been an error during the initialization of the ThorMythic Blocks. This is a grave problem!"); e.printStackTrace();}
	}
	
	public static void initialize()
	{
		try {Recipes(); LogHelper.info("ThorCore", "All ThorMythic Recipes were loaded successfully!");}
		catch (Exception e) {LogHelper.severe("ThorCore", "There have been an error during the initialization of the ThorMythic Recipes. This is a grave problem!"); e.printStackTrace();}
		try {Render(); LogHelper.info("ThorCore", "ThorMythic Renders initialized successfully");}
		catch (Exception e) {LogHelper.severe("ThorCore", "There have been an error during the initialization of the ThorMythic Renders. This is a fatal problem!"); e.printStackTrace();}
	}
	
	public static void postInitialize()
	{
		try {Achievements(); LogHelper.info("ThorCore", "ThorMythic Achievements initialized successfully");}
		catch (Exception e) {LogHelper.severe("ThorCore", "There have been an error during the initialization of the ThorMythic Achievements. This is a grave problem!"); e.printStackTrace();}
	}
	
	public static void Items()
	{
		thorMythicArmorMaterial = new ThorMaterials().addArmorMaterial("THORARMOR", ThorInfo.ID + "thor_armor", 1000, new int[] {6, 15, 8, 5}, 56);
		thorMythicToolMaterial = new ThorMaterials().addToolMaterial("THORTOOL", 10, 3500, 20.0F, 11.0F, 56);

		thorMythicDrop = new ThorItem().setTab(ThorCoreAPI.tabThor).setItemName("thorMythicDrop");
		thorMythicShard = new ThorItem().setTab(ThorCoreAPI.tabThor).setItemName("thorMythicShard");
		thorMythicNugget = new ThorItem().setTab(ThorCoreAPI.tabThor).setItemName("thorMythicNugget");
		thorMythicIngot = new ThorItem().setTab(ThorCoreAPI.tabThor).setItemName("thorMythicIngot").isIngot();
		thorMythicThunderCore = new ThorItem().setTab(ThorCoreAPI.tabThor).setItemName("thorMythicThunderCore");
		
		thorMythicHelm = new ThorMythicArmor(thorMythicArmorMaterial, 0, "thorMythicHelm");
		thorMythicChest = new ThorMythicArmor(thorMythicArmorMaterial, 1, "thorMythicChest");
		thorMythicLeggs = new ThorMythicArmor(thorMythicArmorMaterial, 2, "thorMythicLeggs");
		thorMythicBoots = new ThorMythicArmor(thorMythicArmorMaterial, 3, "thorMythicBoots");
		
		thorMythicHammer = new ThorMythicHammer(thorMythicToolMaterial, "thorMythicHammer");
		thorMythicSword = new ThorMythicSword(thorMythicToolMaterial, "thorMythicSword");
		thorMythicPickaxe = new ThorPickaxe(thorMythicToolMaterial).setTab(ThorCoreAPI.tabThor).setItemName("thorMythicPickaxe").setRepairMaterial(new ItemStack(thorMythicIngot)).setEffect(Enchantment.fortune, 3);
		thorMythicAxe = new ThorAxe(thorMythicToolMaterial).setTab(ThorCoreAPI.tabThor).setItemName("thorMythicAxe").setRepairMaterial(new ItemStack(thorMythicIngot)).setEffect(Enchantment.fortune, 3);
		thorMythicSpade = new ThorSpade(thorMythicToolMaterial).setTab(ThorCoreAPI.tabThor).setItemName("thorMythicSpade").setRepairMaterial(new ItemStack(thorMythicIngot)).setEffect(Enchantment.fortune, 3);
		thorMythicHoe = new ThorHoe(thorMythicToolMaterial).setTab(ThorCoreAPI.tabThor).setItemName("thorMythicHoe").setRepairMaterial(new ItemStack(thorMythicIngot)).setEffect(Enchantment.fortune, 3);
		thorMythicPaxade = new ThorPaxade(thorMythicToolMaterial).setTab(ThorCoreAPI.tabThor).setItemName("thorMythicPaxade").setRepairMaterial(new ItemStack(thorMythicIngot)).setEffect(Enchantment.fortune, 3);
		thorMythicPaxadoe = new ThorPaxadoe(thorMythicToolMaterial).setTab(ThorCoreAPI.tabThor).setItemName("thorMythicPaxadoe").setRepairMaterial(new ItemStack(thorMythicIngot)).setEffect(Enchantment.fortune, 3);
	}
	
	public static void Blocks()
	{
		thorMythicBlock = new ThorBlock(Material.iron).setTab(ThorCoreAPI.tabThor).setBlockName("thorMythicBlock").setBlockHardness(50.0F).setBlockResistance(10000.0F).setBlockHarvestLevel("pickaxe", 10).setBlockStepSound(Block.soundTypeMetal).setBlockLightLevel(2.0F);
		thorMythicOre = new ThorOre(Material.rock).setTab(ThorCoreAPI.tabThor).setBlockName("thorMythicOre").setBlockHardness(20.0F).setBlockResistance(200.0F).setBlockHarvestLevel("pickaxe", 10).setBlockStepSound(Block.soundTypeStone).setBlockDrops(thorMythicDrop, 0, 0, 1);
	}
	
	public static void Recipes()
	{
		//recipes
		ThorCrafting.addShapedRecipe(new ItemStack(thorMythicAxe), new Object[] {" XX", " YX", " Y ", 'X', thorMythicIngot, 'Y', Items.stick});
		ThorCrafting.addRecipe(new ItemStack(thorMythicPickaxe), new Object[] {"XXX", " Y ", " Y ", 'X', thorMythicIngot, 'Y', Items.stick});
		ThorCrafting.addShapedRecipe(new ItemStack(thorMythicSpade), new Object[] {" X ", " Y ", " Y ", 'X', thorMythicIngot, 'Y', Items.stick});
		ThorCrafting.addShapedRecipe(new ItemStack(thorMythicHoe), new Object[] {" XX", " Y ", " Y ", 'X', thorMythicIngot, 'Y', Items.stick});
		ThorCrafting.addRecipe(new ItemStack(thorMythicPaxade), new Object[] {" X ", "AYB", " Y ", 'X', thorMythicPickaxe, 'A', thorMythicAxe, 'B', thorMythicSpade, 'Y', Items.stick});
		ThorCrafting.addShapelessRecipe(new ItemStack(thorMythicPaxadoe), new Object[] {new ItemStack(thorMythicHoe), new ItemStack(thorMythicPaxade)});
		ThorCrafting.addRecipe(new ItemStack(thorMythicBlock), new Object[] {"XXX", "XXX", "XXX", 'X', thorMythicIngot});
		ThorCrafting.addRecipe(new ItemStack(thorMythicShard), new Object[] {"XYX", "YZY", "XYX", 'X', Blocks.diamond_block, 'Y', thorMythicDrop, 'Z', Items.iron_ingot});
		ThorCrafting.addRecipe(new ItemStack(thorMythicShard), new Object[] {"XYX", "YZY", "XYX", 'X', Blocks.diamond_block, 'Y', Blocks.gold_block, 'Z', Items.iron_ingot});
		ThorCrafting.addRecipe(new ItemStack(thorMythicThunderCore), new Object[] {"X  ", "ZXY", "  X", 'X', Blocks.gold_block, 'Y', Blocks.redstone_block, 'Z', Blocks.lapis_block});
		ThorCrafting.addRecipe(new ItemStack(thorMythicHammer), new Object[] {"XYX", " Z ", " Z ", 'X', thorMythicIngot, 'Y', thorMythicThunderCore, 'Z', Items.stick});
		ThorCrafting.addRecipe(new ItemStack(thorMythicSword), new Object[] {" X ", " Y ", " Z ", 'X', thorMythicIngot, 'Y', thorMythicThunderCore, 'Z', Items.stick});
		ThorCrafting.addRecipe(new ItemStack(thorMythicIngot), new Object[] {"XXX", "XXX", "XXX", 'X', thorMythicNugget});
		ThorCrafting.addShapelessRecipe(new ItemStack(thorMythicIngot, 9), new ItemStack(thorMythicBlock));
		ThorCrafting.addShapedRecipe(new ItemStack(thorMythicHelm), new Object[] {"XXX", "X X", 'X', thorMythicIngot});
		ThorCrafting.addShapedRecipe(new ItemStack(thorMythicChest), new Object[] {"X X", "XXX", "XXX", 'X', thorMythicIngot});
		ThorCrafting.addShapedRecipe(new ItemStack(thorMythicLeggs), new Object[] {"XXX", "X X", "X X", 'X', thorMythicIngot});
		ThorCrafting.addShapedRecipe(new ItemStack(thorMythicBoots), new Object[] {"X X", "X X", 'X', thorMythicIngot});

		ThorCrafting.addSmeltingRecipe(new ItemStack(thorMythicShard), new ItemStack(thorMythicNugget), 50.0F);
	}

	public static void Render()
	{
		ObjectRendererHelper.RenderBlock(thorMythicOre, 0);
		ObjectRendererHelper.RenderBlock(thorMythicBlock, 0);
		ObjectRendererHelper.RenderItem(thorMythicDrop, 0);
		ObjectRendererHelper.RenderItem(thorMythicShard, 0);
		ObjectRendererHelper.RenderItem(thorMythicNugget, 0);
		ObjectRendererHelper.RenderItem(thorMythicThunderCore, 0);
		ObjectRendererHelper.RenderItem(thorMythicAxe, 0);
		ObjectRendererHelper.RenderItem(thorMythicBoots, 0);
		ObjectRendererHelper.RenderItem(thorMythicChest, 0);
		ObjectRendererHelper.RenderItem(thorMythicHammer, 0);
		ObjectRendererHelper.RenderItem(thorMythicHelm, 0);
		ObjectRendererHelper.RenderItem(thorMythicHoe, 0);
		ObjectRendererHelper.RenderItem(thorMythicIngot, 0);
		ObjectRendererHelper.RenderItem(thorMythicLeggs, 0);
		ObjectRendererHelper.RenderItem(thorMythicPaxade, 0);
		ObjectRendererHelper.RenderItem(thorMythicPaxadoe, 0);
		ObjectRendererHelper.RenderItem(thorMythicPickaxe, 0);
		ObjectRendererHelper.RenderItem(thorMythicSpade, 0);
		ObjectRendererHelper.RenderItem(thorMythicSword, 0);
	}
	
	public static void Achievements()
	{
		achIngot = (Achievement) new Achievement("achievement.thorIngot", "thorIngot", 7, 7, thorMythicIngot, AchievementList.overpowered).registerStat();
		achHammer = (Achievement) new Achievement("achievement.thorHammer", "thorHammer", 8, 8, thorMythicHammer, achIngot).setSpecial().registerStat();
		
		StatTriggersHelper.INSTANCE.addCraftingTrigger(new ItemStack(thorMythicIngot), achIngot);
		StatTriggersHelper.INSTANCE.addCraftingTrigger(new ItemStack(thorMythicHammer), achHammer);
	}
}
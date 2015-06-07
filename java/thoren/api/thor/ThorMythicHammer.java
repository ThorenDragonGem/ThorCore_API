package thoren.api.thor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiConfigEntries.ChatColorEntry;
import scala.reflect.internal.Trees.Block;
import thoren.api.content.items.tools.ThorSword;
import thoren.api.core.ThorCoreAPI;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

public class ThorMythicHammer extends ThorSword
{
	World world;
	double IposY;
	private FoodStats food = new FoodStats();
	private double addX;
	private double addZ;
	double OE;
	double OF;
	
	public ThorMythicHammer(ToolMaterial toolMaterial, String name)
	{
		super(toolMaterial);
		this.modId("thorcore");
		this.setItemName(name);
		this.setTab(ThorCoreAPI.tabThor);
		this.setRepairMaterial(new ItemStack(ThorMythicCore.thorMythicIngot));
		ContentRegistry.registerItem(this, name, "thorcore", ContentTypes.Item.WEAPON);
		this.addToolTip("The Hammer of Thor!");
		this.addToolTip("Throws Lighting Bolts!");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		this.world = world;
		int yaw = (int)player.rotationYaw;
		if (yaw < 0)
			yaw += 360;
		this.getOrientation(yaw);
		food = player.getFoodStats();
		if (!world.isRemote)
		{
			food = player.getFoodStats();
			if(food.getFoodLevel() > 0)
			{
				world.addWeatherEffect(new EntityLightningBolt(world, player.posX + addX, player.posY, player.posZ + addZ));
				stack.damageItem(2, player);
				food.setFoodLevel(food.getFoodLevel() - 1);
			}
		}
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
		EntityPlayer player = (EntityPlayer) entity;
		ItemStack equiped = player.getCurrentEquippedItem();
		if(equiped == stack)
		{
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30, 1, false, false));
		}
	}
	
	public void getOrientation(int degreeIn)
	{
		double degree = degreeIn + 85; //do not change + 90 (used to make the lighting opposite the eyes)!
		double radius = 5; //OI
		
		double EI;
		double FI;
		
		double radian = Math.toRadians(degree);
		
		OE = radius * Math.cos(radian);
		OF = radius * Math.sin(radian);
		
		EI = Math.sqrt(16 - OE);
		FI = Math.sqrt(16 - OF);
		this.addX = OE;
		this.addZ = OF;
	}
	
	@Deprecated
	public double getPosY(EntityPlayer player)
	{
		BlockPos blockpos = new BlockPos(player.posX + addX, player.posY - 1, player.posZ + addZ);
		IBlockState block = world.getBlockState(blockpos);
		while(block.getBlock() == Blocks.air)
		{
			IposY--;
			if(block.getBlock() != (Blocks.air))
				return IposY;
		}
		player.addChatMessage(new ChatComponentText("" + IposY));
		
		return IposY;
	}
}

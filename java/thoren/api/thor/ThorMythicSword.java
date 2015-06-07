package thoren.api.thor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.content.items.tools.ThorSword;
import thoren.api.core.ThorCoreAPI;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

public class ThorMythicSword extends ThorSword
{
	private FoodStats food = new FoodStats();
	private double addX;
	private double addZ;
	double OE;
	double OF;

	public ThorMythicSword(ToolMaterial toolMaterial, String name)
	{
		super(toolMaterial);
		this.modId("thorcore");
		this.setItemName(name);
		this.setTab(ThorCoreAPI.tabThor);
		this.setRepairMaterial(new ItemStack(ThorMythicCore.thorMythicIngot));
		ContentRegistry.registerItem(this, name, "thorcore", ContentTypes.Item.WEAPON);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			food = player.getFoodStats();
			if(food.getFoodLevel() > 0)
			{
				int yaw = (int)player.rotationYaw;
				EntityTNTPrimed tnt = new EntityTNTPrimed(world, player.posX + addX, player.posY, player.posZ + addZ, player);
				tnt.fuse = 0;
				if (yaw < 0)
				 yaw += 360;
				this.getOrientation(yaw);
				world.spawnEntityInWorld(tnt);
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
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30, 1, false, false));
		}
	}
	
	public void getOrientation(int degreeIn)
	{
		double degree = degreeIn + 90; //do not change + 90 (used to make the lighting opposite the eyes)!
		double radius = 10; //OI
		
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
}
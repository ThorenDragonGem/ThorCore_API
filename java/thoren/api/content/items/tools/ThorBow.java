package thoren.api.content.items.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.core.ThorCoreAPI;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

import com.google.common.collect.Lists;

/**
 * @author AleXndrTheGr8st
 */
public class ThorBow extends ItemBow
{
	private boolean hasToolTip;
	private float zoomAmount = 0.22F;
	private HashMap<ThorBowEffects, Object> effects = new HashMap<ThorBowEffects, Object>();
	private static HashMap<String, List<ThorBow>> bowWithModIdMap = new HashMap<String, List<ThorBow>>();
	private ItemStack repairMaterial;
	private List<String> toolTipStrings = Lists.newArrayList();
	private String bowTypeName;
	private String modId;
	
	/**
	 * Creates a ThorBow.
	 * @param maxDamage the durability of the item.
	 */
	public ThorBow(int maxDamage)
	{
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(maxDamage);
		this.bFull3D = true;
	}
	
	/**
	 * Adds a tool tip on the weapon.
	 * @param toolTip the String displayed on the weapon.
	 * @return ThorBow ThorBow
	 */
	public ThorBow addToolTip(String toolTip)
	{
		this.toolTipStrings.add(toolTip);
		this.hasToolTip = true;
		return this;
	}
	
	/**
	 * Sets the name of the item.
	 * @param unlocalizedName the name of the item.
	 * @return ThorBow
	 */
	public ThorBow setItemName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerItem(this, unlocalizedName);
		ContentRegistry.registerItem(this, unlocalizedName, modId, ContentTypes.Item.WEAPON);
		ThorCoreAPI.proxy.setZoomAmount(this, this.zoomAmount);
		return this;
	}

	/**
	 * Sets the modId of the item.
	 * @param modId the modId of the item.
	 * @return ThorBow
	 */
	public ThorBow modId(String modId)
	{
		List<ThorBow> list = Lists.newArrayList();
		list.add(this);
		this.modId = modId;
		if(this.bowWithModIdMap.containsKey(modId))
			this.bowWithModIdMap.get(modId).add(this);
		else
			this.bowWithModIdMap.put(modId, list);
		return this;
	}
	
	/**
	 * Adds an effect to the item.
	 * @param effect the effect to be added to the bow. See ThorBowEffects.
	 * @return ThorBow
	 */
	public ThorBow setEffect(ThorBowEffects effect)
	{
		this.effects.put(effect, null);
		return this;
	}
	
	/**
	 * Adds an effect to the item.
	 * @param effect the effect to be added to the bow. See ThorBowEffects.
	 * @param modifier the modifier of the effect (FLOAT modifier).
	 * @return ThorBow
	 */
	public ThorBow setEffect(ThorBowEffects effect, float modifier)
	{
		this.effects.put(effect, modifier);
		return this;
	}
	
	/**
	 * Adds an effect to the item.
	 * @param effect the effect to be added to the bow. See ThorBowEffects.
	 * @param modifier the modifier of the effect (INT modifier, like enchantment levels).
	 * @return ThorBow
	 */
	public ThorBow setEffect(ThorBowEffects effect, int modifier)
	{
		this.effects.put(effect, modifier);
		return this;
	}
	
	/**
	 * Sets the repair material of the weapon.
	 * @param repairMaterial the repair material of the weapon.
	 * @return ThorBow
	 */
	public ThorBow setRepairMaterial(ItemStack repairMaterial)
	{
		this.canRepair = true;
		this.repairMaterial = repairMaterial;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the item will be displayed.
	 * @param creativeTab the CreativeTabs.
	 * @return ThorBow
	 */
	public ThorBow setTab(CreativeTabs creativeTab)
	{
		this.setCreativeTab(creativeTab);
		return this;
	}
	
	/**
	 * Sets the speed of the zoom.
	 * @param zoomAmount the amount that the bow should zoom in to (default: 0.22F).
	 * @return ThorBow
	 */
	public ThorBow setZoomAmount(float zoomAmount)
	{
		this.zoomAmount = zoomAmount;
		return this;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if(hasToolTip)
			for(String toolTip : this.toolTipStrings)
				list.add(StatCollector.translateToLocal(toolTip));
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return this.repairMaterial.getItem() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	public static List<ThorBow> getItemListFromModId(String modId)
	{
		if(bowWithModIdMap.containsKey(modId))
			return bowWithModIdMap.get(modId);
		else
			return Lists.newArrayList();
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int maxUseDuration = getMaxItemUseDuration(par1ItemStack) - par4;
		ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, maxUseDuration);
		MinecraftForge.EVENT_BUS.post(event);
		
		if(event.isCanceled())
			return;

		boolean infArrows = (par3EntityPlayer.capabilities.isCreativeMode) || (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0 || (this.effects.containsKey(ThorBowEffects.infiniteArrows)));
		
		if((infArrows) || (par3EntityPlayer.inventory.hasItem(Items.arrow)))
		{
			float f = maxUseDuration / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;
			
			if(f < 0.1D)
				return;
			
			if(f > 1.0F)
				f = 1.0F;
				
			EntityArrow arrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);
			int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
			int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
			int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack);
			boolean efficient = false;
			
			if(f == 1.0F)
				arrow.setIsCritical(true);
			
			if(powerLevel > 0)
				arrow.setDamage(arrow.getDamage() + powerLevel * 0.5D + 0.5D);
			
			if(punchLevel > 0)
				arrow.setKnockbackStrength(punchLevel);
			
			if(flameLevel > 0 || effects.containsKey(ThorBowEffects.flameEffect))
				arrow.setFire(100);
			
			if(f == 1.0F && this.effects.containsKey(ThorBowEffects.critFlameEffect))
				arrow.setFire(100);
			
			if(effects.containsKey(ThorBowEffects.damageEffect))
				arrow.setDamage(arrow.getDamage() *  (Float) effects.get(ThorBowEffects.damageEffect));
			
			if(effects.containsKey(ThorBowEffects.knockbackEffect))
				arrow.setKnockbackStrength(punchLevel > 0 ? punchLevel + (Integer) effects.get(ThorBowEffects.knockbackEffect) : (Integer) effects.get(ThorBowEffects.knockbackEffect));
			
			if(effects.containsKey(ThorBowEffects.efficiencyEffect))
				efficient = randomChance((Integer) effects.get(ThorBowEffects.efficiencyEffect));
			
			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			
			if(infArrows || efficient)
				arrow.canBePickedUp = 2;
			
			else
				par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
			
			if(!par2World.isRemote)
				par2World.spawnEntityInWorld(arrow);
		}
	}
	
	public boolean randomChance(int chance)
	{
		Random random = new Random();
		int rand = random.nextInt(100);
		
		if(rand <= chance)
			return true;
		else
			return false;
	}
}
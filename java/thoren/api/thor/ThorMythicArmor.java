package thoren.api.thor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.content.items.tools.ThorArmor;
import thoren.api.core.ThorCoreAPI;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

public class ThorMythicArmor extends ThorArmor
{
	public ThorMythicArmor(ArmorMaterial material, int slotnumber, String name)
	{
		super(material, slotnumber);
		this.setItemName(name);
		this.setTab(ThorCoreAPI.tabThor);
		this.setRepairMaterial(new ItemStack(ThorMythicCore.thorMythicIngot));
		ContentRegistry.registerItem(this, name, "thorcore", ContentTypes.Item.ARMOR);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		
		if(player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == ThorMythicCore.thorMythicHelm
		        && player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() == ThorMythicCore.thorMythicChest
		        && player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() == ThorMythicCore.thorMythicLeggs
		        && player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() == ThorMythicCore.thorMythicBoots)
		{
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 40, 1, true, false));
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 40, 1, true, false));
			player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 40, 1, true, false));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 40, 1, true, false));
		}
	}
}
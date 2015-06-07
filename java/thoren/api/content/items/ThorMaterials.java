package thoren.api.content.items;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

/**
 * @author ThorenDragonGem
 */
public class ThorMaterials
{
	/**
	 * Adds a new Tool Material.
	 * @param name the name of the tool material.
	 * @param harvestLevel the harvest level of the tools which have material.
	 * @param maxUses the durability of the tools which have this material.
	 * @param efficiency the efficiency of the tools which have this material.
	 * @param damage the damages of the tool material.
	 * @param enchantability the enchantability of the tool material.
	 */
	public static ToolMaterial addToolMaterial(String name, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability)
	{
		return EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damage, enchantability);
	}
	
	/**
	 * Adds a new Armor Material.
	 * @param name the name of the armor material.
	 * @param textureName the base texture name of the armorMaterial.
	 * @param durability the durability of the armor material.
	 * @param reductionAmounts the damage reduction array for every pieces of the armor.
	 * @param enchantability the enchantability of the armor material.
	 */
	public static ArmorMaterial addArmorMaterial(String name, String textureName, int durability, int[] reductionAmounts, int enchantability)
	{
		return EnumHelper.addArmorMaterial(name, textureName, durability, reductionAmounts, enchantability);
	}
}
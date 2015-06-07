package thoren.api.content.enchantments;

import thoren.api.core.ThorCoreAPI;
import thoren.api.core.ThorInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ThorEnchantment extends Enchantment
{
	private int maxLevel;
	private int minLevel;
	private Item item;
    private Enchantment ban;
    private Enchantment ban2;
    private Enchantment ban3;
    private Enchantment ban4;
    private Enchantment banthis;
	
    /**
	 * @param id: ID for the enchantment being added.
	 * @param weight: How often the enchantment shows up.
	 * @param unlocalizedName: Name for the enchantment. (unlocalized)
	 * @param minLevel: The lowest possible level of enchantment.
	 * @param maxLevel: The highest possible level of enchantment.
	 */
    public ThorEnchantment(int id, int weight, EnumEnchantmentType type, ResourceLocation unlocalizedName, int minLevel, int maxLevel, Enchantment par1, Enchantment par2,Enchantment par3, Enchantment par4)
    {

        super(id,unlocalizedName, weight, type);
        this.name = ThorInfo.ID + "." + unlocalizedName;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.ban = par1;
        this.ban2 = par2;
        this.ban3 = par3;
        this.ban4 = par4;
        this.banthis = this;
        addToBookList(this);
    }
    
    public int getMinLevel()
    {
    	return this.minLevel;
    }
    
    public int getMaxLevel()
    {
    	return this.maxLevel;
    }
    
    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1 + enchantmentLevel * 10;
    }

    /**
     * Returns the maximum value of enchantability needed on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 5;
    }
}
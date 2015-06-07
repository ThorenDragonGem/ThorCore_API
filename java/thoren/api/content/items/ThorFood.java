package thoren.api.content.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;
import thoren.api.helpers.game.RenderTypes;

/**
 * @author ThorenDragonGem
 */
public class ThorFood extends ItemFood
{
	/** @default = 32 */
	private int eatduration = 32;
	private boolean alwaysEdible = false;
	private static String modId = "";

	/**
	 * Creates a ThorFood.
	 * @param heal the heal amount gave to the player on eating.
	 * @param saturation the saturation amount gave to the player on eating.
	 * @param wolfmeat if wolf can be eat this food.
	 */
	public ThorFood(int heal, float saturation, boolean wolfmeat)
	{
		super(heal, saturation, wolfmeat);
	}
	
	/**
	 * Sets the name of the food.
	 * @param unlocalizedName the name of the food.
	 * @return ThorFood
	 */
	public ThorFood setItemName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerItem(this, unlocalizedName);
		ContentRegistry.registerItem(this, unlocalizedName, modId, ContentTypes.Item.FOOD);
		return this;
	}
	
	/**
	 * Sets the modId of the food.
	 * @param modId the modId of the food.
	 * @return ThorFood
	 */
	public ThorFood modId(String modId)
	{
		this.modId = modId;
		return this;
	}
	
	/**
	 * Sets the CreativeTabs where the item will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorFood
	 */
	public ThorFood setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets how long the player can eat this item.
	 * @param duration the time duration of eating (default: 32).
	 * @return ThorFood
	 */
	public ThorFood setEatDuration(int duration)
	{
		this.eatduration  = duration;
		return this;
	}
	
	/**
	 * Sets the food as always edible.
	 * @return ThorFood
	 */
	public ThorFood isAlwaysEdible()
	{
		this.alwaysEdible = true;
		return this;
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(player.canEat(this.alwaysEdible))
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }
        return stack;
    }
}
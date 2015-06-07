package thoren.api.content.potions;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class ThorPotionEffect extends Potion
{
	/**
	 * Creates a ThorPotion.
	 * @param potionID the id of the potion. Must be greater than 32.
	 * @param location the resource location of the potion: (new ResourceLocation("id").
	 * @param badEffect if the potion has a bad effect.
	 * @param potionColor the color of the potion (hexadecimal without 0x).
	 */
	public ThorPotionEffect(int potionID, ResourceLocation location, boolean badEffect, int potionColor)
	{
		super(potionID, location, badEffect, potionColor);
	}
	
	/**
	 * Sets the potion effect name.
	 * @param name the name of the potion (Warning: not the id!).
	 * @return ThorPotionEffect
	 */
	public ThorPotionEffect setPotionEffectName(String name)
	{
		this.setPotionName(name);
		return this;
	} 
	
	/**
	 * Sets the potion effect instantanate (harming & heal).
	 * @return ThorPotionEffect
	 */
	public ThorPotionEffect isInstantanate()
	{
		this.isInstant();
		return this;
	}
	
	/**
	 * Sets the potion as bad (negative). 
	 * @return ThorPotionEffect
	 */
	public ThorPotionEffect isBad()
	{
		this.isBadEffect();
		return this;
	}
}
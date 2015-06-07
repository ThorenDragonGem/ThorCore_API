package thoren.api.content.items.tools;

/**
 * @author AleXndrThrGr8st and ThorenDragonGem
 */
public class ThorBowEffects 
{
	public static final ThorBowEffects critFlameEffect = new ThorBowEffects();
	
	/**
	 * Adds a flame effect to the arrows.
	 * Modifier: none.
	 */
	public static final ThorBowEffects flameEffect = new ThorBowEffects();
	
	/**
	 * Allows a single arrow to be used infinitely, ie. without consuming any arrows each shot.
	 * Modifier: none.
	 */
	public static final ThorBowEffects infiniteArrows = new ThorBowEffects();
	
	//Float modifiers.
	/**
	 * Multiplier for the damage of the bow. Similar to the Power enchantment.
	 * Modifier: float damage multiplier (ie. 1.5F).
	 */
	public static final ThorBowEffects damageEffect = new ThorBowEffects();
	
	/**
	 * Multiplier for the knockback of the bow. Similar to the Punch enchantment.
	 * Modifier: float knockback multiplier (ie. 2.5F).
	 */
	public static final ThorBowEffects knockbackEffect = new ThorBowEffects();
	
	//Integer modifiers.
	/**
	 * Adds an efficiency to the bow, resulting in a chance that an arrow won't be consumed.
	 * Modifier: integer efficiency percentage (i.e 30 = 30%).
	 */
	public static final ThorBowEffects efficiencyEffect = new ThorBowEffects();
	
	public static final ThorBowEffects potionEffect = new ThorBowEffects();
	
	public static final ThorBowEffects speedShotEffect = new ThorBowEffects();
}

package thoren.api.content.blocks.decorative;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

import com.google.common.collect.Lists;

/**
 * @author ThorenDragonGem
 */
public class ThorStair extends BlockStairs
{
	private String modId = "";
	private String toolClass;
	private int toolLevel;
	private ThorStair object;
	private boolean isbeaconbase = false;
	private static HashMap<String, List<ThorStair>> blockWithmodIDMap = new HashMap<String, List<ThorStair>>();

	/**
	 * Creates a ThorStair.
	 * @param material the material of the block. See Material.
	 */
	public ThorStair(IBlockState modelState)
	{
		super(modelState);
	}
	
	/**
	 * Sets the modId of the block.
	 * @param modId the modId of the block
	 * @return ThorStair
	 */
	public ThorStair modId(String modId)
	{
		List<ThorStair> list = Lists.newArrayList();
		list.add(this);
		this.modId = modId;
		if(this.blockWithmodIDMap.containsKey(modId))
			this.blockWithmodIDMap.get(modId).add(this);
		else
			this.blockWithmodIDMap.put(modId, list);
		return this;
	}
	
	/**
	 * Sets the name of the block.
	 * @param name the name of the block.
	 * @return ThorStair
	 */
	public ThorStair setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		this.object = this;
		GameRegistry.registerBlock(this, name);
		ContentRegistry.registerBlock(this, name, modId, ContentTypes.Block.DECORATIVE);
		return this;
	}
	
	/**
	 * Sets the hardness of the block.
	 * @param hardness the hardness amount of the block (depends of materials, stone: 1.5F, obsidian: 50.0F).
	 * @return ThorStair
	 */
	public ThorStair setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block (depends of materials, stone: 10.0F, obsidian: 2000.0F).
	 * @return ThorStair
	 */
	public ThorStair setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the harvest tool and level of the block.
	 * @param toolclass the tool class which can break the block.
	 * @param toollevel the level of the tool. Wood: 0, Stone: 1, Iron: 2, Diamond: 3, Gold: 0
	 * @return ThorStair
	 */
	public ThorStair setBlockHarvestLevel(String toolclass, int toollevel)
	{
		this.setHarvestLevel(toolclass, toollevel);
		this.toolClass = toolclass;
		this.toolLevel = toollevel;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorStair
	 */
	public ThorStair setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets what sound is played when you place, or break the block. See Block at soundTypes.
	 * @param sound the sound played.
	 * @return ThorStair
	 */
	public ThorStair setBlockStepSound(Block.SoundType sound)
	{
		this.setStepSound(sound);
		return this;
	}
	
	/**
	 * Sets the light level emitted by the block.
	 * @param lightlevel the light level emitted.
	 * @return ThorStair
	 */
	public ThorStair setBlockLightLevel(float lightlevel)
	{
		this.setLightLevel(lightlevel);
		return this;
	}

	/**
	 * Sets the block as unbreakable.
	 * @return ThorStair
	 */
	public ThorStair isUnbreakable()
	{
		this.setBlockUnbreakable();
		return this;
	}
	
	/**
	 * Sets if the block receive randomly ticks.
	 * @param tick true for receiving, and false for not.
	 * @return ThorStair
	 */
	public ThorStair setBlockTickRandomly(boolean tick)
	{
		this.setTickRandomly(tick);
		return this;
	}
	
	/**
	 * Sets if the block can be a part of a beacon base.
	 * @param isbeaconbase if the block can be a part of a beacon base.
	 * @return ThorStair
	 */
	public ThorStair setBeaconBase(boolean isbeaconbase)
	{
		this.isbeaconbase = isbeaconbase;
		return this;
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
		return this == object;
    }
	
	@Override
	public String getHarvestTool(IBlockState state)
	{
		return toolClass;
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return toolLevel;
	}
}
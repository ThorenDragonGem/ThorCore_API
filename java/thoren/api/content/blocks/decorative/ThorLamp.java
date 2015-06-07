package thoren.api.content.blocks.decorative;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

import com.google.common.collect.Lists;

public class ThorLamp extends Block
{
    private final boolean isOn;
    private Block offLamp;
    private Block onLamp;
	private String modId = "";
	private String toolClass;
	private int toolLevel;
	private ThorLamp object;
	private static HashMap<String, List<ThorLamp>> blockWithmodIDMap = new HashMap<String, List<ThorLamp>>();

	/**
	 * Creates a ThorLamp.
	 * @param isOn if the lamp is on.
	 */
    public ThorLamp(boolean isOn)
    {
        super(Material.redstoneLight);
        this.isOn = isOn;
        if(isOn)
        	this.onLamp = this;
        else
        	this.offLamp = this;
        if(isOn)
        {
            this.setLightLevel(1.0F);
        }
    }

	/**
	 * Sets the modId of the block.
	 * @param modId the modId of the block
	 * @return ThorLamp
	 */
	public ThorLamp modId(String modId)
	{
		List<ThorLamp> list = Lists.newArrayList();
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
	 * @return ThorLamp
	 */
	public ThorLamp setBlockName(String name)
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
	 * @return ThorLamp
	 */
	public ThorLamp setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block (depends of materials, stone: 10.0F, obsidian: 2000.0F).
	 * @return ThorLamp
	 */
	public ThorLamp setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the harvest tool and level of the block.
	 * @param toolclass the tool class which can break the block.
	 * @param toollevel the level of the tool. Wood: 0, Stone: 1, Iron: 2, Diamond: 3, Gold: 0
	 * @return ThorLamp
	 */
	public ThorLamp setBlockHarvestLevel(String toolclass, int toollevel)
	{
		this.setHarvestLevel(toolclass, toollevel);
		this.toolClass = toolclass;
		this.toolLevel = toollevel;
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorLamp
	 */
	public ThorLamp setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets what sound is played when you place, or break the block. See Block at soundTypes.
	 * @param sound the sound played.
	 * @return ThorLamp
	 */
	public ThorLamp setBlockStepSound(Block.SoundType sound)
	{
		this.setStepSound(sound);
		return this;
	}

    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        if (!world.isRemote)
        {
            if (this.isOn && !world.isBlockPowered(pos))
            {
                world.setBlockState(pos, offLamp.getDefaultState(), 2);
            }
            else if (!this.isOn && world.isBlockPowered(pos))
            {
                world.setBlockState(pos, onLamp.getDefaultState(), 2);
            }
        }
    }

    /**
     * Called when a neighboring block changes.
     */
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!world.isRemote)
        {
            if (this.isOn && !world.isBlockPowered(pos))
            {
                world.scheduleUpdate(pos, this, 4);
            }
            else if (!this.isOn && world.isBlockPowered(pos))
            {
                world.setBlockState(pos, onLamp.getDefaultState(), 2);
            }
        }
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (!world.isRemote)
        {
            if (this.isOn && !world.isBlockPowered(pos))
            {
                world.setBlockState(pos, offLamp.getDefaultState(), 2);
            }
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     *  
     * @param fortune the level of the Fortune enchantment on the player's tool
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(offLamp);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos)
    {
        return Item.getItemFromBlock(offLamp);
    }

    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(offLamp);
    }
}
package thoren.api.content.blocks.decorative;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

/**
 * @author AleXndrTheGr8st
 */
public class ThorPane extends BlockPane
{
	private int meta;
	private String modId;
	private boolean canDropItself;

	/**
	 * Creates a ThorPane.
	 * @param meta the meta-data of the pane.
	 */
	public ThorPane(int meta)
	{
		super(Material.iron, true);
		this.canDropItself = true;
		this.meta = meta;
	}
	
	/**
	 * Sets the which modId the block belongs to. Used to find the textures.
	 * Should be set before the other properties.
	 * @param modId The modId of the plugin the block belongs to.
	 * @return ThorPane
	 */
	public ThorPane modId(String modId)
	{
		this.modId = modId;
		return this;
	}
	
	/**
	 * Sets the blockName of the block, and also registers the block with the GameRegistry.
	 * @param blockName The name of the block (unlocalized).
	 * @return ThorPane
	 */
	public ThorPane setBlockName(String blockName)
	{
		super.setUnlocalizedName(blockName);
		GameRegistry.registerBlock(this, blockName);
		ContentRegistry.registerBlock(this, blockName, modId, ContentTypes.Block.DECORATIVE);
		return this;
	}
	
	/**
	 * Sets the hardness of the block.
	 * @param hardness the hardness amount of the block (depends of materials, stone: 1.5F, obsidian: 50.0F).
	 * @return ThorPane
	 */
	public ThorPane setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block (depends of materials, stone: 10.0F, obsidian: 2000.0F).
	 * @return ThorPane
	 */
	public ThorPane setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the harvest tool and level of the block.
	 * @param toolclass the tool class which can break the block.
	 * @param toollevel the level of the tool. Wood: 0, Stone: 1, Iron: 2, Diamond: 3, Gold: 0
	 * @return ThorPane
	 */
	public ThorPane setBlockHarvestLevel(String toolclass, int toollevel)
	{
		this.setHarvestLevel(toolclass, toollevel);
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorPane
	 */
	public ThorPane setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets what sound is played when you place, or break the block. See Block at soundTypes.
	 * @param sound the sound played.
	 * @return ThorPane
	 */
	public ThorPane setBlockStepSound(Block.SoundType sound)
	{
		this.setStepSound(sound);
		return this;
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return !this.canDropItself ? null : super.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return 18;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
    	return worldIn.getBlockState(pos).getBlock() == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
	}
	
	@Override
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
    {
        boolean flag = this.canPaneConnectTo(worldIn, pos, EnumFacing.NORTH);
        boolean flag1 = this.canPaneConnectTo(worldIn, pos, EnumFacing.SOUTH);
        boolean flag2 = this.canPaneConnectTo(worldIn, pos, EnumFacing.WEST);
        boolean flag3 = this.canPaneConnectTo(worldIn, pos, EnumFacing.EAST);

        if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1))
        {
            if (flag2)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
                super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
            }
            else if (flag3)
            {
                this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
                super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
            }
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        }

        if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))
        {
            if (flag)
            {
                this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
                super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
            }
            else if (flag1)
            {
                this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
                super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
            }
        }
        else
        {
            this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
            super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        }
    }
	
    @Override
	public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        float f = 0.4375F;
        float f1 = 0.5625F;
        float f2 = 0.4375F;
        float f3 = 0.5625F;
        boolean flag = this.canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock());
        boolean flag1 = this.canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock());
        boolean flag2 = this.canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock());
        boolean flag3 = this.canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock());

        if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1))
        {
            if (flag2)
            {
                f = 0.0F;
            }
            else if (flag3)
            {
                f1 = 1.0F;
            }
        }
        else
        {
            f = 0.0F;
            f1 = 1.0F;
        }

        if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))
        {
            if (flag)
            {
                f2 = 0.0F;
            }
            else if (flag1)
            {
                f3 = 1.0F;
            }
        }
        else
        {
            f2 = 0.0F;
            f3 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }
    
    @Override
	public boolean canSilkHarvest()
    {
        return true;
    }
    
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return meta;
    }
    
    @Override
    public BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {NORTH, EAST, WEST, SOUTH});
    }
}
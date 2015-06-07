package thoren.api.content.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

public class ThorFenceGate extends BlockFenceGate
{
    private ThorFenceGate object;
	private String modId;

	public ThorFenceGate()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(OPEN, Boolean.valueOf(false)).withProperty(POWERED, Boolean.valueOf(false)).withProperty(IN_WALL, Boolean.valueOf(false)));
    }

	/**
	 * Sets the modId of the block.
	 * @param modId the modId of the block
	 * @return ThorFenceGate
	 */
	public ThorFenceGate modId(String modId)
	{
		this.modId = modId;
		return this;
	}
	
	/**
	 * Sets the name of the block.
	 * @param name the name of the block.
	 * @return ThorFenceGate
	 */
	public ThorFenceGate setBlockName(String name)
	{
		this.setUnlocalizedName(name);
		this.object = this;
		GameRegistry.registerBlock(this, name);
		ContentRegistry.registerBlock(this, name, modId, ContentTypes.Block.GENERAL);
		return this;
	}
	
	/**
	 * Sets the hardness of the block.
	 * @param hardness the hardness amount of the block (depends of materials, stone: 1.5F, obsidian: 50.0F).
	 * @return ThorFenceGate
	 */
	public ThorFenceGate setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block (depends of materials, stone: 10.0F, obsidian: 2000.0F).
	 * @return ThorFenceGate
	 */
	public ThorFenceGate setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the harvest tool and level of the block.
	 * @param toolclass the tool class which can break the block.
	 * @param toollevel the level of the tool. Wood: 0, Stone: 1, Iron: 2, Diamond: 3, Gold: 0
	 * @return ThorFenceGate
	 */
	public ThorFenceGate setBlockHarvestLevel(String toolclass, int toollevel)
	{
		this.setHarvestLevel(toolclass, toollevel);
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorFenceGate
	 */
	public ThorFenceGate setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid() ? super.canPlaceBlockAt(worldIn, pos) : false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        if (((Boolean)state.getValue(OPEN)).booleanValue())
        {
            return null;
        }
        else
        {
            EnumFacing.Axis axis = ((EnumFacing)state.getValue(FACING)).getAxis();
            return axis == EnumFacing.Axis.Z ? new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)((float)pos.getZ() + 0.375F), (double)(pos.getX() + 1), (double)((float)pos.getY() + 1.5F), (double)((float)pos.getZ() + 0.625F)) : new AxisAlignedBB((double)((float)pos.getX() + 0.375F), (double)pos.getY(), (double)pos.getZ(), (double)((float)pos.getX() + 0.625F), (double)((float)pos.getY() + 1.5F), (double)(pos.getZ() + 1));
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing.Axis axis = ((EnumFacing)worldIn.getBlockState(pos).getValue(FACING)).getAxis();

        if (axis == EnumFacing.Axis.Z)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
        }
        else
        {
            this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
        }
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return ((Boolean)worldIn.getBlockState(pos).getValue(OPEN)).booleanValue();
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(OPEN, Boolean.valueOf(false)).withProperty(POWERED, Boolean.valueOf(false)).withProperty(IN_WALL, Boolean.valueOf(false));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (((Boolean)state.getValue(OPEN)).booleanValue())
        {
            state = state.withProperty(OPEN, Boolean.valueOf(false));
            worldIn.setBlockState(pos, state, 2);
        }
        else
        {
            EnumFacing enumfacing1 = EnumFacing.fromAngle((double)playerIn.rotationYaw);

            if (state.getValue(FACING) == enumfacing1.getOpposite())
            {
                state = state.withProperty(FACING, enumfacing1);
            }

            state = state.withProperty(OPEN, Boolean.valueOf(true));
            worldIn.setBlockState(pos, state, 2);
        }

        worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue(OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
        return true;
    }

    /**
     * Called when a neighboring block changes.
     */
    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!worldIn.isRemote)
        {
            boolean flag = worldIn.isBlockPowered(pos);

            if (flag || neighborBlock.canProvidePower())
            {
                if (flag && !((Boolean)state.getValue(OPEN)).booleanValue() && !((Boolean)state.getValue(POWERED)).booleanValue())
                {
                    worldIn.setBlockState(pos, state.withProperty(OPEN, Boolean.valueOf(true)).withProperty(POWERED, Boolean.valueOf(true)), 2);
                    worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1003, pos, 0);
                }
                else if (!flag && ((Boolean)state.getValue(OPEN)).booleanValue() && ((Boolean)state.getValue(POWERED)).booleanValue())
                {
                    worldIn.setBlockState(pos, state.withProperty(OPEN, Boolean.valueOf(false)).withProperty(POWERED, Boolean.valueOf(false)), 2);
                    worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1006, pos, 0);
                }
                else if (flag != ((Boolean)state.getValue(POWERED)).booleanValue())
                {
                    worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(flag)), 2);
                }
            }
        }
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING, OPEN, POWERED, IN_WALL});
    }
}
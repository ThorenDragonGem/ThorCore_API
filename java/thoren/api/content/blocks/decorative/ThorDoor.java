package thoren.api.content.blocks.decorative;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

/**
 * @author ThorenDragonGem
 */
public class ThorDoor extends BlockDoor
{
	private String modId;
	private ItemStack stackToDrop;
	private Item itemDoor;
	private boolean requirePower = false;
	private ThorDoor object;
	
	/**
	 * Creates a new ThorDoor with the specified material.
	 * @param requiresPower Whether or not the door requires power to be opened.
	 */
	public ThorDoor(Material material, boolean requiresPower, Item itemDoor)
	{
		super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, Boolean.valueOf(false)).withProperty(HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(POWERED, Boolean.valueOf(false)).withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER));
		this.itemDoor = itemDoor;
		this.requirePower = requiresPower;
	}
	
	/**
	 * Sets the which modId the block belongs to. Used to find the textures.
	 * Should be set before the other properties.
	 * @param modId The modId of the plugin the block belongs to.
	 * @return ThorDoor
	 */
	public ThorDoor modId(String modId)
	{
		this.modId = modId;
		return this;
	}
	
	/**
	 * Sets the name of the block.
	 * @param name the name of the block.
	 * @return ThorDoor
	 */
	public ThorDoor setBlockName(String name)
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
	 * @return ThorDoor
	 */
	public ThorDoor setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block (depends of materials, stone: 10.0F, obsidian: 2000.0F).
	 * @return ThorDoor
	 */
	public ThorDoor setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the harvest tool and level of the block.
	 * @param toolclass the tool class which can break the block.
	 * @param toollevel the level of the tool. Wood: 0, Stone: 1, Iron: 2, Diamond: 3, Gold: 0
	 * @return ThorDoor
	 */
	public ThorDoor setBlockHarvestLevel(String toolclass, int toollevel)
	{
		this.setHarvestLevel(toolclass, toollevel);
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs.
	 * @return ThorDoor
	 */
	public ThorDoor setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets an itemstack to drop when the block is destroyed. Only used if the block drops something other than itself.
	 * @param stack ItemStack to drop when block is destroyed.
	 * @return ThorDoor
	 */
	public ThorDoor setStackToDrop(ItemStack stack)
	{
		this.stackToDrop = stack;
		return this;
	}
	
	@Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return isOpen(combineMetadata(worldIn, pos));
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
    {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        return super.getSelectedBoundingBox(worldIn, pos);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        return super.getCollisionBoundingBox(worldIn, pos, state);
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : this.getItem();
    }
	
    @Override
	public int getMobilityFlag()
    {
        return 1;
    }
	
    @Override
	public int getRenderType(){
        return 7;
    }
    
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return pos.getY() >= worldIn.getHeight() - 1 ? false : World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end)
    {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        return super.collisionRayTrace(worldIn, pos, start, end);
    }
	
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (this.requirePower = true)
        {
            return false; //Allow items to interact with the door
        }
        else
        {
            BlockPos blockpos1 = state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
            IBlockState iblockstate1 = pos.equals(blockpos1) ? state : worldIn.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this)
            {
                return false;
            }
            else
            {
                state = iblockstate1.cycleProperty(OPEN);
                worldIn.setBlockState(blockpos1, state, 2);
                worldIn.markBlockRangeForRenderUpdate(blockpos1, pos);
                worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue(OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
                return true;
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER)
        {
            BlockPos blockpos1 = pos.down();
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
            }
            else if (neighborBlock != this)
            {
                this.onNeighborBlockChange(worldIn, blockpos1, iblockstate1, neighborBlock);
            }
        }
        else
        {
            boolean flag1 = false;
            BlockPos blockpos2 = pos.up();
            IBlockState iblockstate2 = worldIn.getBlockState(blockpos2);

            if (iblockstate2.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;
            }

            if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down()))
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;

                if (iblockstate2.getBlock() == this)
                {
                    worldIn.setBlockToAir(blockpos2);
                }
            }

            if (flag1)
            {
                if (!worldIn.isRemote)
                {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            }
            else
            {
                boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos2);

                if ((flag || neighborBlock.canProvidePower()) && neighborBlock != this && flag != ((Boolean)iblockstate2.getValue(POWERED)).booleanValue())
                {
                    worldIn.setBlockState(blockpos2, iblockstate2.withProperty(POWERED, Boolean.valueOf(flag)), 2);

                    if (flag != ((Boolean)state.getValue(OPEN)).booleanValue())
                    {
                        worldIn.setBlockState(pos, state.withProperty(OPEN, Boolean.valueOf(flag)), 2);
                        worldIn.markBlockRangeForRenderUpdate(pos, pos);
                        worldIn.playAuxSFXAtEntity((EntityPlayer)null, flag ? 1003 : 1006, pos, 0);
                    }
                }
            }
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        this.setBoundBasedOnMeta(combineMetadata(worldIn, pos));
    }
    
    private void setBoundBasedOnMeta(int combinedMeta)
    {
        float f = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        EnumFacing enumfacing = getFacing(combinedMeta);
        boolean flag = isOpen(combinedMeta);
        boolean flag1 = isHingeLeft(combinedMeta);

        if (flag)
        {
            if (enumfacing == EnumFacing.EAST)
            {
                if (!flag1)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                }
            }
            else if (enumfacing == EnumFacing.SOUTH)
            {
                if (!flag1)
                {
                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                }
            }
            else if (enumfacing == EnumFacing.WEST)
            {
                if (!flag1)
                {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                }
            }
            else if (enumfacing == EnumFacing.NORTH)
            {
                if (!flag1)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
        else if (enumfacing == EnumFacing.EAST)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
        else if (enumfacing == EnumFacing.SOUTH)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }
        else if (enumfacing == EnumFacing.WEST)
        {
            this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else if (enumfacing == EnumFacing.NORTH)
        {
            this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return this.getItem();
    }
    
    private Item getItem()
    {
        return itemDoor;
    }
}
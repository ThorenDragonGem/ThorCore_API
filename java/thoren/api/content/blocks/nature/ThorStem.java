package thoren.api.content.blocks.nature;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

import com.google.common.base.Predicate;

public class ThorStem extends BlockStem
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    private final Block crop;
    private static Item seed;
    private static Item drop;
	private Block ground;
    private static String modId = "";
    private static boolean isPlant = false;
    private static boolean canUseBoneMeal = true;
    public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate()
    {
        public boolean apply(EnumFacing facing)
        {
            return facing != EnumFacing.DOWN;
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.apply((EnumFacing)p_apply_1_);
        }
    });
	
	/**
	 * Creates a ThorStem (like melon).
	 * @param seed the seed which will make this plant.
	 * @param crop the item dropped by the grown plant.
	 * @param drop the quantity of  crop dropped by the grown plant.
	 * @param canUseBoneMeal if the growth can be accelerated by bone meal by use on it.
	 */
	public ThorStem(Block crop, Item seed, Item drop, boolean canUseBoneMeal)
	{
		super(crop);
		this.crop = crop;
		this.seed = seed;
		this.drop = drop;
		this.canUseBoneMeal = canUseBoneMeal;
		float f = 0.125F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
	}
	
	/**
	 * Sets the name of the block.
	 * @param unlocalizedName the name of the block.
	 * @return ThorStem
	 */
	public ThorStem setBlockName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerBlock(this, unlocalizedName);
		ContentRegistry.registerBlock(this, unlocalizedName, modId, isPlant ? ContentTypes.Block.FLOWER : ContentTypes.Block.CROP);
		return this;
	}
	
	/**
	 * Sets the modId of the block.
	 * @param modId the modId of the block.
	 * @return ThorStem
	 */
	public ThorStem modId(String modId)
	{
		this.modId = modId;
		return this;
	}
	
	/**
	 * Sets the crop as a plant block.
	 * @return ThorStem
	 */
	public ThorStem isPlant()
	{
		this.isPlant = true;
		return this;
	}
	
	/**
	 * Sets the hardness of the block.
	 * @param hardness the hardness amount of the block (default: 0.0F).
	 * @return ThorStem
	 */
	public ThorStem setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block  (default: (float)null).
	 * @return ThorStem
	 */
	public ThorStem setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs (default: (CreativeTabs)null).
	 * @return ThorStem
	 */
	public ThorStem setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets what sound is played when you place, or break the block.
	 * @param sound the sound played (default: soundTypeGrass).
	 * @return ThorStem
	 */
	public ThorStem setBlockStepSound(Block.SoundType sound)
	{
		this.setStepSound(sound);
		return this;
	}
	
	/**
	 * Sets the light level emitted by the block.
	 * @param lightlevel the light level emitted (default: (float)null)
	 * @return ThorStem
	 */
	public ThorStem setBlockLightLevel(float lightlevel)
	{
		this.setLightLevel(lightlevel);
		return this;
	}

	/**
	 * Sets the block where the stem can be planted.
	 * @param ground the block where the stem can be planted.
	 */
    protected boolean canPlaceBlockOn(Block ground)
    {
        return ground == this.ground;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            float f = ThorCrop.getGrowthChance(this, worldIn, pos);

            if (rand.nextInt((int)(25.0F / f) + 1) == 0)
            {
                int i = ((Integer)state.getValue(AGE)).intValue();

                if (i < 7)
                {
                    state = state.withProperty(AGE, Integer.valueOf(i + 1));
                    worldIn.setBlockState(pos, state, 2);
                }
                else
                {
                    Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                    while (iterator.hasNext())
                    {
                        EnumFacing enumfacing = (EnumFacing)iterator.next();

                        if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop)
                        {
                            return;
                        }
                    }

                    pos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(rand));
                    Block block = worldIn.getBlockState(pos.down()).getBlock();

                    if (worldIn.isAirBlock(pos) && (block.canSustainPlant(worldIn, pos.down(), EnumFacing.UP, this) || block == Blocks.dirt || block == Blocks.grass))
                    {
                        worldIn.setBlockState(pos, this.crop.getDefaultState());
                    }
                }
            }
        }
    }

    public void growStem(World worldIn, BlockPos pos, IBlockState state)
    {
        int i = ((Integer)state.getValue(AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
        worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(Math.min(7, i))), 2);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state)
    {
        if (state.getBlock() != this)
        {
            return super.getRenderColor(state);
        }
        else
        {
            int i = ((Integer)state.getValue(AGE)).intValue();
            int j = i * 32;
            int k = 255 - i * 8;
            int l = i * 4;
            return j << 16 | k << 8 | l;
        }
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
    {
        return this.getRenderColor(worldIn.getBlockState(pos));
    }

    protected Item getSeedItem()
    {
        return seed;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     * @param fortune the level of the Fortune enchantment on the player's tool
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return drop;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        Item item = this.getSeedItem();
        return item != null ? item : null;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        this.growStem(worldIn, pos, state);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(AGE)).intValue();
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {AGE, FACING});
    }
}
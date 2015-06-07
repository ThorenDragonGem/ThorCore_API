package thoren.api.content.blocks.nature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thoren.api.content.items.ThorItem;
import thoren.api.helpers.game.ContentRegistry;
import thoren.api.helpers.game.ContentTypes;

public class ThorCrop extends BlockCrops
{
	private static String modId = "";
	private static ThorItem seed;
	private static ThorItem crop;
	private static int drop;
	private static boolean isPlant = false;
	
	/**
	 * Creates a ThorCrop.
	 * @param seed the seed which will made this plant.
	 * @param crop the item dropped by the grown plant.
	 * @param drop the quantity of  crop dropped by the grown plant.
	 */
	public ThorCrop(ThorItem seed, ThorItem crop, int drop)
	{
		super();
		this.drop = drop;
		this.seed = seed;
		this.crop = crop;
	}
	
	/**
	 * Sets the name of the block.
	 * @param unlocalizedName the name of the block.
	 * @return ThorCrop
	 */
	public ThorCrop setBlockName(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName);
		GameRegistry.registerBlock(this, unlocalizedName);
		ContentRegistry.registerBlock(this, unlocalizedName, modId, isPlant ? ContentTypes.Block.FLOWER : ContentTypes.Block.CROP);
		return this;
	}
	
	/**
	 * Sets the modId of the block.
	 * @param modId the modId of the block.
	 * @return ThorCrop
	 */
	public ThorCrop modId(String modId)
	{
		this.modId = modId;
		return this;
	}
	
	/**
	 * Sets the crop as a plant block.
	 * @return ThorCrop
	 */
	public ThorCrop isPlant()
	{
		this.isPlant = true;
		return this;
	}
	
	/**
	 * Sets the hardness of the block.
	 * @param hardness the hardness amount of the block (default: 0.0F).
	 * @return ThorCrop
	 */
	public ThorCrop setBlockHardness(float hardness)
	{
		this.setHardness(hardness);
		return this;
	}
	
	/**
	 * Sets the resistance of the block.
	 * @param resistance the blast resistance of the block  (default: (float)null).
	 * @return ThorCrop
	 */
	public ThorCrop setBlockResistance(float resistance)
	{
		this.setResistance(resistance);
		return this;
	}
	
	/**
	 * Sets the CreativeTab where the block will be displayed.
	 * @param tab the CreativeTabs (default: (CreativeTabs)null).
	 * @return ThorCrop
	 */
	public ThorCrop setTab(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
		return this;
	}
	
	/**
	 * Sets what sound is played when you place, or break the block.
	 * @param sound the sound played (default: soundTypeGrass).
	 * @return ThorCrop
	 */
	public ThorCrop setBlockStepSound(Block.SoundType sound)
	{
		this.setStepSound(sound);
		return this;
	}
	
	/**
	 * Sets the light level emitted by the block.
	 * @param lightlevel the light level emitted (default: (float)null)
	 * @return ThorCrop
	 */
	public ThorCrop setBlockLightLevel(float lightlevel)
	{
		this.setLightLevel(lightlevel);
		return this;
	}
	
	/**
	 * @return the quantity dropped when the block is broken.
	 */
	public int quantityDropped(Random random)
	{
		return drop;
	}
	
	/**
	 * @return the seed of the block.
	 */
	protected ThorItem getSeed()
	{
		return seed;
	}
	
	/**
	 * @return the item dropped by the block.
	 */
	protected ThorItem getCrop()
	{
		return crop;
	}
	
    public static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos)
    {
        float f = 1.0F;
        BlockPos blockpos1 = pos.down();

        for (int i = -1; i <= 1; ++i)
        {
            for (int j = -1; j <= 1; ++j)
            {
                float f1 = 0.0F;
                IBlockState iblockstate = worldIn.getBlockState(blockpos1.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(worldIn, blockpos1.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable)blockIn))
                {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(worldIn, blockpos1.add(i, 0, j)))
                    {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos2 = pos.north();
        BlockPos blockpos3 = pos.south();
        BlockPos blockpos4 = pos.west();
        BlockPos blockpos5 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos4).getBlock() || blockIn == worldIn.getBlockState(blockpos5).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos2).getBlock() || blockIn == worldIn.getBlockState(blockpos3).getBlock();

        if (flag && flag1)
        {
            f /= 2.0F;
        }
        else
        {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos5.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos5.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock();

            if (flag2)
            {
                f /= 2.0F;
            }
        }

        return f;
    }
}
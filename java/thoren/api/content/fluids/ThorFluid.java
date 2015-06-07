package thoren.api.content.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * @author ThorenDragonGem
 */
@Deprecated
public class ThorFluid extends BlockFluidClassic
{

	public ThorFluid(Fluid fluid, Material material)
	{
		super(fluid, material);
	}
	
}

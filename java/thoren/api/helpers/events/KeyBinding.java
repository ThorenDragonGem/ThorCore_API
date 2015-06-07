package thoren.api.helpers.events;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBinding
{
	public static net.minecraft.client.settings.KeyBinding Storm;
	
	public static void initialize()
	{
		Storm = new net.minecraft.client.settings.KeyBinding("throws lighting bolts in a diameter of 5 blocks around the player", org.lwjgl.input.Keyboard.KEY_R, "key.categories.gameplay");
		registry();
	}
	
	public static void registry()
	{
		ClientRegistry.registerKeyBinding(Storm);
	}

}

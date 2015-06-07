package thoren.api.helpers.events;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * @author ThorenDragonGem
 */
public class SoundHelper
{
	private static World world;

	/**
	 * Plays a sound at entities.
	 * @param entity the entity where the sound is played.
	 * @param modId the modId (to find the sound registering file and the sound file).
	 * @param name the name of the sound (to find the sound registering file and the sound file).
	 * @param volume the volume of the song.
	 * @param frequency how much time the sound is played in a time.
	 */
	public static void playingSoundAt(Entity entity, String modId, String name, float volume, float frequency)
	{
		world.playSoundAtEntity(entity, modId + ":" + name, volume, frequency);
	}
	
}
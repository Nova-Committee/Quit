package committee.nova.quit.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInit {
    public static final KeyMapping fastQuit = new KeyMapping("key.quit.fastQuit", KeyConflictContext.GUI, InputConstants.Type.KEYSYM, InputConstants.KEY_Q, "key.categories.quit");
    public static final KeyMapping bossKey = new KeyMapping("key.quit.bossKey", KeyConflictContext.GUI, InputConstants.Type.KEYSYM, InputConstants.KEY_B, "key.categories.quit");

    @SubscribeEvent
    public static void init(final RegisterKeyMappingsEvent event) {
        event.register(fastQuit);
        event.register(bossKey);
    }
}

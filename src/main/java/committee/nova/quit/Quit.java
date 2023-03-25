package committee.nova.quit;

import committee.nova.quit.key.KeyInit;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "quit", useMetadata = true, clientSideOnly = true)
public class Quit {
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        KeyInit.init();
    }
}

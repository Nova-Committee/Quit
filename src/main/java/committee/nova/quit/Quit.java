package committee.nova.quit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Quit.MODID)
public class Quit {
    public static final String MODID = "quit";

    public Quit() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}

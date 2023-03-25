package committee.nova.quit.screen;

import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;

public class GuiQuit extends GuiYesNo {
    public GuiQuit(GuiYesNoCallback guiYesNoCallback, String title) {
        super(guiYesNoCallback, title, "", 13468);
    }
}

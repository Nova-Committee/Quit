package committee.nova.quit.client.gui;

import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;

public class GuiQuit extends GuiYesNo {
    public GuiQuit(GuiYesNoCallback callback, String title) {
        super(callback, title, "", 13468);
    }
}

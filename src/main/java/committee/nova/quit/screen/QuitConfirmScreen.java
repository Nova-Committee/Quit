package committee.nova.quit.screen;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.text.Text;

public class QuitConfirmScreen extends ConfirmScreen {
    public QuitConfirmScreen(BooleanConsumer consumer, Text title) {
        super(consumer, title, Text.empty());
    }
}

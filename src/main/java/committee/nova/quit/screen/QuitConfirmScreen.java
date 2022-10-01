package committee.nova.quit.screen;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.network.chat.Component;

public class QuitConfirmScreen extends ConfirmScreen {
    public QuitConfirmScreen(BooleanConsumer consumer, Component title) {
        super(consumer, title, Component.empty());
    }
}

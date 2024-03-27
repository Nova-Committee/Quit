package committee.nova.quit;

import committee.nova.quit.client.key.KeyInit;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.OptionalLong;
import java.util.function.Supplier;

@Mod(modid = Quit.MODID, useMetadata = true, clientSideOnly = true)
public class Quit {
    public static final String MODID = "quit";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static Runnable preventWindowFromClosing = () -> {
    };

    public static Supplier<OptionalLong> getOptionalWindow = OptionalLong::empty;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        KeyInit.init();
        boolean shouldContinue = false;
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(long.class);
            MethodHandle getWindow = lookup.findStatic(Display.class, "getWindow", methodType);

            getOptionalWindow = () -> {
                try {
                    long window = (long) getWindow.invokeExact();
                    return OptionalLong.of(window);
                } catch (Throwable e) {
                    LOGGER.error("Failed to invoke Display#getWindow", e);
                }
                return OptionalLong.empty();
            };
            LOGGER.info("Display#getWindow found.");
            shouldContinue = true;
        } catch (NoSuchMethodException | IllegalAccessException e) {
            LOGGER.info("Display#getWindow not found.");
        }
        if (shouldContinue) try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(void.class, long.class, boolean.class);
            MethodHandle glfwSetWindowShouldClose = lookup.findStatic(Class.forName("org.lwjgl3.glfw.GLFW"), "glfwSetWindowShouldClose", methodType);

            preventWindowFromClosing = () -> {
                getOptionalWindow.get().ifPresent(l -> {
                    try {
                        glfwSetWindowShouldClose.invokeExact(l, false);
                    } catch (Throwable e) {
                        LOGGER.error("Failed to invoke GLFW#glfwSetWindowShouldClose", e);
                    }
                });
            };
            LOGGER.info("LWJGL3 (CleanRoom) found.");
            shouldContinue = false;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException e) {
            LOGGER.info("LWJGL3 (CleanRoom) not found.");
        }
        if (shouldContinue) try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(void.class, long.class, boolean.class);
            MethodHandle glfwSetWindowShouldClose = lookup.findStatic(Class.forName("org.lwjgl.glfw.GLFW"), "glfwSetWindowShouldClose", methodType);

            preventWindowFromClosing = () -> {
                getOptionalWindow.get().ifPresent(l -> {
                    try {
                        glfwSetWindowShouldClose.invokeExact(l, false);
                    } catch (Throwable e) {
                        LOGGER.error("Failed to invoke GLFW#glfwSetWindowShouldClose", e);
                    }
                });
            };
            LOGGER.info("LWJGL3 (Official) found.");
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException e) {
            LOGGER.info("LWJGL3 (Official) not found.");
        }
    }
}

package com.darvinmiller.adminwheel;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class AdminWheelClient implements ClientModInitializer {
    private static KeyBinding wheelKey;
    private static KeyBinding settingsKey;
    private static boolean wasPressed = false;

    @Override
    public void onInitializeClient() {
        wheelKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.adminwheel.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.adminwheel"
        ));

        settingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.adminwheel.settings",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.adminwheel"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (settingsKey.wasPressed() && client.player != null) {
                client.player.sendMessage(Text.literal(
                        "§dAdminWheel §7| Настройки будут добавлены следующим шагом."
                ), false);
            }

            if (client.player == null) {
                wasPressed = false;
                return;
            }

            boolean pressed = wheelKey.isPressed();

            if (pressed && !wasPressed) {
                client.player.sendMessage(Text.literal(
                        "§dAdminWheel §7| Наведи прицел на игрока и выбери действие."
                ), false);
            }

            wasPressed = pressed;
        });
    }
}

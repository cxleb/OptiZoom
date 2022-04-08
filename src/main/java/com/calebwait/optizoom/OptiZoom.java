package com.calebwait.optizoom;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class OptiZoom implements ModInitializer {
    
    private double mSavedFOV;
    private boolean mPressing = false;
    private final static double sZoomFOV = 30;

    @Override
    public void onInitialize() {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Zoom In",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_CONTROL,
            "OptiZoom"
        ));

        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.isPressed()) {
                if(!mPressing) {
                    mSavedFOV = client.options.fov;
                    mPressing = true;
                }
                client.options.fov = sZoomFOV;
                client.options.smoothCameraEnabled = true;
            } else if(mPressing) {
                client.options.fov = mSavedFOV;
                mPressing = false;
                client.options.smoothCameraEnabled = false;
            }
        });
	}
}

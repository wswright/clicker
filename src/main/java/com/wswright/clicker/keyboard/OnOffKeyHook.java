package com.wswright.clicker.keyboard;

import com.wswright.clicker.config.Config;
import org.jnativehook.keyboard.NativeKeyEvent;

public class OnOffKeyHook implements IKeyHook{
    private static final int TOGGLE_KEYCODE = Config.KEY_KB_ACCENT_TILDE;
    private boolean toggle;

    public boolean isOn() {
        return toggle;
    }

    public boolean isOff() {
        return !toggle;
    }

    @Override
    public int getKeyCode() {
        return TOGGLE_KEYCODE;
    }

    @Override
    public void pressed(NativeKeyEvent e) {

    }

    @Override
    public void holding(NativeKeyEvent e) {

    }

    @Override
    public void released(NativeKeyEvent e) {
        toggle = !toggle;
        System.out.println(String.format("Current State is: %s", toggle ? "ON" : "OFF"));
    }
}

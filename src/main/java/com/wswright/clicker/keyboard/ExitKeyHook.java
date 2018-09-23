package com.wswright.clicker.keyboard;

import org.jnativehook.keyboard.NativeKeyEvent;

public class ExitKeyHook implements IKeyHook{
    private static final int ESCAPE_KEYCODE = 1;
    private boolean shouldEscape = false;

    @Override
    public int getKeyCode() {
        return ESCAPE_KEYCODE;
    }

    public boolean isEscaping() {
        return shouldEscape;
    }

    @Override
    public void pressed(NativeKeyEvent e) {
        System.out.println("ESCAPE");
        shouldEscape = true;
    }

    @Override
    public void holding(NativeKeyEvent e) {
        System.out.println("ESCAPE");
        shouldEscape = true;
    }

    @Override
    public void released(NativeKeyEvent e) {
        System.out.println("ESCAPE");
        shouldEscape = true;
    }
}

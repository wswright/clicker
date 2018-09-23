package com.wswright.clicker.keyboard;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.*;

public interface IKeyHook {
    int getKeyCode();
    void pressed(NativeKeyEvent e);
    void holding(NativeKeyEvent e);
    void released(NativeKeyEvent e);
}

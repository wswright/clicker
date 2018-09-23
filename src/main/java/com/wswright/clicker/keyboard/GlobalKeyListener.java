package com.wswright.clicker.keyboard;
import com.wswright.clicker.loop.ILoop;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GlobalKeyListener implements NativeKeyListener {
    private static final boolean IS_KEY_DEBUGGING_ENABLED = false;
    Logger logger = Logger.getGlobal();
    private ILoop loop;
    private List<IKeyHook> keyHookList = new ArrayList<>();
    private Map<Integer, Boolean> pressing = new HashMap<>();


    public ILoop getLoop() {
        return loop;
    }

    public void setLoop(ILoop loop) {
        this.loop = loop;
    }

    public void addKeyHook(IKeyHook keyHook) {
        keyHookList.add(keyHook);
        pressing.putIfAbsent(keyHook.getKeyCode(), false);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        if(IS_KEY_DEBUGGING_ENABLED) System.out.println(formatKeyEvent(e));
        keyEvent(e, true);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if(IS_KEY_DEBUGGING_ENABLED) System.out.println(formatKeyEvent(e));
        keyEvent(e, true);
    }


    private void keyEvent(NativeKeyEvent e, boolean newState) {
        for(IKeyHook hook : keyHookList)
            if(hook.getKeyCode() == e.getKeyCode()) {
                switch (handleEvent(hook.getKeyCode(), newState)) {
                    case CONFUSED:
                        throw new RuntimeException("Something went bad wrong");
                    case HOLDING:
                        hook.holding(e);
                        break;
                    case PRESSED:
                        hook.pressed(e);
                        break;
                    case RELEASED:
                        hook.released(e);
                        break;
                    default:
                        throw new RuntimeException("KeyState was funky when handling the event");
                }
            }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if(IS_KEY_DEBUGGING_ENABLED) System.out.println(formatKeyEvent(e));
        keyEvent(e, false);
    }

    private MapState handleEvent(int keyCode, boolean newState) {
        MapState state = MapState.CONFUSED;
        if(pressing.containsKey(keyCode)) {
            boolean current = pressing.get(keyCode);
            if(current) {//Key is True
                if(newState) {
                    return MapState.HOLDING;
                } else {
                    pressing.replace(keyCode, newState);
                    return MapState.RELEASED;
                }
            } else {//Key is False
                pressing.replace(keyCode, true);
                return MapState.PRESSED;
            }
        } else {//Key DNE
            pressing.put(keyCode, newState);
            return newState ? MapState.PRESSED : MapState.RELEASED;
        }
    }

    private enum MapState {PRESSED, RELEASED, HOLDING, CONFUSED}

    private static String formatKeyEvent(NativeKeyEvent e) {
        return String.format("Native Key Released:  KeyChar: %c, KeyCode: %s, KeyLoc: %s, isActionKey: %b", e.getKeyChar(), e.getKeyCode(), e.getKeyLocation(), e.isActionKey());
    }
}

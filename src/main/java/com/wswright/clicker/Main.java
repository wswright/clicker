package com.wswright.clicker;

import com.wswright.clicker.keyboard.*;
import com.wswright.clicker.mouse.MouseGetter;
import com.wswright.clicker.mouse.MouseMover;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public final static int RETURN_ERR_HOOK_EXCEPTION = 2;
    private static MouseMover mouseMover = new MouseMover();
    private static MouseGetter mouseGetter = new MouseGetter();

    public static void main(String[] args) {
        System.out.print("Registering Native Hook...");
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println(String.format("There was a problem registering a native hook. Message: %s", e));
            System.exit(RETURN_ERR_HOOK_EXCEPTION);
        }
        System.out.print(" Complete! Adding Native Key Listener...");
        GlobalKeyListener listener = new GlobalKeyListener();

        //Create Hooks
        BoxBuilderKeyHook boxHook = new BoxBuilderKeyHook();
        ExitKeyHook exitHook = new ExitKeyHook();
        OnOffKeyHook onOffHook = new OnOffKeyHook();

        //Add Hooks
        listener.addKeyHook(boxHook);
        listener.addKeyHook(exitHook);
        listener.addKeyHook(onOffHook);

        GlobalScreen.addNativeKeyListener(listener);
        System.out.println(" Complete! Listening...");

        setGlobalScreenLoggerLevel(Level.WARNING);
        boolean running = true;
        while(running) {
            boolean mouseMoved = false;
            //Only do this if the Rectangle has been set
            if(boxHook.isReady()) {
                //Only do this if we are toggled ON
                if(onOffHook.isOn()) {
                    Point p = boxHook.getRect().getRandomPointInArea();
                    mouseMover.moveTo(p);
                    mouseMoved = true;
                    System.out.println(String.format("MOVING to %s in box: %s", fmtPoint(p), boxHook.getRect().toString()));
                } else {

                }
            }
            try {
                if(mouseMoved)
                    Thread.sleep(10);//We still need to sleep even if the mouse moved,
                else                        //Otherwise Explorer.exe crashes on Windows
                    Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            running = !exitHook.isEscaping();
        }
        System.out.println("Exiting!");
    }

    private static String fmtPoint(Point p) {
        return String.format("[%d, %d]", p.x, p.y);
    }

    /**
     * This method turns the GlobalScreen logger's level a bit higher than the default.
     */
    private static void setGlobalScreenLoggerLevel(Level level) {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(level);

        logger.setUseParentHandlers(false); //As instructed here: https://github.com/kwhat/jnativehook/wiki/ConsoleOutput
    }

}

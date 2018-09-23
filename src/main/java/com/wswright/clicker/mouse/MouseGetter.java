package com.wswright.clicker.mouse;

import java.awt.*;

public class MouseGetter {
    public PointerInfo getMouse() {
        PointerInfo a = MouseInfo.getPointerInfo();
        return a;
    }

    public Point getMousePoint() {
        return MouseInfo.getPointerInfo().getLocation();
    }
}

package com.wswright.clicker.mouse;

import javafx.util.Pair;

import java.awt.*;

public class MouseMover {
    public void moveTo(Point pointDestination) {
        MouseGetter getter = new MouseGetter();
        Point pointCurrent = getter.getMousePoint();
        try {
            Robot r = new Robot();
            Pair<Integer,Integer> pair = getMoveDistance(pointCurrent, pointDestination);
            r.mouseMove(pointDestination.x, pointDestination.y);
//            System.out.println(String.format("Mouse Moved from %s to %s", fmtPoint(pointCurrent), fmtPoint(pointDestination)));
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    private String fmtPoint(Point p) {
        return String.format("[%d, %d]", p.x, p.y);
    }

    public Pair<Integer, Integer> getMoveDistance(Point start, Point stop) {
        int x = stop.x - start.x;
        int y = stop.y - start.y;
        return new Pair<>(x, y);
    }
}

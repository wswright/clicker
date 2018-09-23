package com.wswright.clicker.loop;

import java.util.List;

public interface ILoop {
    List<Integer> getHooks();
    void keyTyped(Integer i);
    void keyPressed(Integer i);
    void keyReleased(Integer i);
}

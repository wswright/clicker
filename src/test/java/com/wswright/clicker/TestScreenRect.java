package com.wswright.clicker;

import com.wswright.clicker.screen.ScreenRect;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TestScreenRect {
    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    public void testGetRandomInRect() {
        final int NUM_TEST_RUNS = 10000;
        ScreenRect rect = new ScreenRect(0,0,100,200);
        for(int i=0; i<NUM_TEST_RUNS; i++) {
            Point p = rect.getRandomPointInArea();
            assertThat(p.x).isBetween(0, 100);
            assertThat(p.y).isBetween(0, 200);
        }
    }

    @Test
    public void testGetRandomInRect_InitialNegative() {
        final int NUM_TEST_RUNS = 10000;
        ScreenRect rect = new ScreenRect(-100,-300,100,200);
        for(int i=0; i<NUM_TEST_RUNS; i++) {
            Point p = rect.getRandomPointInArea();
            assertThat(p.x).isBetween(-100, 0);
            assertThat(p.y).isBetween(-300, -100);
        }
    }

    @Test
    public void testBuildScreenRect() {
        ScreenRect rect = new ScreenRect();
        rect.firstPoint(0,0);
        rect.secondPoint(100,200);
        assertThat(rect.getX()).isEqualTo(0);
        assertThat(rect.getY()).isEqualTo(0);
        assertThat(rect.getW()).isEqualTo(100);
        assertThat(rect.getH()).isEqualTo(200);
    }
}

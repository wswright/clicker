package com.wswright.clicker;

import com.wswright.clicker.mouse.MouseGetter;
import com.wswright.clicker.mouse.MouseMover;
import org.jnativehook.GlobalScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class TestGetMoveDistance {
    private Point testPoint1 = new Point(0,0);

    @InjectMocks
    private MouseMover mover;


    @Test
    public void testGetMoveDistance() {
        final int X = 100, Y = 200;
        Point target = new Point(X,Y);

        assertThat(mover.getMoveDistance(testPoint1, target).getKey()).isEqualTo(X);
        assertThat(mover.getMoveDistance(testPoint1, target).getValue()).isEqualTo(Y);
    }

    @Test
    public void testGetMoveDistance_NegativeInitial() {
        final int X1 = -100, Y1 = -200, X2 = 100, Y2 = 200;
        Point p1 = new Point(X1, Y1), p2 = new Point(X2, Y2);
        assertThat(mover.getMoveDistance(p1,p2).getKey()).isEqualTo(200);
        assertThat(mover.getMoveDistance(p1,p2).getValue()).isEqualTo(400);
    }

    /**
     * Note: This test will actually move your mouse.
     */
    @Test
    public void testMouseActuallyMoves() {
        Random r = new Random();
        List<Point> points = new ArrayList<>();
        for(int i=0; i<100; i++) {
            points.add(new Point(r.nextInt(1000), r.nextInt(1000)));
        }

        for(Point pt : points) {
            Point p = (new MouseGetter()).getMousePoint();
            Point destination = new Point(pt.x, pt.y);
            mover.moveTo(destination);
            Point p2 = (new MouseGetter()).getMousePoint();

            assertThat(p2.x).isEqualTo(destination.x);
            assertThat(p2.y).isEqualTo(destination.y);
        }
    }

    @Before
    public void setUp() throws Exception {

    }
}

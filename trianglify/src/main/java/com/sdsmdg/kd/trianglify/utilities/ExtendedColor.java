package com.sdsmdg.kd.trianglify.utilities;

/**
 * <h1>Title</h1>
 * <b>Description : Extends android.graphics.Color to add function that helps colorizer work.</b>
 * <p>
 *
 * @author suyash
 * @since 1/4/17.
 */

public class ExtendedColor extends android.graphics.Color {
    public int a;
    public int r;
    public int g;
    public int b;

    public ExtendedColor(int palleteColor) {
        this(0xFF,
             (palleteColor >> 4*4),
             ((palleteColor >> 4*2) & 0xFF),
             ((palleteColor) & 0xFF));
    }

    public ExtendedColor(int r, int g, int b) {
        this(0xFF, r, g, b);
    }

    private ExtendedColor(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static ExtendedColor avg(ExtendedColor c0, ExtendedColor c1) {
        return new ExtendedColor((c0.a + c1.a) / 2,
                (c0.r + c1.r) / 2,
                (c0.g + c1.g) / 2,
                (c0.b + c1.b) / 2
        );
    }

    public int toInt() {
        return argb(a, r, g, b);
    }
}

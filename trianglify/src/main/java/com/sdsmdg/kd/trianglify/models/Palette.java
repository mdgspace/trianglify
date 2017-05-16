package com.sdsmdg.kd.trianglify.models;

/**
 * <h1>Palette</h1>
 * <b>Description : </b>
 * Set of 9 colors that are used to color a triangulation. Palette contains few predefined color sets
 * as well as method to perform operations on palette.
 *
 * @author kriti
 * @since 18/3/17.
 */

public class Palette {
    public static final int DEFAULT_PALETTE_COUNT = 28;

    public static final int YL_GN = 0;
    public static final int YL = 1;
    public static final int YL_GN_BU = 2;
    public static final int GN_BU = 3;
    public static final int BU_GN = 4;
    public static final int PU_BU_GN = 5;
    public static final int PU_BU = 6;
    public static final int BU_PU = 7;
    public static final int RD_PU = 8;
    public static final int PU_RD = 9;
    public static final int OR_RD = 10;
    public static final int YL_OR_RD = 11;
    public static final int YL_OR_BR = 12;
    public static final int PURPLES = 13;
    public static final int BLUES = 14;
    public static final int GREENS = 15;
    public static final int ORANGES = 16;
    public static final int REDS = 17;
    public static final int GREYS = 18;
    public static final int PU_OR = 19;
    public static final int BR_B_G = 20;
    public static final int PU_RE_GN = 21;
    public static final int PI_YE_G = 22;
    public static final int RD_BU = 23;
    public static final int RD_GY = 24;
    public static final int RD_YL_BU = 25;
    public static final int SPECTRAL = 26;
    public static final int RD_YL_GN = 27;

    private int colors[];

    /**
     * Return palette object corresponding to supplied value of paletteIndex, palette is constructed
     * from a predefined set of colors
     * @param palleteIndex Index of palette to return
     * @return Palette object generated from predefined set of colors
     */
    public static Palette getPalette(int palleteIndex){
        switch (palleteIndex){
            case YL_GN:
                return new Palette(0xFFFFE0, 0xFFFFCC, 0xFFFACD, 0xFFFF00, 0xFFEF00, 0xFFD300, 0xF8DE7E, 0xFFD700, 0xC3B091);
            case YL:
                return new Palette(0xffffe5, 0xf7fcb9, 0xd9f0a3, 0xaddd8e, 0x78c679, 0x41ab5d, 0x238443, 0x006837, 0x004529);
            case YL_GN_BU:
                return new Palette(0xffffd9, 0xedf8b1, 0xc7e9b4, 0x7fcdbb, 0x41b6c4, 0x1d91c0, 0x225ea8, 0x253494, 0x081d58);
            case GN_BU:
                return new Palette(0xf7fcf0, 0xe0f3db, 0xccebc5, 0xa8ddb5, 0x7bccc4, 0x4eb3d3, 0x2b8cbe, 0x0868ac, 0x084081);
            case BU_GN:
                return new Palette(0xf7fcfd, 0xe5f5f9, 0xccece6, 0x99d8c9, 0x66c2a4, 0x41ae76, 0x238b45, 0x006d2c, 0x00441b);
            case PU_BU_GN:
                return new Palette(0xfff7fb, 0xece2f0, 0xd0d1e6, 0xa6bddb, 0x67a9cf, 0x3690c0, 0x02818a, 0x016c59, 0x014636);
            case PU_BU:
                return new Palette(0xfff7fb, 0xece7f2, 0xd0d1e6, 0xa6bddb, 0x74a9cf, 0x3690c0, 0x0570b0, 0x045a8d, 0x023858);
            case BU_PU:
                return new Palette(0xf7fcfd, 0xe0ecf4, 0xbfd3e6, 0x9ebcda, 0x8c96c6, 0x8c6bb1, 0x88419d, 0x810f7c, 0x4d004b);
            case RD_PU:
                return new Palette(0xfff7f3, 0xfde0dd, 0xfcc5c0, 0xfa9fb5, 0xf768a1, 0xdd3497, 0xae017e, 0x7a0177, 0x49006a);
            case PU_RD:
                return new Palette(0xf7f4f9, 0xe7e1ef, 0xd4b9da, 0xc994c7, 0xdf65b0, 0xe7298a, 0xce1256, 0x980043, 0x67001f);
            case OR_RD:
                return new Palette(0xfff7ec, 0xfee8c8, 0xfdd49e, 0xfdbb84, 0xfc8d59, 0xef6548, 0xd7301f, 0xb30000, 0x7f0000);
            case YL_OR_RD:
                return new Palette(0xffffcc, 0xffeda0, 0xfed976, 0xfeb24c, 0xfd8d3c, 0xfc4e2a, 0xe31a1c, 0xbd0026, 0x800026);
            case YL_OR_BR:
                return new Palette(0xffffe5, 0xfff7bc, 0xfee391, 0xfec44f, 0xfe9929, 0xec7014, 0xcc4c02, 0x993404, 0x662506);
            case PURPLES:
                return new Palette(0xfcfbfd, 0xefedf5, 0xdadaeb, 0xbcbddc, 0x9e9ac8, 0x807dba, 0x6a51a3, 0x54278f, 0x3f007d);
            case BLUES:
                return new Palette(0xf7fbff, 0xdeebf7, 0xc6dbef, 0x9ecae1, 0x6baed6, 0x4292c6, 0x2171b5, 0x08519c, 0x08306b);
            case GREENS:
                return new Palette(0xf7fcf5, 0xe5f5e0, 0xc7e9c0, 0xa1d99b, 0x74c476, 0x41ab5d, 0x238b45, 0x006d2c, 0x00441b);
            case ORANGES:
                return new Palette(0xfff5eb, 0xfee6ce, 0xfdd0a2, 0xfdae6b, 0xfd8d3c, 0xf16913, 0xd94801, 0xa63603, 0x7f2704);
            case REDS:
                return new Palette(0xfff5f0, 0xfee0d2, 0xfcbba1, 0xfc9272, 0xfb6a4a, 0xef3b2c, 0xcb181d, 0xa50f15, 0x67000d);
            case GREYS:
                return new Palette(0xffffff, 0xf0f0f0, 0xd9d9d9, 0xbdbdbd, 0x969696, 0x737373, 0x525252, 0x252525, 0x000000);
            case PU_OR:
                return new Palette(0x7f3b08, 0xb35806, 0xe08214, 0xfdb863, 0xfee0b6, 0xf7f7f7, 0xd8daeb, 0xb2abd2, 0x8073ac);
            case BR_B_G:
                return new Palette(0x543005, 0x8c510a, 0xbf812d, 0xdfc27d, 0xf6e8c3, 0xf5f5f5, 0xc7eae5, 0x80cdc1, 0x35978f);
            case PU_RE_GN:
                return new Palette(0x40004b, 0x762a83, 0x9970ab, 0xc2a5cf, 0xe7d4e8, 0xf7f7f7, 0xd9f0d3, 0xa6dba0, 0x5aae61);
            case PI_YE_G:
                return new Palette(0x8e0152, 0xc51b7d, 0xde77ae, 0xf1b6da, 0xfde0ef, 0xf7f7f7, 0xe6f5d0, 0xb8e186, 0x7fbc41);
            case RD_BU:
                return new Palette(0x67001f, 0xb2182b, 0xd6604d, 0xf4a582, 0xfddbc7, 0xf7f7f7, 0xd1e5f0, 0x92c5de, 0x4393c3);
            case RD_GY:
                return new Palette(0x67001f, 0xb2182b, 0xd6604d, 0xf4a582, 0xfddbc7, 0xffffff, 0xe0e0e0, 0xbababa, 0x878787);
            case RD_YL_BU:
                return new Palette(0xa50026, 0xd73027, 0xf46d43, 0xfdae61, 0xfee090, 0xffffbf, 0xe0f3f8, 0xabd9e9, 0x74add1);
            case SPECTRAL:
                return new Palette(0x9e0142, 0xd53e4f, 0xf46d43, 0xfdae61, 0xfee08b, 0xffffbf, 0xe6f598, 0xabdda4, 0x66c2a5);
            case RD_YL_GN:
                return new Palette(0xa50026, 0xd73027, 0xf46d43, 0xfdae61, 0xfee08b, 0xffffbf, 0xd9ef8b, 0xa6d96a, 0x66bd63);
            default:
                throw new IllegalArgumentException("Index should be less Palette.DEFAULT_PALETTE_COUNT");
        }
    }

    /**
     * Returns index of palette object passed from list of palettes predefined in Palette
     * @param palette Object for finding index
     * @return Index from predefined pallete or -1 if not found
     */
    public static int indexOf(Palette palette){
        int pos = -1;
        for (int i = 0; i < Palette.DEFAULT_PALETTE_COUNT; i++){
            if (Palette.getPalette(i) == palette) {
                pos = i;
            }
        }
        return  pos;
    }

    public Palette(int c0, int c1, int c2, int c3, int c4, int c5, int c6, int c7, int c8) {
        colors = new int[9];
        colors[0] = c0;
        colors[1] = c1;
        colors[2] = c2;
        colors[3] = c3;
        colors[4] = c4;
        colors[5] = c5;
        colors[6] = c6;
        colors[7] = c7;
        colors[8] = c8;
    }

    public Palette(int colors[]) {
        if (colors.length != 9) {
            throw new IllegalArgumentException("Colors array length should exactly be 9");
        }
        this.colors = colors;
    }

    /**
     * Returns color corresponding to index passed from the set of color for a palette
     * @param index Index of color in set of color for current palette object
     * @return color as int without alpha channel
     */
    public int getColor(int index){
        switch (index){
            case 0:
                return colors[0];
            case 1:
                return colors[1];
            case 2:
                return colors[2];
            case 3:
                return colors[3];
            case 4:
                return colors[4];
            case 5:
                return colors[5];
            case 6:
                return colors[6];
            case 7:
                return colors[7];
            case 8:
                return colors[8];
            default:
                throw new IllegalArgumentException("Index should be less than 9");
        }
    }
}
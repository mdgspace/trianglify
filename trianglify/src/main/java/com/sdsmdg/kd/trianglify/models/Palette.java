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
    public static final int BR_BL = 20;
    public static final int PU_RD_GN = 21;
    public static final int PI_YL_GN = 22;
    public static final int RD_BU = 23;
    public static final int RD_GY = 24;
    public static final int RD_YL_BU = 25;
    public static final int SPECTRAL = 26;
    public static final int RD_YL_GN = 27;

    private int[] colors;

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        if (colors.length != 9) {
            throw new IllegalArgumentException("Colors array length should exactly be 9");
        }
        this.colors = colors;
    }

    /**
     * Return palette object corresponding to supplied value of paletteIndex, palette is constructed
     * from a predefined set of colors
     * @param paletteIndex Index of palette to return
     * @return Palette object generated from predefined set of colors
     */
    public static Palette getPalette(int paletteIndex) {
        switch (paletteIndex) {
            case YL:
                return new Palette(0xffffffe0, 0xffffffcc, 0xfffffacd, 0xffffff00, 0xffffef00, 0xffffd300, 0xfff8de7e, 0xffffd700, 0xffc3b091);
            case YL_GN:
                return new Palette(0xffffffe5, 0xfff7fcb9, 0xffd9f0a3, 0xffaddd8e, 0xff78c679, 0xff41ab5d, 0xff238443, 0xff006837, 0xff004529);
            case YL_GN_BU:
                return new Palette(0xffffffd9, 0xffedf8b1, 0xffc7e9b4, 0xff7fcdbb, 0xff41b6c4, 0xff1d91c0, 0xff225ea8, 0xff253494, 0xff081d58);
            case GN_BU:
                return new Palette(0xfff7fcf0, 0xffe0f3db, 0xffccebc5, 0xffa8ddb5, 0xff7bccc4, 0xff4eb3d3, 0xff2b8cbe, 0xff0868ac, 0xff084081);
            case BU_GN:
                return new Palette(0xfff7fcfd, 0xffe5f5f9, 0xffccece6, 0xff99d8c9, 0xff66c2a4, 0xff41ae76, 0xff238b45, 0xff006d2c, 0xff00441c);
            case PU_BU_GN:
                return new Palette(0xfffff7fb, 0xffece2f0, 0xffd0d1e6, 0xffa6bddb, 0xff67a9cf, 0xff3690c0, 0xff02818a, 0xff016c59, 0xff014636);
            case PU_BU:
                return new Palette(0xfffff7fb, 0xffece7f2, 0xffd0d1e6, 0xffa6bddb, 0xff74a9cf, 0xff3690c0, 0xff0570b0, 0xff045a8d, 0xff023858);
            case BU_PU:
                return new Palette(0xfff7fcfd, 0xffe0ecf4, 0xffbfd3e6, 0xff9ebcda, 0xff8c96c6, 0xff8c6bb1, 0xff88419d, 0xff810f7c, 0xff4d004b);
            case RD_PU:
                return new Palette(0xfffff7f3, 0xfffde0dd, 0xfffcc5c0, 0xfffa9fb5, 0xfff768a1, 0xffdd3497, 0xffae017e, 0xff7a0177, 0xff49006a);
            case PU_RD:
                return new Palette(0xfff7f4f9, 0xffe7e1ef, 0xffd4b9da, 0xffc994c7, 0xffdf65b0, 0xffe7298a, 0xffce1256, 0xff980043, 0xff67001f);
            case OR_RD:
                return new Palette(0xfffff7ec, 0xfffee8c8, 0xfffdd49e, 0xfffdbb84, 0xfffc8d59, 0xffef6548, 0xffd7301f, 0xffb30000, 0xff7f0000);
            case YL_OR_RD:
                return new Palette(0xffffffcc, 0xffffeda0, 0xfffed976, 0xfffeb24c, 0xfffd8d3c, 0xfffc4e2a, 0xffe31a1c, 0xffbd0026, 0xff800026);
            case YL_OR_BR:
                return new Palette(0xffffffe5, 0xfffff7bc, 0xfffee391, 0xfffec44f, 0xfffe9929, 0xffec7014, 0xffcc4c02, 0xff993404, 0xff662506);
            case PURPLES:
                return new Palette(0xfffcfbfd, 0xffefedf5, 0xffdadaeb, 0xffbcbddc, 0xff9e9ac8, 0xff807dba, 0xff6a51a3, 0xff54278f, 0xff3f007d);
            case BLUES:
                return new Palette(0xfff7fbff, 0xffdeebf7, 0xffc6dbef, 0xff9ecae1, 0xff6baed6, 0xff4292c6, 0xff2171b5, 0xff08519c, 0xff08306b);
            case GREENS:
                return new Palette(0xfff7fcf5, 0xffe5f5e0, 0xffc7e9c0, 0xffa1d99b, 0xff74c476, 0xff41ab5d, 0xff238b45, 0xff006d2c, 0xff00441b);
            case ORANGES:
                return new Palette(0xfffff5eb, 0xfffee6ce, 0xfffdd0a2, 0xfffdae6b, 0xfffd8d3c, 0xfff16913, 0xffd94801, 0xffa63603, 0xff7f2704);
            case REDS:
                return new Palette(0xfffff5f0, 0xfffee0d2, 0xfffcbba1, 0xfffc9272, 0xfffb6a4a, 0xffef3b2c, 0xffcb181d, 0xffa50f15, 0xff67000d);
            case GREYS:
                return new Palette(0xffffffff, 0xfff0f0f0, 0xffd9d9d9, 0xffbdbdbd, 0xff969696, 0xff737373, 0xff525252, 0xff252525, 0xff000000);
            case PU_OR:
                return new Palette(0xff7f3b08, 0xffb35806, 0xffe08214, 0xfffdb863, 0xfffee0b6, 0xfff7f7f7, 0xffd8daeb, 0xffb2abd2, 0xff8073ac);
            case BR_BL:
                return new Palette(0xff543005, 0xff8c510a, 0xffbf812d, 0xffdfc27d, 0xfff6e8c3, 0xfff5f5f5, 0xffc7eae5, 0xff80cdc1, 0xff35978f);
            case PU_RD_GN:
                return new Palette(0xff40004b, 0xff762a83, 0xff9970ab, 0xffc2a5cf, 0xffe7d4e8, 0xfff7f7f7, 0xffd9f0d3, 0xffa6dba0, 0xff5aae61);
            case PI_YL_GN:
                return new Palette(0xff8e0152, 0xffc51b7d, 0xffde77ae, 0xfff1b6da, 0xfffde0ef, 0xfff7f7f7, 0xffe6f5d0, 0xffb8e186, 0xff7fbc41);
            case RD_BU:
                return new Palette(0xff67001f, 0xffb2182b, 0xffd6604d, 0xfff4a582, 0xfffddbc7, 0xfff7f7f7, 0xffd1e5f0, 0xff92c5de, 0xff4393c3);
            case RD_GY:
                return new Palette(0xff67001f, 0xffb2182b, 0xffd6604d, 0xfff4a582, 0xfffddbc7, 0xffffffff, 0xffe0e0e0, 0xffbababa, 0xff878787);
            case RD_YL_BU:
                return new Palette(0xffa50026, 0xffd73027, 0xfff46d43, 0xfffdae61, 0xfffee090, 0xffffffbf, 0xffe0f3f8, 0xffabd9e9, 0xff74add1);
            case SPECTRAL:
                return new Palette(0xff9e0142, 0xffd53e4f, 0xfff46d43, 0xfffdae61, 0xfffee08b, 0xffffffbf, 0xffe6f598, 0xffabdda4, 0xff66c2a5);
            case RD_YL_GN:
                return new Palette(0xffa50026, 0xffd73027, 0xfff46d43, 0xfffdae61, 0xfffee08b, 0xffffffbf, 0xffd9ef8b, 0xffa6d96a, 0xff66bd63);
            default:
                throw new IllegalArgumentException("Index should be less Palette.DEFAULT_PALETTE_COUNT");
        }
    }

    /**
     * Returns index of palette object passed from list of palettes predefined in Palette
     * @param palette Object for finding index
     * @return Index from predefined pallete or -1 if not found
     */
    public static int indexOf(Palette palette) {
        int pos = -1;

        for (int i = 0; i < Palette.DEFAULT_PALETTE_COUNT; i++) {
            if ( Palette.getPalette(i).getColors()[8] == palette.getColors()[8] ) {
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

    public Palette(int[] colors) {
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
    public int getColor(int index) {
        switch (index) {
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
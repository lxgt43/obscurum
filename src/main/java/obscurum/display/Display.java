package obscurum.display;

import java.awt.Color;
import obscurum.creatures.Creature;

/**
 * This stores a number of display constants, such as special glyph codes or
 * colours.
 * @author Alex Ghita
 */
public final class Display {
    /**
     * S = Single
     * D = Double
     * H = Horizontal
     * V = Vertical
     * B = Bottom
     * T = Top
     * L = Left
     * R = Right
     * X = Cross
     */
    public static final char SH_LINE = (char)196;
    public static final char SV_LINE = (char)179;
    public static final char SBL_CORNER = (char)192;
    public static final char SBR_CORNER = (char)217;
    public static final char STL_CORNER = (char)218;
    public static final char STR_CORNER = (char)191;
    public static final char DH_LINE = (char)205;
    public static final char DV_LINE = (char)186;
    public static final char DBL_CORNER = (char)200;
    public static final char DBR_CORNER = (char)188;
    public static final char DTL_CORNER = (char)201;
    public static final char DTR_CORNER = (char)187;
    public static final char SH_SB_INTERSECT = (char)194;
    public static final char SH_ST_INTERSECT = (char)193;
    public static final char SV_SL_INTERSECT = (char)180;
    public static final char SV_SR_INTERSECT = (char)195;
    public static final char SH_DB_INTERSECT = (char)210;
    public static final char SH_DT_INTERSECT = (char)208;
    public static final char SV_DL_INTERSECT = (char)181;
    public static final char SV_DR_INTERSECT = (char)198;
    public static final char SX_INTERSECT = (char)197;
    public static final char SH_DV_INTERSECT = (char)215;
    public static final char DH_SB_INTERSECT = (char)209;
    public static final char DH_ST_INTERSECT = (char)207;
    public static final char DV_SL_INTERSECT = (char)182;
    public static final char DV_SR_INTERSECT = (char)199;
    public static final char DH_DB_INTERSECT = (char)203;
    public static final char DH_DT_INTERSECT = (char)202;
    public static final char DV_DL_INTERSECT = (char)185;
    public static final char DV_DR_INTERSECT = (char)204;
    public static final char DX_INTERSECT = (char)206;
    public static final char DH_SV_INTERSECT = (char)216;

    // other symbols
    public static final char D_ARROW = (char)25;
    public static final char U_ARROW = (char)24;
    public static final char SPACE = (char)32;
    public static final char BOOK = (char)8;

    // specific colours
    public static final DisplayColour BG_WINDOW_FRAME = DisplayColour.fromRgb("BG", 22, 22, 11);
    public static final DisplayColour FG_WINDOW_FRAME = DisplayColour.fromRgb("FG", 82, 6, 4);
    public static final DisplayColour[] QUALITY_COLOURS = {
            DisplayColour.fromRgba("Q1", 255, 255, 255, 255),
            DisplayColour.fromRgba("Q2", 30, 255, 30, 0),
            DisplayColour.fromRgba("Q3", 0, 112, 255, 221),
            DisplayColour.fromRgba("Q4", 163, 53, 238, 0)
    };

    // general colours taken from AsciiPanel
    public static final int NUM_OF_COLOURS = 18;
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(128, 0, 0);
    public static final Color GREEN = new Color(0, 128, 0);
    public static final Color YELLOW = new Color(128, 128, 0);
    public static final Color ORANGE = new Color(255, 128, 0);
    public static final Color BLUE = new Color(0, 0, 128);
    public static final Color MAGENTA = new Color(128, 0, 128);
    public static final Color CYAN = new Color(0, 128, 128);
    public static final Color WHITE = new Color(192, 192, 192);
    public static final Color BROWN = new Color(120, 53, 31);
    public static final Color BRIGHT_BLACK = new Color(128, 128, 128);
    public static final Color BRIGHT_RED = new Color(255, 0, 0);
    public static final Color BRIGHT_GREEN = new Color(0, 255, 0);
    public static final Color BRIGHT_YELLOW = new Color(255, 255, 0);
    public static final Color BRIGHT_BLUE = new Color(0, 0, 255);
    public static final Color BRIGHT_MAGENTA = new Color(255, 0, 255);
    public static final Color BRIGHT_CYAN = new Color(0, 255, 255);
    public static final Color BRIGHT_WHITE = new Color(255, 255, 255);
    public static final DisplayColour[] COLOURS = {
            DisplayColour.BLACK,
            DisplayColour.RED,
            DisplayColour.GREEN,
            DisplayColour.YELLOW,
            DisplayColour.ORANGE,
            DisplayColour.BLUE,
            DisplayColour.MAGENTA,
            DisplayColour.CYAN,
            DisplayColour.WHITE,
            DisplayColour.BROWN,
            DisplayColour.BRIGHT_BLACK,
            DisplayColour.BRIGHT_RED,
            DisplayColour.BRIGHT_GREEN,
            DisplayColour.BRIGHT_YELLOW,
            DisplayColour.BRIGHT_BLUE,
            DisplayColour.BRIGHT_MAGENTA,
            DisplayColour.BRIGHT_CYAN,
            DisplayColour.BRIGHT_WHITE
    };
    public static final String[] COLOUR_NAMES = {"Black", "Red", "Green",
            "Yellow", "Orange", "Blue", "Magenta", "Cyan", "White", "Brown",
            "Bright Black", "Bright Red", "Bright Green", "Bright Yellow",
            "Bright Blue", "Bright Magenta", "Bright Cyan", "Bright White"};

    public static String attributesToString(int[] attributes) {
        if (attributes.length != Creature.NUM_OF_ATTRIBUTES) {
            throw new IllegalArgumentException("Attribute list length " +
                    attributes.length + " must be " + Creature.NUM_OF_ATTRIBUTES + ".");
        }

        String output = "";

        if (attributes[Creature.STRENGTH] > 0) {
            output += "STR:" + attributes[Creature.STRENGTH];
        }
        if (attributes[Creature.AGILITY] > 0) {
            if (output != "") {
                output += ", ";
            }
            output += "AGI:" + attributes[Creature.AGILITY];
        }
        if (attributes[Creature.STAMINA] > 0) {
            if (output != "") {
                output += ", ";
            }
            output += "STA:" + attributes[Creature.STAMINA];
        }
        if (attributes[Creature.SPIRIT] > 0) {
            if (output != "") {
                output += ", ";
            }
            output += "SPI:" + attributes[Creature.SPIRIT];
        }
        if (attributes[Creature.INTELLECT] > 0) {
            if (output != "") {
                output += ", ";
            }
            output += "INT:" + attributes[Creature.INTELLECT];
        }

        return output;
    }
}

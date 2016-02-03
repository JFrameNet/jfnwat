package jp.keio.jfn.wat.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class has statics variables and methods used by the other classes in the project.
 */
public class Utils {
    public static List<String> allColors = new ArrayList<String>(Arrays.asList("#4db6ac",
            "#F7941E",
            "#EA5753",
            "#EF9A9A",
            "#F7D100",
            "darkblue",
            "peru",
            "#1ED626",
            "steelblue",
            "tomato",
            "plum",
            "#43BF9C",
            "darkgoldenrod",
            "firebrick",
            "#F700A2",
            "#25A1A1",
            "#E08D8D",
            "#43BF64",
            "#F7941E",
            "#EA5753",
            "#EF9A9A",
            "#F7D100",
            "darkblue",
            "peru",
            "#1ED626",
            "steelblue",
            "tomato",
            "plum",
            "#43BF9C",
            "darkgoldenrod",
            "firebrick",
            "#F700A2",
            "#25A1A1",
            "#E08D8D",
            "#43BF64"
            ));


    /**
     * Checks if two strings match.
     */
    public static boolean matchSearch (String query, String name) {
        return ((name.equalsIgnoreCase(query))
                ||(name.toLowerCase().contains(query.toLowerCase()))
                ||(query.toLowerCase().contains(name.toLowerCase())));
    }
}

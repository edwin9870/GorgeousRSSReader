package com.example.xyzreader.util;

/**
 * Created by Edwin Ramirez Ventura on 6/23/2017.
 */

public final class TextUtil {

    public static String formatCharacterPerLine(int charactersPerLine, String text){

        String tenCharPerLineString = "";
        while (text.length() > charactersPerLine) {

            String buffer = text.substring(0, charactersPerLine);
            tenCharPerLineString = tenCharPerLineString + buffer + "/n";
            text = text.substring(charactersPerLine);
        }

        tenCharPerLineString = tenCharPerLineString + text;
        return tenCharPerLineString;
    }
}

package org.bananalaba.teamsports.aggregate;

import java.util.regex.Pattern;

public class ValidationAssets {

    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("([\\p{L}\\p{N}\\-\\s_’\\.']{1,100})|(<no-data>)");

    private ValidationAssets() {
    }

    public static boolean isValidName(final String name) {
        var matcher = VALID_NAME_PATTERN.matcher(name);
        return matcher.matches();
    }

}

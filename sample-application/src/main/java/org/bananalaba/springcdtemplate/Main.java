package org.bananalaba.springcdtemplate;

import static com.google.common.base.Preconditions.checkArgument;

public class Main {

    public static void main(String[] arguments) {
        checkArgument(arguments.length == 0, "no arguments expected");
        System.out.println("Hello, world");
    }

}

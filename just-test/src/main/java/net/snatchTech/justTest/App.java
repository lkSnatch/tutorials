package net.snatchTech.justTest;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main( String[] args ) {

        System.out.println(sumEqualTen(4, 6));

    }

    static boolean sumEqualTen(int a, int b) {

        return a + b == 10;

    }
}

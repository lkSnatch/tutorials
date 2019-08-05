/*
 * Developed by Alexey Kukushkin aka Snatch aka Mixail Truverov on 06.03.19 10:20.
 * Last modified 18.01.19 14:21.
 * Copyright (c) 2019. All rights reserved.
 */

package net.snatchTech.justTest.excp;

/**
 * Util class for work with exceptions
 */
public class ExcpUtil {

    /**
     * Get all messages from all exceptions (going through causes).
     *
     * @param e any throwable object
     * @return String full exception description with all causes
     */
    public static String getFullDescription(Throwable e){

        StringBuilder sb = new StringBuilder(e.getClass().getName())
                .append(": ")
                .append(e.getMessage());

        Throwable cause = e.getCause();
        while (cause != null){
            sb.append(" ; ")
                    .append(cause.getClass().getName())
                    .append(": ")
                    .append(cause.getMessage());

            cause = cause.getCause();
        }

        return sb.toString();

    }

}

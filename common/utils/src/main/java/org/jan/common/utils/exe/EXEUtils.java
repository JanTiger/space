package org.jan.common.utils.exe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jan.common.utils.io.IOUtils;

/**
 * Utilities for executing command line.
 *
 * @author jan.wang
 */
public class EXEUtils {

    /**
     * Executes command line.
     *
     * @param args
     * @return results after execution
     * @see EXEUtils#execInDirectory(String, String...)
     */
    public static String exec(String... args){
        return execInDirectory(null, args);
    }

    /**
     * Executes command line in the specified directory.
     *
     * @param binDirectory
     * @param args
     * @return results after execution
     */
    public static String execInDirectory(String binDirectory, String... args){
        return exec(binDirectory, new ProcessBuilder(args));
    }

    /**
     * Executes command line.
     *
     * @param commands
     * @return results after execution
     * @see EXEUtils#execInDirectory(String, List)
     */
    public static String exec(List<String> commands){
        return execInDirectory(null, commands);
    }

    /**
     * Executes command line in the specified directory.
     *
     * @param binDirectory
     * @param commands
     * @return results after execution
     */
    public static String execInDirectory(String binDirectory, List<String> commands){
        return exec(binDirectory, new ProcessBuilder(commands));
    }

    /*
     * Executes command line.
     */
    private static String exec(String binDirectory, ProcessBuilder processBuilder){
        if(null != binDirectory)
            processBuilder.directory(new File(binDirectory));
        processBuilder.redirectErrorStream(true);
        InputStream input = null;
        try {
            Process process = processBuilder.start();
            input = process.getInputStream();
            return IOUtils.toString(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

}

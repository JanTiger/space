package org.jan.common.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.jan.common.utils.io.IOUtils;

/**
 * General File System utilities.
 * <p>
 * This class provides static utility methods for general file system
 * functions not provided via the JDK {@link java.io.File File} class.
 * <p>
 * The current functions provided are:
 * <ul>
 * <li>Get the free space on a drive
 * </ul>
 *
 * @since 1.0
 */
public class FileSystemUtils {

    /** Singleton instance, used mainly for testing. */
    private static final FileSystemUtils INSTANCE = new FileSystemUtils();

    /** Operating system state flag for error. */
    private static final int INIT_PROBLEM = -1;
    /** Operating system state flag for neither Unix nor Windows. */
    private static final int OTHER = 0;
    /** Operating system state flag for Windows. */
    private static final int WINDOWS = 1;
    /** Operating system state flag for Unix. */
    private static final int UNIX = 2;
    /** Operating system state flag for Posix flavour Unix. */
    private static final int POSIX_UNIX = 3;

    /** The operating system flag. */
    private static final int OS;

    /** The path to df */
    private static final String DF;

    static {
        int os = OTHER;
        String dfPath = "df";
        try {
            String osName = System.getProperty("os.name");
            if (osName == null) {
                throw new IOException("os.name not found");
            }
            osName = osName.toLowerCase(Locale.ENGLISH);
            // match
            if (osName.indexOf("windows") != -1) {
                os = WINDOWS;
            } else if (osName.indexOf("linux") != -1 ||
                osName.indexOf("mpe/ix") != -1 ||
                osName.indexOf("freebsd") != -1 ||
                osName.indexOf("irix") != -1 ||
                osName.indexOf("digital unix") != -1 ||
                osName.indexOf("unix") != -1 ||
                osName.indexOf("mac os x") != -1) {
                os = UNIX;
            } else if (osName.indexOf("sun os") != -1 ||
                osName.indexOf("sunos") != -1 ||
                osName.indexOf("solaris") != -1) {
                os = POSIX_UNIX;
                dfPath = "/usr/xpg4/bin/df";
            } else if (osName.indexOf("hp-ux") != -1 ||
                osName.indexOf("aix") != -1) {
                os = POSIX_UNIX;
            } else {
                os = OTHER;
            }

        } catch (Exception ex) {
            os = INIT_PROBLEM;
        }
        OS = os;
        DF = dfPath;
    }

    /**
     * Instances should NOT be constructed in standard programming.
     */
    public FileSystemUtils() {
        super();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the free space on a drive or volume in kilobytes by invoking
     * the command line.
     * <pre>
     * FileSystemUtils.freeSpaceKb("C:");       // Windows
     * FileSystemUtils.freeSpaceKb("/volume");  // *nix
     * </pre>
     * The free space is calculated via the command line.
     * It uses 'dir /-c' on Windows, 'df -kP' on AIX/HP-UX and 'df -k' on other Unix.
     * <p>
     * In order to work, you must be running Windows, or have a implementation of
     * Unix df that supports GNU format when passed -k (or -kP). If you are going
     * to rely on this code, please check that it works on your OS by running
     * some simple tests to compare the command line with the output from this class.
     * If your operating system isn't supported, please raise a JIRA call detailing
     * the exact result from df -k and as much other detail as possible, thanks.
     *
     * @param path  the path to get free space for, not null, not empty on Unix
     * @return the amount of free drive space on the drive or volume in kilobytes
     * @throws IllegalArgumentException if the path is invalid
     * @throws IllegalStateException if an error occurred in initialisation
     * @throws IOException if an error occurs when finding the free space
     * @since 1.0, enhanced OS support in 1.0
     */
    public static long freeSpaceKb(String path) throws IOException {
        return freeSpaceKb(path, -1);
    }
    /**
     * Returns the free space on a drive or volume in kilobytes by invoking
     * the command line.
     * <pre>
     * FileSystemUtils.freeSpaceKb("C:");       // Windows
     * FileSystemUtils.freeSpaceKb("/volume");  // *nix
     * </pre>
     * The free space is calculated via the command line.
     * It uses 'dir /-c' on Windows, 'df -kP' on AIX/HP-UX and 'df -k' on other Unix.
     * <p>
     * In order to work, you must be running Windows, or have a implementation of
     * Unix df that supports GNU format when passed -k (or -kP). If you are going
     * to rely on this code, please check that it works on your OS by running
     * some simple tests to compare the command line with the output from this class.
     * If your operating system isn't supported, please raise a JIRA call detailing
     * the exact result from df -k and as much other detail as possible, thanks.
     *
     * @param path  the path to get free space for, not null, not empty on Unix
     * @param timeout The timout amount in milliseconds or no timeout if the value
     *  is zero or less
     * @return the amount of free drive space on the drive or volume in kilobytes
     * @throws IllegalArgumentException if the path is invalid
     * @throws IllegalStateException if an error occurred in initialisation
     * @throws IOException if an error occurs when finding the free space
     * @since 1.0
     */
    public static long freeSpaceKb(String path, long timeout) throws IOException {
        return INSTANCE.freeSpaceOS(path, OS, true, timeout);
    }

    /**
     * Returns the disk size of the volume which holds the working directory.
     * <p>
     * Identical to:
     * <pre>
     * freeSpaceKb(new File(".").getAbsolutePath())
     * </pre>
     * @return the amount of free drive space on the drive or volume in kilobytes
     * @throws IllegalStateException if an error occurred in initialisation
     * @throws IOException if an error occurs when finding the free space
     * @since 1.0
     */
    public static long freeSpaceKb() throws IOException {
        return freeSpaceKb(-1);
    }

    /**
     * Returns the disk size of the volume which holds the working directory.
     * <p>
     * Identical to:
     * <pre>
     * freeSpaceKb(new File(".").getAbsolutePath())
     * </pre>
     * @param timeout The timout amount in milliseconds or no timeout if the value
     *  is zero or less
     * @return the amount of free drive space on the drive or volume in kilobytes
     * @throws IllegalStateException if an error occurred in initialisation
     * @throws IOException if an error occurs when finding the free space
     * @since 1.0
     */
    public static long freeSpaceKb(long timeout) throws IOException {
        return freeSpaceKb(new File(".").getAbsolutePath(), timeout);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the free space on a drive or volume in a cross-platform manner.
     * Note that some OS's are NOT currently supported, including OS/390.
     * <pre>
     * FileSystemUtils.freeSpace("C:");  // Windows
     * FileSystemUtils.freeSpace("/volume");  // *nix
     * </pre>
     * The free space is calculated via the command line.
     * It uses 'dir /-c' on Windows and 'df' on *nix.
     *
     * @param path  the path to get free space for, not null, not empty on Unix
     * @param os  the operating system code
     * @param kb  whether to normalize to kilobytes
     * @param timeout The timout amount in milliseconds or no timeout if the value
     *  is zero or less
     * @return the amount of free drive space on the drive or volume
     * @throws IllegalArgumentException if the path is invalid
     * @throws IllegalStateException if an error occurred in initialisation
     * @throws IOException if an error occurs when finding the free space
     */
    long freeSpaceOS(String path, int os, boolean kb, long timeout) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be empty");
        }
        switch (os) {
            case WINDOWS:
                return kb ? freeSpaceWindows(path, timeout) / FileUtils.ONE_KB : freeSpaceWindows(path, timeout);
            case UNIX:
                return freeSpaceUnix(path, kb, false, timeout);
            case POSIX_UNIX:
                return freeSpaceUnix(path, kb, true, timeout);
            case OTHER:
                throw new IllegalStateException("Unsupported operating system");
            default:
                throw new IllegalStateException(
                  "Exception caught when determining operating system");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Find free space on the Windows platform using the 'dir' command.
     *
     * @param path  the path to get free space for, including the colon
     * @param timeout The timout amount in milliseconds or no timeout if the value
     *  is zero or less
     * @return the amount of free drive space on the drive
     * @throws IOException if an error occurs
     */
    long freeSpaceWindows(String path, long timeout) throws IOException {
        path = FilenameUtils.normalize(path, false);
        if (path.length() > 0 && path.charAt(0) != '"') {
            path = "\"" + path + "\"";
        }

        // build and run the 'dir' command
        String[] cmdAttribs = new String[] {"cmd.exe", "/C", "dir /a /-c " + path};

        // read in the output of the command to an ArrayList
        List<String> lines = performCommand(cmdAttribs, Integer.MAX_VALUE, timeout);

        // now iterate over the lines we just read and find the LAST
        // non-empty line (the free space bytes should be in the last element
        // of the ArrayList anyway, but this will ensure it works even if it's
        // not, still assuming it is on the last non-blank line)
        for (int i = lines.size() - 1; i >= 0; i--) {
            String line = lines.get(i);
            if (line.length() > 0) {
                return parseDir(line, path);
            }
        }
        // all lines are blank
        throw new IOException(
                "Command line 'dir /-c' did not return any info " +
                "for path '" + path + "'");
    }

    /**
     * Parses the Windows dir response last line
     *
     * @param line  the line to parse
     * @param path  the path that was sent
     * @return the number of bytes
     * @throws IOException if an error occurs
     */
    long parseDir(String line, String path) throws IOException {
        // read from the end of the line to find the last numeric
        // character on the line, then continue until we find the first
        // non-numeric character, and everything between that and the last
        // numeric character inclusive is our free space bytes count
        int bytesStart = 0;
        int bytesEnd = 0;
        int j = line.length() - 1;
        innerLoop1: while (j >= 0) {
            char c = line.charAt(j);
            if (Character.isDigit(c)) {
              // found the last numeric character, this is the end of
              // the free space bytes count
              bytesEnd = j + 1;
              break innerLoop1;
            }
            j--;
        }
        innerLoop2: while (j >= 0) {
            char c = line.charAt(j);
            if (!Character.isDigit(c) && c != ',' && c != '.') {
              // found the next non-numeric character, this is the
              // beginning of the free space bytes count
              bytesStart = j + 1;
              break innerLoop2;
            }
            j--;
        }
        if (j < 0) {
            throw new IOException(
                    "Command line 'dir /-c' did not return valid info " +
                    "for path '" + path + "'");
        }

        // remove commas and dots in the bytes count
        StringBuilder buf = new StringBuilder(line.substring(bytesStart, bytesEnd));
        for (int k = 0; k < buf.length(); k++) {
            if (buf.charAt(k) == ',' || buf.charAt(k) == '.') {
                buf.deleteCharAt(k--);
            }
        }
        return parseBytes(buf.toString(), path);
    }

    //-----------------------------------------------------------------------
    /**
     * Find free space on the *nix platform using the 'df' command.
     *
     * @param path  the path to get free space for
     * @param kb  whether to normalize to kilobytes
     * @param posix  whether to use the posix standard format flag
     * @param timeout The timout amount in milliseconds or no timeout if the value
     *  is zero or less
     * @return the amount of free drive space on the volume
     * @throws IOException if an error occurs
     */
    long freeSpaceUnix(String path, boolean kb, boolean posix, long timeout) throws IOException {
        if (path.length() == 0) {
            throw new IllegalArgumentException("Path must not be empty");
        }

        // build and run the 'dir' command
        String flags = "-";
        if (kb) {
            flags += "k";
        }
        if (posix) {
            flags += "P";
        }
        String[] cmdAttribs =
            flags.length() > 1 ? new String[] {DF, flags, path} : new String[] {DF, path};

        // perform the command, asking for up to 3 lines (header, interesting, overflow)
        List<String> lines = performCommand(cmdAttribs, 3, timeout);
        if (lines.size() < 2) {
            // unknown problem, throw exception
            throw new IOException(
                    "Command line '" + DF + "' did not return info as expected " +
                    "for path '" + path + "'- response was " + lines);
        }
        String line2 = lines.get(1); // the line we're interested in

        // Now, we tokenize the string. The fourth element is what we want.
        StringTokenizer tok = new StringTokenizer(line2, " ");
        if (tok.countTokens() < 4) {
            // could be long Filesystem, thus data on third line
            if (tok.countTokens() == 1 && lines.size() >= 3) {
                String line3 = lines.get(2); // the line may be interested in
                tok = new StringTokenizer(line3, " ");
            } else {
                throw new IOException(
                        "Command line '" + DF + "' did not return data as expected " +
                        "for path '" + path + "'- check path is valid");
            }
        } else {
            tok.nextToken(); // Ignore Filesystem
        }
        tok.nextToken(); // Ignore 1K-blocks
        tok.nextToken(); // Ignore Used
        String freeSpace = tok.nextToken();
        return parseBytes(freeSpace, path);
    }

    //-----------------------------------------------------------------------
    /**
     * Parses the bytes from a string.
     *
     * @param freeSpace  the free space string
     * @param path  the path
     * @return the number of bytes
     * @throws IOException if an error occurs
     */
    long parseBytes(String freeSpace, String path) throws IOException {
        try {
            long bytes = Long.parseLong(freeSpace);
            if (bytes < 0) {
                throw new IOException(
                        "Command line '" + DF + "' did not find free space in response " +
                        "for path '" + path + "'- check path is valid");
            }
            return bytes;

        } catch (NumberFormatException ex) {
            throw new IOExceptionWithCause(
                    "Command line '" + DF + "' did not return numeric data as expected " +
                    "for path '" + path + "'- check path is valid", ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Performs the os command.
     *
     * @param cmdAttribs  the command line parameters
     * @param max The maximum limit for the lines returned
     * @param timeout The timout amount in milliseconds or no timeout if the value
     *  is zero or less
     * @return the parsed data
     * @throws IOException if an error occurs
     */
    List<String> performCommand(String[] cmdAttribs, int max, long timeout) throws IOException {
        // this method does what it can to avoid the 'Too many open files' error
        // based on trial and error and these links:
        // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4784692
        // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4801027
        // http://forum.java.sun.com/thread.jspa?threadID=533029&messageID=2572018
        // however, its still not perfect as the JDK support is so poor
        // (see commond-exec or ant for a better multi-threaded multi-os solution)

        List<String> lines = new ArrayList<String>(20);
        Process proc = null;
        InputStream in = null;
        OutputStream out = null;
        InputStream err = null;
        BufferedReader inr = null;
        try {

            Thread monitor = ThreadMonitor.start(timeout);

            proc = openProcess(cmdAttribs);
            in = proc.getInputStream();
            out = proc.getOutputStream();
            err = proc.getErrorStream();
            inr = new BufferedReader(new InputStreamReader(in));
            String line = inr.readLine();
            while (line != null && lines.size() < max) {
                line = line.toLowerCase(Locale.ENGLISH).trim();
                lines.add(line);
                line = inr.readLine();
            }

            proc.waitFor();

            ThreadMonitor.stop(monitor);

            if (proc.exitValue() != 0) {
                // os command problem, throw exception
                throw new IOException(
                        "Command line returned OS error code '" + proc.exitValue() +
                        "' for command " + Arrays.asList(cmdAttribs));
            }
            if (lines.isEmpty()) {
                // unknown problem, throw exception
                throw new IOException(
                        "Command line did not return any info " +
                        "for command " + Arrays.asList(cmdAttribs));
            }
            return lines;

        } catch (InterruptedException ex) {
            throw new IOExceptionWithCause(
                    "Command line threw an InterruptedException " +
                    "for command " + Arrays.asList(cmdAttribs) + " timeout=" + timeout, ex);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(err);
            IOUtils.closeQuietly(inr);
            if (proc != null) {
                proc.destroy();
            }
        }
    }

    /**
     * Opens the process to the operating system.
     *
     * @param cmdAttribs  the command line parameters
     * @return the process
     * @throws IOException if an error occurs
     */
    Process openProcess(String[] cmdAttribs) throws IOException {
        return Runtime.getRuntime().exec(cmdAttribs);
    }

}
class IOExceptionWithCause extends IOException {

    /**
     * Defines the serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new instance with the given message and cause.
     * <p>
     * As specified in {@link Throwable}, the message in the given <code>cause</code> is not used in this instance's
     * message.
     * </p>
     *
     * @param message
     *            the message (see {@link #getMessage()})
     * @param cause
     *            the cause (see {@link #getCause()}). A {@code null} value is allowed.
     */
    public IOExceptionWithCause(String message, Throwable cause) {
        super(message);
        this.initCause(cause);
    }

    /**
     * Constructs a new instance with the given cause.
     * <p>
     * The message is set to <code>cause==null ? null : cause.toString()</code>, which by default contains the class
     * and message of <code>cause</code>. This constructor is useful for call sites that just wrap another throwable.
     * </p>
     *
     * @param cause
     *            the cause (see {@link #getCause()}). A {@code null} value is allowed.
     */
    public IOExceptionWithCause(Throwable cause) {
        super(cause == null ? null : cause.toString());
        this.initCause(cause);
    }
}
/**
 * Monitors a thread, interrupting it of it reaches the specified timout.
 * <p>
 * This works by sleeping until the specified timout amount and then
 * interrupting the thread being monitored. If the thread being monitored
 * completes its work before being interrupted, it should <code>interrupt()<code>
 * the <i>monitor</i> thread.
 * <p>
 *
 * <pre>
 *       long timeoutInMillis = 1000;
 *       try {
 *           Thread monitor = ThreadMonitor.start(timeoutInMillis);
 *           // do some work here
 *           ThreadMonitor.stop(monitor);
 *       } catch (InterruptedException e) {
 *           // timed amount was reached
 *       }
 * </pre>
 *
 * @version  $Id: ThreadMonitor.java 1307459 2012-03-30 15:11:44Z ggregory $
 */
class ThreadMonitor implements Runnable {

    private final Thread thread;
    private final long timeout;

    /**
     * Start monitoring the current thread.
     *
     * @param timeout The timout amount in milliseconds
     * or no timeout if the value is zero or less
     * @return The monitor thread or {@code null}
     * if the timout amount is not greater than zero
     */
    public static Thread start(long timeout) {
        return start(Thread.currentThread(), timeout);
    }

    /**
     * Start monitoring the specified thread.
     *
     * @param thread The thread The thread to monitor
     * @param timeout The timout amount in milliseconds
     * or no timeout if the value is zero or less
     * @return The monitor thread or {@code null}
     * if the timout amount is not greater than zero
     */
    public static Thread start(Thread thread, long timeout) {
        Thread monitor = null;
        if (timeout > 0) {
            ThreadMonitor timout = new ThreadMonitor(thread, timeout);
            monitor = new Thread(timout, ThreadMonitor.class.getSimpleName());
            monitor.setDaemon(true);
            monitor.start();
        }
        return monitor;
    }

    /**
     * Stop monitoring the specified thread.
     *
     * @param thread The monitor thread, may be {@code null}
     */
    public static void stop(Thread thread) {
        if (thread != null) {
            thread.interrupt();
        }
    }

    /**
     * Construct and new monitor.
     *
     * @param thread The thread to monitor
     * @param timeout The timout amount in milliseconds
     */
    private ThreadMonitor(Thread thread, long timeout) {
        this.thread = thread;
        this.timeout = timeout;
    }

    /**
     * Sleep until the specified timout amount and then
     * interrupt the thread being monitored.
     *
     * @see Runnable#run()
     */
    public void run() {
        try {
            Thread.sleep(timeout);
            thread.interrupt();
        } catch (InterruptedException e) {
            // timeout not reached
        }
    }
}
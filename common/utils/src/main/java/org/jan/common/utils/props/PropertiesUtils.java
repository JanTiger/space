package org.jan.common.utils.props;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.jan.common.utils.file.FileUtils;
import org.jan.common.utils.io.IOUtils;


/**
 * Utilities for parsing *.properties.
 *
 * @author jan.wang
 *
 */
public class PropertiesUtils {

    /**
     * Returns the {@link Properties} from the input stream, and this method can not close the stream.
     *
     * @param input
     * @return {@link Properties}
     */
    public static Properties loadProperties(InputStream input){
        if(null != input){
            Properties props = new Properties();
            try {
                props.load(input);
                return props;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Returns the {@link Properties} from the specified file.
     *
     * @param file
     * @return {@link Properties}
     */
    public static Properties loadProperties(File file){
        return getProperties(file);
    }

    /**
     * Returns the {@link Properties} from the absolute path.
     *
     * @param absolutePath
     * @return {@link Properties}
     */
    public static Properties loadProperties(String absolutePath){
        return getProperties(absolutePath);
    }

    /**
     * Returns the {@link Properties} from the relative path.
     *
     * @param clazz
     * @param relativePath
     * @return {@link Properties}
     */
    public static Properties loadProperties(Class<?> clazz, String relativePath){
        if(null == clazz)
            return null;
        InputStream input = clazz.getResourceAsStream(relativePath);
        Properties props = loadProperties(input);
        IOUtils.closeQuietly(input);
        return props;
    }

    /*
     * Returns the properties from the path or a file.
     */
    private static Properties getProperties(Object obj){
        if(null == obj)
            return null;
        InputStream input = null;
        Properties props = null;
        try {
            input = obj instanceof File ? new FileInputStream((File)obj) : new FileInputStream((String)obj);
            props = loadProperties(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
        }
        return props;
    }

    /**
     * Writes properties to the specified output stream.
     *
     * @param props
     * @param out
     */
    public static void storeProperties(Properties props, OutputStream out){
        try {
            props.store(out, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes properties to the specified file.
     *
     * @param props
     * @param file
     */
    public static void storeProperties(Properties props, File file){
        OutputStream out = null;
        try {
            if(null == file || !file.exists())
                throw new FileNotFoundException(file.getPath());
            out = new FileOutputStream(file);
            props.store(out, null);
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * Writes properties to the specified file which will be created if the parameter named 'isCreate' is true.
     *
     * @param props
     * @param file
     * @param isCreate
     * @see PropertiesUtils#storeProperties(Properties, File)
     */
    public static void storeProperties(Properties props, File file, boolean isCreate){
        if(isCreate && null != file)
            file = FileUtils.instanceFile(file.getPath(), true);
        storeProperties(props, file);
    }

    /**
     * Writes properties to the specified path.
     *
     * @param props
     * @param path
     * @see PropertiesUtils#storeProperties(Properties, File)
     */
    public static void storeProperties(Properties props, String path){
        storeProperties(props, new File(path));
    }

    /**
     * Writes properties to the specified path.
     * If the file is no exist it will be created when the parameter named 'isCreate' is true.
     *
     * @param props
     * @param path
     * @param isCreate
     * @see PropertiesUtils#storeProperties(Properties, File)
     */
    public static void storeProperties(Properties props, String path, boolean isCreate){
        if(isCreate && null != path)
            FileUtils.instanceFile(path, true);
        storeProperties(props, new File(path));
    }

}

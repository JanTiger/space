/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.common.utils.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import org.jan.common.utils.io.IOUtils;

/**
 *
 * Utility for zip (only gzip and deflate supported).
 *
 * @author Jan.Wang
 *
 */
public class ZipUtils {

    /**
     * gzip compression
     * */
    public static byte[] gzip(byte[] data) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        GZIPOutputStream gzip = null;
        try {
            bos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            bytes = bos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(gzip);
        }
        return bytes;
    }

    /**
     * gzip decompression
     * */
    public static byte[] ungzip(byte[] data) {
        byte[] bytes = null;
        ByteArrayInputStream bis = null;
        GZIPInputStream gzip = null;
        ByteArrayOutputStream bos = null;
        try {
            bis = new ByteArrayInputStream(data);
            gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            bos = new ByteArrayOutputStream();
            int b = -1;
            while (-1 != (b = gzip.read(buf, 0, buf.length)))
                bos.write(buf, 0, b);
            bytes = bos.toByteArray();
            bos.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(gzip);
        }
        return bytes;
    }

    /**
     * deflate compression
     * */
    public static byte[] deflate(byte[] data) {
        Deflater compresser = new Deflater();
        compresser.setLevel(Deflater.BEST_COMPRESSION);
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        compresser.deflate(bytes);
        while (!compresser.finished()) {
            bos.write(bytes, 0, compresser.deflate(bytes));
        }
        bytes = bos.toByteArray();
        return bytes;
    }

    /**
     * deflate decompression
     * */
    public static byte[] inflate(byte[] data) {
        Inflater decompresser = new Inflater();
        decompresser.setInput(data);
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while (!decompresser.finished()) {
                bos.write(bytes, 0, decompresser.inflate(bytes));
            }
        } catch (DataFormatException e) {
            e.printStackTrace();
        } finally {
            bytes = bos.toByteArray();
        }
        return bytes;
    }
}

/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.common.utils.file;

/**
 * @author jan.wang
 *
 */
import java.io.IOException;
import java.io.InputStream;

/**
 * file type judge
 */
public final class FileTypeUtils {
    /*
     * Constructor
     */
    private FileTypeUtils() {}

    /*
     * convert file head to hex
     *
     * @param array of bytes
     * @return
     */
    private static String bytesToHexString(byte[] src){
        if (null == src || src.length == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        for (byte b : src) {
            String hv = Integer.toHexString(b & 0xFF);
            if (hv.length() < 2)
                sb.append(0);
            sb.append(hv);
        }
        return sb.toString();
    }
    /*
     * find file head
     *
     * @param inputStream
     * @return head
     * @throws IOException
     */
    private static String getFileContent(InputStream inputStream) {
        byte[] bytes = new byte[28];
        try {
            inputStream.read(bytes, 0, 28);
        } catch (IOException e) {
            throw new RuntimeException("read file content error!");
        }
        return bytesToHexString(bytes);
    }
    /**
     *
     * judge file type
     *
     * @param inputStream
     * @return file type
     */
    public static FileType getType(InputStream inputStream) {
        if(null == inputStream)
            return FileType.UNKOWN;
        String fileHead = getFileContent(inputStream);
        if (null != fileHead && fileHead.length() > 0){
            fileHead = fileHead.toUpperCase();
            FileType[] fileTypes = FileType.values();
            for (FileType type : fileTypes) {
                if (fileHead.startsWith(type.getValue()))
                    return type;
            }
        }
        return FileType.UNKOWN;
    }
    /**
     * judge specified file type
     *
     * @param inputStream
     * @param fileType
     * @return
     */
    public static boolean isType(InputStream inputStream, FileType fileType){
        if(null == inputStream || null == fileType)
            return false;
        String fileHead = getFileContent(inputStream);
        if (null == fileHead || fileHead.length() == 0)
            return false;
        fileHead = fileHead.toUpperCase();
        return fileHead.startsWith(fileType.getValue());
    }

}

enum FileType {
    /**
     * JEPG.
     */
    JPEG("FFD8FF"),
    /**
     * PNG.
     */
    PNG("89504E47"),
    /**
     * GIF.
     */
    GIF("47494638"),
    /**
     * TIFF.
     */
    TIFF("49492A00"),
    /**
     * Windows Bitmap.
     */
    BMP("424D"),
    /**
     * CAD.
     */
    DWG("41433130"),
    /**
     * Adobe Photoshop.
     */
    PSD("38425053"),
    /**
     * Rich Text Format.
     */
    RTF("7B5C727466"),
    /**
     * XML.
     */
    XML("3C3F786D6C"),
    /**
     * HTML.
     */
    HTML("68746D6C3E"),
    /**
     * Email [thorough only].
     */
    EML("44656C69766572792D646174653A"),
    /**
     * Outlook Express.
     */
    DBX("CFAD12FEC5FD746F"),
    /**
     * Outlook (pst).
     */
    PST("2142444E"),
    /**
     * MS Word/Excel 2003.
     */
    XLS_DOC("D0CF11E0"),
    /**
     * MS Word/Excel 2007.
     */
    XLSX_DOC("504B03041"),
    /**
     * MS Access.
     */
    MDB("5374616E64617264204A"),
    /**
     * WordPerfect.
     */
    WPD("FF575043"),
    /**
     * Postscript.
     */
    EPS("252150532D41646F6265"),
    /**
     * Adobe Acrobat.
     */
    PDF("255044462D312E"),
    /**
     * Quicken.
     */
    QDF("AC9EBD8F"),
    /**
     * Windows Password.
     */
    PWL("E3828596"),
    /**
     * ZIP Archive.
     */
    ZIP("504B0304"),
    /**
     * RAR Archive.
     */
    RAR("52617221"),
    /**
     * Wave.
     */
    WAV("57415645"),
    /**
     * AVI.
     */
    AVI("41564920"),
    /**
     * Real Audio.
     */
    RAM("2E7261FD"),
    /**
     * Real Media.
     */
    RM("2E524D46"),
    /**
     * MPEG (mpg).
     */
    MPG("000001BA"),
    /**
     * Quicktime.
     */
    MOV("6D6F6F76"),
    /**
     * Windows Media.
     */
    ASF("3026B2758E66CF11"),
    /**
     * MIDI.
     */
    MID("4D546864"),
    /**
     * UNKOWN
     */
    UNKOWN("");
    private String value = "";
    /**
     * Constructor.
     *
     * @param type
     */
    private FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
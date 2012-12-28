/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.jan.common.utils.xml.JAXBXmlUtils;
import org.jan.webapp.hms.model.xml.InitialData;

/**
 * @author Jan.Wang
 *
 */
public class Test {

    /**
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("E:/Jan/space/app/web/hms/src/main/resources/initialData.xml");
        InitialData data = JAXBXmlUtils.xmlToObj(new FileInputStream(f), InitialData.class);
        System.out.println(data);
    }

}

/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.util;

import java.util.UUID;

/**
 * @author Jan.Wang
 *
 */
public final class IDGenerator {

    private IDGenerator(){}

    public static String create(){
        return UUID.randomUUID().toString();
    }

}

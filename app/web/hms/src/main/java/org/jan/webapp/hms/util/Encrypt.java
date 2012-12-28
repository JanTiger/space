/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.util;

import org.jan.common.utils.security.Algorithm;

/**
 * @author Jan.Wang
 *
 */
public final class Encrypt {

    private Encrypt(){}

    public static String ep(String input){
        return Algorithm.Coder.BASE64.encode(input);
    }

    public static String dp(String input){
        return Algorithm.Coder.BASE64.decoder(input);
    }

}

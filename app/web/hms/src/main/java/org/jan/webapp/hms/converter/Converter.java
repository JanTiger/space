/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.converter;

/**
 * @author Jan.Wang
 * @param <A>
 * @param <B>
 *
 */
public interface Converter<A, B> {

    B convert(A a);

    A inverse(B b);

}

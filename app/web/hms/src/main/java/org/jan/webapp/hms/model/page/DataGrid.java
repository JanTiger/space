/************************************************************************
 Copyright (C) Unpublished Electronic Arts (EA) Software, Inc.
 All rights reserved. EA Software, Inc., Confidential and Proprietary.

 This software is subject to copyright protection
 under the laws of the Canada and other countries.

 Unless otherwise explicitly stated, this software is provided
 by Electronic Arts (EA).

 *************************************************************************/
package org.jan.webapp.hms.model.page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan.Wang
 *
 */
public class DataGrid<T> {

    private List<T> rows = new ArrayList<T>();
    private Long total = 0L;

    public DataGrid(){}

    public DataGrid(List<T> rows, long total){
        if(null != rows)
            this.rows = rows;
        this.total = total;
    }

    /**
     * @return the rows
     */
    public List<T> getRows() {
        return rows;
    }
    /**
     * @param rows the rows to set
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    /**
     * @return the total
     */
    public Long getTotal() {
        return total;
    }
    /**
     * @param total the total to set
     */
    public void setTotal(Long total) {
        this.total = total;
    }

}

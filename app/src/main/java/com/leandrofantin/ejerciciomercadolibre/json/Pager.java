package com.leandrofantin.ejerciciomercadolibre.json;

/**
 * @lfantin (lea)
 */
public class Pager {

    private String total="0";
    private String offset="0";
    private String limit="0";

    public Pager(String total, String offset, String limit) {
        this.total = total;
        this.offset = offset;
        this.limit = limit;
    }

    public String getTotal() {

        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}

package com.chaldea.dto;

public class ConvertRequest {
    private String fromType;
    private Long fromQty;
    private String toType;

    public String getFromType() { return fromType; }
    public void setFromType(String fromType) { this.fromType = fromType; }

    public Long getFromQty() { return fromQty; }
    public void setFromQty(Long fromQty) { this.fromQty = fromQty; }

    public String getToType() { return toType; }
    public void setToType(String toType) { this.toType = toType; }
}

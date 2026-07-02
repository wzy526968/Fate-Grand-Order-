package com.chaldea.dto;

public class ResourceDTO {
    private Long id;
    private String name;
    private String type;
    private Long quantity;
    private String icon;

    public ResourceDTO() {}

    public ResourceDTO(Long id, String name, String type, Long quantity, String icon) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.icon = icon;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}

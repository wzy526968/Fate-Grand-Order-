package com.chaldea.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;       // display name, e.g. "圣晶石"

    @Column(nullable = false)
    private String type;       // enum-style key: SAINT_QUARTZ, MANA_PRISM, QP, RARE_PRISM, SQ_FRAGMENT

    @Column(nullable = false)
    private Long quantity = 0L;

    private String icon;       // emoji or icon character

    // --- Getters & Setters ---

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

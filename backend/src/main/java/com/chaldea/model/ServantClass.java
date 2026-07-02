package com.chaldea.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "servant_classes")
@JsonIgnoreProperties({"servants"})
public class ServantClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name", nullable = false, unique = true, length = 50)
    private String className;

    @Column(length = 50)
    private String icon;

    @OneToMany(mappedBy = "servantClass")
    private List<Servant> servants;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public List<Servant> getServants() { return servants; }
    public void setServants(List<Servant> servants) { this.servants = servants; }
}

package com.chaldea.dto;

public class ServantDTO {
    private Long id;
    private String name;
    private Long classId;
    private String className;
    private String classIcon;
    private String title;
    private Integer rarity;
    private Integer strength;
    private Integer endurance;
    private Integer agility;
    private Integer magic;
    private Integer luck;
    private Integer np;
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getClassIcon() { return classIcon; }
    public void setClassIcon(String classIcon) { this.classIcon = classIcon; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getRarity() { return rarity; }
    public void setRarity(Integer rarity) { this.rarity = rarity; }
    public Integer getStrength() { return strength; }
    public void setStrength(Integer strength) { this.strength = strength; }
    public Integer getEndurance() { return endurance; }
    public void setEndurance(Integer endurance) { this.endurance = endurance; }
    public Integer getAgility() { return agility; }
    public void setAgility(Integer agility) { this.agility = agility; }
    public Integer getMagic() { return magic; }
    public void setMagic(Integer magic) { this.magic = magic; }
    public Integer getLuck() { return luck; }
    public void setLuck(Integer luck) { this.luck = luck; }
    public Integer getNp() { return np; }
    public void setNp(Integer np) { this.np = np; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

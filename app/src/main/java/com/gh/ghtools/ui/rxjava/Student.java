package com.gh.ghtools.ui.rxjava;

/**
 * @author: gh
 * @description:
 * @date: 2019-09-19.
 * @from:
 */
public class Student {

    private String id;
    private String name;
    private String age;
    private String clazz;

    public Student(String id, String name, String age, String clazz) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.clazz = clazz;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age == null ? "" : age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getClazz() {
        return clazz == null ? "" : clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}

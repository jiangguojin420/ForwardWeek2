package com.bw.forwardweek2.model.dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ClassifyTable {
    //用来存分类json
    private String classifyJson;

    @Generated(hash = 955344891)
    public ClassifyTable(String classifyJson) {
        this.classifyJson = classifyJson;
    }

    @Generated(hash = 270909972)
    public ClassifyTable() {
    }

    public String getClassifyJson() {
        return this.classifyJson;
    }

    public void setClassifyJson(String classifyJson) {
        this.classifyJson = classifyJson;
    }
}

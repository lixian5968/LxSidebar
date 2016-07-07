package com.kongnan.sidebar.Demo2;

/**
 * Created by lixian on 2016/7/4.
 */

public class DataModel {
    private String name; // 在ListView中显示的数据
    private char indexName; // 拼音首字母

    public DataModel() {

    }

    public DataModel(String name, char indexName) {
        this.name = name;
        this.indexName = indexName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getIndexName() {
        return indexName;
    }

    public void setIndexName(char indexName) {
        this.indexName = indexName;
    }
}
package org.togethwy.sample.mysql;

import java.util.List;

/**
 * @author wangtonghe
 * @date 2017/10/11 11:55
 */
public class DoubanMovie {

    private String name;

    private String author;

    private String actor;

    private String lang;


    private List<String> category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}

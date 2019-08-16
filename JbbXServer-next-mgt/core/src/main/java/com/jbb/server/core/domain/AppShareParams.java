package com.jbb.server.core.domain;

public class AppShareParams {

    private String title;
    private String description;
    private String image;
    private String link;

    public AppShareParams() {

    }

    public AppShareParams(String title, String description, String image, String link) {
        super();
        this.title = title;
        this.description = description;
        this.image = image;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "AppShareParams [title=" + title + ", description=" + description + ", image=" + image + ", link="
            + link + "]";
    }

}

package com.jbb.mgt.core.domain;

public class RelationBase {

    private int id;
    private int relationLevel;
    private String des;
    private String queue;
    private String relation;

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelationLevel() {
        return relationLevel;
    }

    public void setRelationLevel(int relationLevel) {
        this.relationLevel = relationLevel;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "RelationBase{" + "id=" + id + ", relationLevel=" + relationLevel + ", des='" + des + '\'' + '}';
    }
}

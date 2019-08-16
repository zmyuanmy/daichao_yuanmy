package com.jbb.mgt.server.rs.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Property;

/**
 * Property response对象
 *
 * @author wyq
 * @date 2018/6/6 19:45
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsProperty {
    private String name;
    private String value;

    public RsProperty() {
        super();
    }

    public RsProperty(Property property) {
        this.name = property.getName();
        this.value = property.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

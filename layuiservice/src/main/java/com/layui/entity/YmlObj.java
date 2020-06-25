package com.layui.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "content")
public class YmlObj {

    private List<String> item;

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "YmlObj{" +
                "item=" + item +
                '}';
    }
}

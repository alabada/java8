package com.alabada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author wenzhd
 * @Date 2018/4/2
 * @Description 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Article {
    private String title;
    private String author;
    private List<String> tags;

    public Boolean isJava() {
        return "Java".equals(this.title);
    }

    public Boolean isAngular() {
        return "Angular".equals(this.title);
    }
}

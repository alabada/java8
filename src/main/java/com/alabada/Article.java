package com.alabada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author wenzhd
 * @Date 2018/4/2
 * @Description 
 */
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Article {
    private String title;
    private String author;
    private List<String> tags;
}

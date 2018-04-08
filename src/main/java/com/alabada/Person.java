package com.alabada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author wenzhd
 * @Date 2018/4/8
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Person {
    private Integer age;
    private String name;
    private Double score;
    private Boolean sex;
}

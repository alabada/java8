package com.alabada;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author wenzhd
 * @Date 2018/4/2
 * @Description
 */
public class StreamTest {

    private List<Article> articles = Lists.newArrayList(
            new Article("C++", "author1", Lists.newArrayList("aaa", "bb")),
            new Article("Matlab", "author2", Lists.newArrayList("ccc","d")),
            new Article("Matlab", "author11", Lists.newArrayList("aaa")),
            new Article("C#", "author3", Lists.newArrayList("bbb")),
            new Article("Java", "author4", Lists.newArrayList("c")),
            new Article("Action Script", "author5", Lists.newArrayList("ddd", "d")),
            new Article("Java", "author6", Lists.newArrayList("ddd"))
    );

    /**
     * 过滤
     * @return
     */
    public Optional<Article> getFirstJavaArticle() {
        return articles.stream()
                .filter(article -> article.getTags().contains("Java"))
                .findFirst();
    }

    /**
     * 查找第一个
     * @param s
     * @return
     */
    public Optional<Article> getFirstJavaArticle1(String s) {
        return articles.stream()
                .filter(article -> s.equals(article.getTitle()))
                .findFirst();
    }

    @Test
    public void testStreamFilter() {
        Article article = getFirstJavaArticle1("Java").get();

        Assert.assertEquals("author4", article.getAuthor());
    }

    @Test
    public void testStreamFilterOrElse() {
        Article defaultArticle = new Article("defaultTitle", "defautAuthor", Lists.newArrayList());
        Article article = getFirstJavaArticle1("ddds").orElse(defaultArticle);

        Assert.assertEquals("defautAuthor", article.getAuthor());
    }

    @Test
    public void testGetListByStream() {
        List<Article> articleList = articles.stream()
                .filter(article -> "Java".equals(article.getTitle()))
                .collect(Collectors.toList());

        Assert.assertEquals(2, articleList.size());

    }

    /**
     * 分组
     */
    @Test
    public void testGroupingByStream() {
        Map<String, List<Article>> groupArticleMap = articles.stream()
        .collect(Collectors.groupingBy(Article::getTitle));

        Assert.assertEquals(2, groupArticleMap.get("Java").size());
        Assert.assertEquals("Java", groupArticleMap.get("Java").get(0).getTitle());

        Assert.assertEquals(2, groupArticleMap.get("Matlab").size());
        Assert.assertEquals("Matlab", groupArticleMap.get("Matlab").get(0).getTitle());

        Assert.assertEquals(1, groupArticleMap.get("C++").size());
        Assert.assertEquals("C++", groupArticleMap.get("C++").get(0).getTitle());
    }

    @Test
    public void testGetDistinctTags() {
        Set<String> distinctTags = articles.stream()
                .flatMap(article -> article.getTags().stream()) // flatmap把列表转为一个返回流
                .collect(Collectors.toSet()); // 使用collect去创建一个集合作为返回

        System.out.println(distinctTags.toString());
    }

    // 分组后为每组做统计，再给一个计数收集器就好了。
//    Map<String, Long> numEmployeesByCity =
//            employees.stream().collect(groupingBy(Employee::getCity, counting()));

    // 计算每个城市的平均年龄，这可以联合使用 averagingInt 和 groupingBy 收集器
//    Map<String, Double> avgSalesByCity =
//            employees.stream().collect(groupingBy(Employee::getCity,
//                    averagingInt(Employee::getNumSales)));

    // 找出最优秀的员工，你可以将所有雇员分为两组，一组销售量大于 N，另一组小于 N，使用 partitioningBy 收集器：
//    Map<Boolean, List<Employee>> partitioned =
//            employees.stream().collect(partitioningBy(e -> e.getNumSales() > 150));




}

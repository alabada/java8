package com.alabada;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author wenzhd
 * @Date 2018/4/2
 * @Description
 */
public class StreamTest {

    private List<Article> articles = Lists.newArrayList(
            new Article("C++", "author1", Lists.newArrayList()),
            new Article("Matlab", "author2", Lists.newArrayList()),
            new Article("Matlab", "author11", Lists.newArrayList()),
            new Article("C#", "author3", Lists.newArrayList()),
            new Article("Java", "author4", Lists.newArrayList()),
            new Article("Action Script", "author5", Lists.newArrayList()),
            new Article("Java", "author6", Lists.newArrayList())
    );

    public Optional<Article> getFirstJavaArticle() {
        return articles.stream()
                .filter(article -> article.getTags().contains("Java"))
                .findFirst();
    }

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

}

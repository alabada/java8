package com.alabada;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * @Author wenzhd
 * @Date 2018/4/2
 * @Description
 */
public class StreamTest {

    private List<Article> articles = Lists.newArrayList(
            new Article("C++", "author1", Lists.newArrayList()),
            new Article("Matlab", "author2", Lists.newArrayList()),
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

}

package com.alabada;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author wenzhd
 * @Date 2018/4/2
 * @Description
 */
public class StreamTest {

    private List<Article> articles = Lists.newArrayList(
            new Article("C++", "author1", Lists.newArrayList("aaa", "bb", "bb", "ccc")),
            new Article("Matlab", "author2", Lists.newArrayList("ccc", "d")),
            new Article("Matlab", "author11", Lists.newArrayList("aaa")),
            new Article("C#", "author3", Lists.newArrayList("bbb")),
            new Article("Java", "author4", Lists.newArrayList("c")),
            new Article("Action Script", "author5", Lists.newArrayList("ddd", "d")),
            new Article("Java", "author6", Lists.newArrayList("ddd"))
    );

    /**
     * 过滤
     *
     * @return
     */
    public Optional<Article> getFirstJavaArticle() {
        return articles.stream()
                .filter(article -> article.getTags().contains("Java"))
                .findFirst();
    }

    /**
     * 查找第一个
     *
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

    @Test
    public void testGetDistinctTags2() {
        List<String> distinctTags = articles.stream()
                .flatMap(article -> article.getTags().stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(distinctTags);
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

    private List<String> testList = Lists.newArrayList("aa", "bb", "cc", "aaa", "bb", "ccc");

    @Test
    public void testDistinctFunc() {

        List<String> result = testList.stream()
                .distinct()
                .collect(Collectors.toList());

        Assert.assertEquals(5, result.size());
    }

    @Test
    public void testLimitFunc() {
        List<String> result = testList.stream()
                .limit(3)
                .collect(Collectors.toList());

        Assert.assertEquals(3, result.size());
        Assert.assertEquals("aa", result.get(0));
        Assert.assertEquals("bb", result.get(1));
        Assert.assertEquals("cc", result.get(2));
    }

    @Test
    public void testSkipFunc() {
        List<String> result = testList.stream()
                .skip(4)
                .collect(Collectors.toList());

        Assert.assertEquals(2, result.size());
        System.out.println(result.toArray()[1]);
        Assert.assertArrayEquals(new String[]{"bb", "ccc"}, result.toArray());
    }

    @Test
    public void testMap() {
        List<String> result = articles.stream()
                .map(Article::getTitle)
                .collect(Collectors.toList());

        Assert.assertNotNull(result);
        System.out.println(result);
        Assert.assertEquals(7, result.size());
    }

    // 小流合并成大流
    @Test
    public void testFlatMap() {
        List<String> bigList = Lists.newArrayList();
        bigList.add("I am a boy");
        bigList.add("I love the girl");
        bigList.add("But the girl loves another girl");

        List<String> distinctVlauesFromLotsOfValue = bigList.stream()
                .map(lineList -> lineList.split(" ")) // 变成小流
                .flatMap(Arrays::stream) // 将很多小流合并成大流
                .distinct() // 去重
                .collect(Collectors.toList());

        System.out.println(distinctVlauesFromLotsOfValue);
    }

    /**
     * 判断流中是否存在至少一个元素满足指定的条件，条件通过Lambda表达式传递给anyMatch，
     * 执行结果为Boolean类型
     */
    @Test
    public void testAnyMatch() {
        Boolean result1 = articles.stream()
                .anyMatch(Article::isJava);

        Boolean result2 = articles.stream()
                .anyMatch(Article::isAngular);


        Assert.assertEquals(true, result1);
        Assert.assertEquals(false, result2);

    }

    /**
     * allMatch用于判断流中所有元素是否都满足指定条件
     */
    @Test
    public void testAllMatch() {
        Boolean result = articles.stream()
                .allMatch(Article::isJava);

        Assert.assertEquals(false, result);
    }

    @Test
    public void testNoneMatch() {
        Boolean result = articles.stream()
                .noneMatch(Article::isAngular);

        Assert.assertEquals(true, result);
    }

    @Test
    public void testFindAny() {
        Optional<String> any = testList.stream()
                .findAny();

        Assert.assertNotNull(any);
        System.out.println(any);
    }

    @Test
    public void testFindFirst() {
        Optional<Article> article = articles.stream()
                .findFirst();

        Assert.assertNotNull(article);
        System.out.println(article);
    }

    private List<Person> persons = Lists.newArrayList(
            new Person(20, "name1", 78.0, true),
            new Person(30, "name2", 88.0, false),
            new Person(40, "name3", 74.0, true),
            new Person(50, "name4", 75.0, false)
    );

    @Test
    public void testReduceForAdd() {
        int sumAge = persons.stream()
                .map(Person::getAge)
                .reduce(
                        0, // 第一个参数：标识初始值，这里为0
                        (p1, p2) -> p1 + p2 // 第二个参数为需要进行的规约操作，接收拥有两个参数的Lambda表达式，reduce把流中元素两两输给Lambda表达式。
                );

        Assert.assertEquals(140, sumAge);
    }

    /**
     * --------------------good example------------------------------
     */
    @Test
    public void testReduceForAdd2() {
        int sumAge = persons.stream()
                .map(Person::getAge)
                .reduce(
                        0, // 第一个参数：标识初始值，这里为0
                        Integer::sum // 第二个参数为需要进行的规约操作，使用Integer提供的sum函数代替自定义的Lambda表达式
                        // Integer 还提供了min，max等一系列数值操作。
                );

        Assert.assertEquals(140, sumAge);
    }

    @Test
    public  void testToValueable() {
        IntStream stream = persons.stream()
                .mapToInt(Person::getAge);

        Assert.assertNotNull(stream);
    }

    @Test
    public  void testToValueableAndCalculate() {
        OptionalInt maxAge = persons.stream() // 注意返回的是OprionlInt;类似还有OprionalDouble、OptionalLong
                .mapToInt(Person::getAge)
                .max();

        Assert.assertNotNull(maxAge);
        System.out.print(maxAge);
    }


}

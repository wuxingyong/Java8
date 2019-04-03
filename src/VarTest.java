package src;

import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * java 8 lambda 示例
 * @author 30377
 *
 */
public class VarTest {
    public static void main(String[] args) {
        /***************************利用lambda表达式实现Runnable接口*****************************************/
        //Java8 以前
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("too much code , for too little to do");
            }
        }).start();
        //使用Java8 方式
        new Thread(() -> System.out.println("In Java8,Lambda expressions rocks !!")).start();
        /**********************************使用lambda表达式对列表进行迭代*****************************************/

        List<String> list = Arrays.asList("Java","Scala","C++","Haskell","Lisp");
        //Java8 以前
        for (String string : list) {
            System.out.println(string);
        }
        //使用Java8 方式
        list.forEach(n -> System.out.println(n));
        list.forEach(System.out::println);
        System.out.println("--------------------------------------");
        Map<String,String> maps = new HashMap<String,String>();
        maps.put("k1", "v1");
        maps.put("k2", "v2");
        maps.put("k3", "v3");
        maps.put("k4", "v4");
        maps.values().stream().map((v)-> v + "1").forEach(System.out::println);
        maps.keySet().stream().map((k)-> "k"+k).filter(x -> x.endsWith("2")).forEach(System.out::println);
        maps.forEach((k,v) -> System.out.println("key:"+k+" value:"+v) );
        /**********************************使用lambda表达式和函数式接口Predicate*****************************************/
        System.out.println("Languages which starts with J :");
        filter(list, (str)->str.startsWith("J"));
        System.out.println("Languages which ends with a ");
        filter(list, (str)->str.endsWith("a"));
        System.out.println("Print all languages :");
        filter(list, (str)->true);
        System.out.println("Print no language : ");
        filter(list, (str)->false);
        System.out.println("Print language whose length greater than 4:");
        filter(list, (str)->str.length() > 4);
        /**********************************如何在lambda表达式中加入Predicate*****************************************/
        // 甚至可以用and()、or()和xor()逻辑函数来合并Predicate，
        // 例如要找到所有以J开始，长度为四个字母的名字，你可以合并两个Predicate并传入
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;
        list.stream().filter(startsWithJ.and(fourLetterLong)).forEach((n) -> System.out.print("nName, which starts with 'J' and four letter long is : " + n));
        /**********************************Java 8中使用lambda表达式的Map和Reduce示例*****************************************/
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        // 不使用lambda表达式为每个订单加上12%的税
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            System.out.println(price);
        }
        // 使用lambda表达式
        costBeforeTax.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);

        // 为每个订单加上12%的税
        // 老方法：
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            total = total + price;
        }
        System.out.println("Total : " + total);
        // 新方法：
        double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
        System.out.println(costBeforeTax.stream().mapToDouble(cost -> cost + .12*cost).average().getAsDouble());
        /*****************************通过过滤创建一个String列表**********************************/
        List<String> strList = Arrays.asList(new String[] {"abcd","","bcd","","defg","","jk"});
        // 创建一个字符串列表，每个字符串长度大于2
        List<String> filtered = strList.stream().filter(x -> x.length()> 2).collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);

        /*****************************对列表的每个元素应用函数**********************************/
        // 将字符串换成大写并用逗号链接起来
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(G7Countries);
        /*****************************复制不同的值，创建一个子列表**********************************/
        // 用所有不同的数字创建一个正方形列表
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
        /*****************************计算集合元素的最大值、最小值、总和以及平均值**********************************/
        //获取数字的个数、最小值、最大值、总和以及平均值
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    }

    public static void filter(List<String> names,Predicate<String> condtion) {
        for (String name : names) {
            if(condtion.test(name)) {
                System.out.println(name + " ");
            }
        }
    }
}

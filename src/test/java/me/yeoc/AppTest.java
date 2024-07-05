package me.yeoc;


import me.yeoc.ddosbot.util.HttpClientUtil;

/**
 * Unit test for simple App.
 */
public class AppTest 

{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public static void main(String[] args) {
        System.out.println(HttpClientUtil.doGet("https://baidu.com"));
    }
}

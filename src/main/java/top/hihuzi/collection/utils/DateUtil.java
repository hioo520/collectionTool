package top.hihuzi.collection.utils;

/**
 * tips
 *
 * @author: hihuzi 2018/10/21 2:29
 */
/*
 */

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    /** 存放不同的日期模板格式的sdf的Map */
    private static ThreadLocal<Map<String, SimpleDateFormat>> sdfMap = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            System.out.println(Thread.currentThread().getName()	+ " init pattern: " + Thread.currentThread());
            return new HashMap<String, SimpleDateFormat>();
        }
    };

    /**
     * 返回一个SimpleDateFormat,每个线程只会new一次pattern对应的sdf
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        Map<String, SimpleDateFormat> tl = sdfMap.get();
        SimpleDateFormat sdf = tl.get(pattern);
        if (sdf == null) {
            System.out.println(Thread.currentThread().getName()+" put new sdf of pattern " + pattern + " to map");
            sdf = new SimpleDateFormat(pattern);
            tl.put(pattern, sdf);
        }
        return sdf;
    }

    /**
     * 这样每个线程只会有一个SimpleDateFormat
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern)
            throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }

}



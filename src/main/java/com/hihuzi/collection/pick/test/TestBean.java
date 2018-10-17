package com.hihuzi.collection.pick.test;

import lombok.Data;

import java.util.Date;

/**
 * tips 测试bean
 *
 * @author: hihuzi  2018/6/27 6:39
 */
@Data
public class TestBean extends Base {

    private String id;

    private String name;

    private String email;

    private String address;
    private Date date = new Date();
}
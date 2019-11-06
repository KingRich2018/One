package com.happing.one.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.ZonedDateTime;

@TableName("employee")
@Data
public class Employee {
    @TableId
    private int id;
    private String name;
    private String sex;
    private String telephone;
    private String province;
    private String city;
    private String district;
    private ZonedDateTime entrtime;
    private String state;
}

package com.happing.one.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.ZonedDateTime;

@TableName("work")
@Data
public class Work {
    @TableId
    private int id;
    private int employeeId;
    private String name;
    private double time;
    private ZonedDateTime evanDate;
    private String remark;
}

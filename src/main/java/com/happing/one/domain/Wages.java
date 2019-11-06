package com.happing.one.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("wages")
@Data
public class Wages {
    @TableId
    private int id;
    private int employee;
    private String name;
    private double subsidy = 0.0; // 补贴
    private double wage =0.0; // 工资
    private double other =0.0;
    private String remark;
}

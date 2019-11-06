package com.happing.one.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.happing.one.domain.enumeration.RestrictType;
import lombok.Data;

@TableName("units")
@Data
public class Units {
    @TableId
    private int id;
    private String unit;
    private double punish;
    private RestrictType type;
}

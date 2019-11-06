package com.happing.one.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.happing.one.domain.enumeration.TurnoverType;
import lombok.Data;

import java.time.ZonedDateTime;

@TableName("income")
@Data
public class Income {
    @TableId
    private int id;
    private TurnoverType type; //
    private double total;
    private ZonedDateTime date;
}

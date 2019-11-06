package com.happing.one.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("one")
@Data
public class One {
    @TableId
    private Long id;
    private String img;
    private String content;
    private String may;
    private String dom;
}

package com.happing.one.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_user")
@Data
public class SysUser {
    @TableId
    private Long id;
    private String name;
    private String password;
    private String cellphone;
    private String perms;
}

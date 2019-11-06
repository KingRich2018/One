package com.happing.one.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.happing.one.domain.SysUser;
import com.happing.one.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser getSysUser(String cellphone){
        QueryWrapper<SysUser> query = new QueryWrapper();
        query.eq("cellphone",cellphone);
        return sysUserMapper.selectOne(query);
    }

}

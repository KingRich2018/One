package com.happing.one.controller;

import com.happing.one.domain.One;
import com.happing.one.mybatis.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/one")
public class OneController extends BaseController<One> {

}

package com.happing.one.controller;

import com.happing.one.domain.Units;
import com.happing.one.mybatis.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/units")
public class UnitsController extends BaseController<Units> {
}

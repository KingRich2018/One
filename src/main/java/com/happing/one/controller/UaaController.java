package com.happing.one.controller;


import com.happing.one.domain.SysUser;
import com.happing.one.service.SysUserService;
import com.happing.one.untils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.Collections;

@RestController
public class UaaController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
   public ResponseEntity login( //
     @RequestParam(name = "cellphone") String cellphone, //
      @RequestParam(name = "password") String password){
        SysUser sysUser = sysUserService.getSysUser(cellphone);
        if (sysUser == null) {
            throw new AuthenticationException("用户不存在!");
        } else if (!sysUser.getPassword().equals(password)) {
            throw new AuthenticationException("用户名或密码错误");
        } else {
            return ResponseEntity.ok(Collections.singletonMap("authorization", JwtUtil.sign(cellphone,password)));
        }
   }
   @PostMapping("/logout")
    public ResponseEntity logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    return ResponseEntity.ok().build();
    }

    @GetMapping("/unauth")
    public ResponseEntity unauth(){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

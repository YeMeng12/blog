package com.fandi.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fandi.common.dto.LoginDto;
import com.fandi.common.lang.Result;
import com.fandi.entity.User;
import com.fandi.service.UserService;
import com.fandi.util.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import com.wf.captcha.SpecCaptcha;


@RestController
public class AccountController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response){
        Object captcha = request.getSession().getAttribute("CAPTCHA");
        if(!loginDto.getCode().equals(captcha)){
            return Result.fail("验证码错误");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码错误");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");


        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    @PostMapping("/signUp")
    public Result signUp(@Validated @RequestBody User user){
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setStatus(0);
        user.setCreated(LocalDateTime.now());
        //设置默认图像
        if(user.getAvatar().equals("")){
            user.setAvatar("https://dspncdn.com/a1/media/originals/5a/5f/c0/5a5fc04082bed538818099033429567d.jpg");
        }
        userService.save(user);
        return Result.succ(null);
    }

    @ApiOperation(value = "验证码")
    @RequestMapping("/captcha")
    public Result captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        request.getSession().setAttribute("CAPTCHA", verCode);  //存入session
        HashMap<String, Object> code = new HashMap<>();
        code.put("verKey", key);
        code.put("verCode", specCaptcha.toBase64());
        // 将key和base64返回给前端
        return Result.succ(code);
    }
}

package com.chanokh.reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chanokh.reggie.common.R;
import com.chanokh.reggie.entity.User;
import com.chanokh.reggie.service.UserService;
import com.chanokh.reggie.utils.SMSUtils;
import com.chanokh.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
//            SMSUtils.sendMessage("暴龙战士","",phone
//            ,code);
            //不用手机号就这样使用验证码
            session.setAttribute(phone,code);
            log.info("验证码是: "+code);
            return R.success(code + "");
        }
        return R.error("发送失败");
    }
    @PostMapping("/login")
    public R<String> login(@RequestBody Map map, HttpSession session ) {
        log.info(map.toString());
        String phone = map.get("phone").toString();
        //获取验证嘛
        String code = map.get("code").toString();

        Object codeInSession = session.getAttribute(phone);
        if( codeInSession != null && codeInSession.equals(code)) {
            //对比成功 判断是否是新用户

            LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
            qw.eq(User::getPhone, phone);
            User user = userService.getOne(qw);
            Long id = null;

            if(user == null) {
                //新用户
                User u = new User();
                u.setPhone(phone);
                u.setStatus(1);
                userService.save(u);
                id = u.getId();
            }else id = user.getId();
            session.setAttribute("user",id);
            return R.success("登录成功");

        }else return R.error("登录失败");

    }
}

package com.chanokh.reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chanokh.reggie.common.R;
import com.chanokh.reggie.entity.Employee;
import com.chanokh.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/login")
    //为什么要httpServletRequest？ 因为登录成功后 要使用session
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        System.out.println(employee.getUsername());
        //md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        qw.eq(Employee::getUsername,employee.getUsername());
        //因为unique索引
        Employee e = employeeService.getOne(qw);
        if(e == null)  return R.error("登录失败");
        if(!e.getPassword().equals(password)) {
            return R.error("登录失败");
        }
        if(e.getStatus() != 1) return R.error("此账号已被禁用");
        //session设置 用户id
        request.getSession().setAttribute("employee",e.getId());
        return R.success(e);
    }

    //退出
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee ) {

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(new Date());
//        employee.setUpdateTime(new Date());
        Long createUserId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(createUserId);
//        employee.setUpdateUser(createUserId);
        employeeService.save(employee);
        return R.success("新增成功");
    }
    @GetMapping("/page")
    public R<Page> queryEmployee(int page, int pageSize,String name) {
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper();
        qw.like(name != null && !name.equals(""),Employee::getName,name);
        qw.orderByDesc(Employee::getUpdateTime);

       employeeService.page(pageInfo,qw);
        return R.success(pageInfo);
    }
    @PutMapping
    public R<String> changeEmployeeStatus(HttpServletRequest request, @RequestBody Employee e) {

//        e.setUpdateUser((Long) request.getSession().getAttribute("employee"));
//        e.setUpdateTime(new Date());
        employeeService.updateById(e);
        return R.success("信息修改成功");
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee e = employeeService.getById(id) ;
        if(e != null) {
            return R.success(e);
        }
        return R.error("查無此人");
    }
}

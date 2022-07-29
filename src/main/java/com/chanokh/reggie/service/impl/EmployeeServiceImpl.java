package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.entity.Employee;
import com.chanokh.reggie.service.EmployeeService;
import com.chanokh.reggie.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
 * @author Ymtx
 * @description 针对表【employee(员工信息)】的数据库操作Service实现
 * @createDate 2022-07-26 17:18:27
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {

}





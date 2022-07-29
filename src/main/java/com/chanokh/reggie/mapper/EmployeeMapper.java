package com.chanokh.reggie.mapper;

import com.chanokh.reggie.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Ymtx
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-07-26 17:18:27
* @Entity com.chanokh.reggie.entity.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}





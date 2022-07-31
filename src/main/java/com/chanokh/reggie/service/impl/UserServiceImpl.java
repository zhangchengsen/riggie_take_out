package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.entity.User;
import com.chanokh.reggie.service.UserService;
import com.chanokh.reggie.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Ymtx
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-07-30 15:30:17
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





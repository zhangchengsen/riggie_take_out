package com.chanokh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanokh.reggie.entity.AddressBook;
import com.chanokh.reggie.service.AddressBookService;
import com.chanokh.reggie.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author Ymtx
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-07-30 16:50:23
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}





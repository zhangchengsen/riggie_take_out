package com.chanokh.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段 insert");
        metaObject.setValue("createTime",new Date());
        metaObject.setValue("updateTime",new Date());
        metaObject.setValue("createUser", BaseContext.getCuurentId());
        metaObject.setValue("updateUser", BaseContext.getCuurentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段 update");
        metaObject.setValue("updateTime", new Date());
        metaObject.setValue("updateUser",BaseContext.getCuurentId());


    }
}

package com.chanokh.reggie.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chanokh.reggie.common.BaseContext;
import com.chanokh.reggie.common.R;
import com.chanokh.reggie.entity.ShoppingCart;
import com.chanokh.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        //设置用户id
        Long id = BaseContext.getCuurentId();
        //判断是菜品还是套餐

        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId, id);
        if(shoppingCart.getDishId() != null) {
            qw.eq(ShoppingCart::getDishId,shoppingCart.getDishId());

        }else {
            qw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart sc = shoppingCartService.getOne(qw);
        //查询菜品是否存在
        if(sc == null) {
            shoppingCart.setNumber(1);
            shoppingCart.setUserId(id);
            shoppingCartService.save(shoppingCart);
            sc = shoppingCart;

        }else {

            sc.setNumber(sc.getNumber() + 1);
            shoppingCartService.updateById(sc);

        }
        //统一返回
        return R.success(sc);
    }

    /**
     * 获取购物车列表
     * @return 购物车列表
     */
    @GetMapping("list")
    public R<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        Long id = BaseContext.getCuurentId();
        qw.eq(ShoppingCart::getUserId, id);
        return R.success(shoppingCartService.list(qw));
    }
    /**
     * 删除购物车物品
     */
    @DeleteMapping("clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId,BaseContext.getCuurentId());
        shoppingCartService.remove(qw);
        return R.success("删除成功");
    }

}

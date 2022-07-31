package com.chanokh.reggie.dto;


import com.chanokh.reggie.entity.Setmeal;
import com.chanokh.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;


    private String categoryName;
}

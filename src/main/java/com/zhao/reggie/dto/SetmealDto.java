package com.zhao.reggie.dto;


import com.zhao.reggie.entity.Setmeal;
import com.zhao.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

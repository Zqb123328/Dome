package com.zhao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.reggie.dto.DishDto;
import com.zhao.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时添加对应的菜品口味
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);
    //根据id修改菜品信息和对应的口味信息
    public void updateWithFlavor(DishDto dishDto);
}

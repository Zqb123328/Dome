package com.zhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.reggie.common.CoustomException;
import com.zhao.reggie.entity.Category;
import com.zhao.reggie.entity.Dish;
import com.zhao.reggie.entity.Setmeal;
import com.zhao.reggie.mapper.CategoryMapper;
import com.zhao.reggie.service.CategoryService;
import com.zhao.reggie.service.DishService;
import com.zhao.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    //根据id删除分类，删除之前需要进行判断
    @Override
    public void remove(long id) {
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
         dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
         if(count1>0){
             //已经关联菜品，抛出一个异常
             throw new CoustomException("当前分类下关联了菜品，不能删除");
         }
        //查询当前分类是否关联了套餐如果已经关联，抛出一个异常
         LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
         setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2>0){
            //已经关联套餐，抛出一个异常
            throw new CoustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}

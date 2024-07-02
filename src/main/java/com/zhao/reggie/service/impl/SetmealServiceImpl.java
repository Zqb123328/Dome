package com.zhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.reggie.common.CoustomException;
import com.zhao.reggie.dto.SetmealDto;
import com.zhao.reggie.entity.Setmeal;
import com.zhao.reggie.entity.SetmealDish;
import com.zhao.reggie.mapper.SetmealMapper;
import com.zhao.reggie.service.SetmealDishService;
import com.zhao.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联信息
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal表，执行jinsert操作
        this.save(setmealDto);
        //保存套餐的基本信息，操作setmeal_dish表，执行jinsert操作
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐
     * @param ids
     */
    @Override
    public void deleteWithDish(List<Long> ids) {
        //查询套餐状态，确定是否可以删除
        //select count（*） from setmeal where id in(1,2,3,) and status=1
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if(count>0){
            //如果不能删除，抛出一个业务异常
            throw new CoustomException("套餐正在售卖，不能删除");
        }
        //如果可以删除先删除套餐表中的数据--setmeal
        this.removeByIds(ids);
        //delete from setmeal dish wher setmeal_id in(1,2,3)
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //删除关系表中的数据--setmeal_dish
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }
}

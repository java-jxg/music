package com.fastcase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.Admin;
import com.fastcase.hadler.XGException;
import com.fastcase.service.AdminService;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-01-22
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("login/status")
    public R loginStatus(@RequestBody Admin admin, HttpSession session){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name",admin.getName());
        wrapper.eq("password",admin.getPassword());
        Admin one = adminService.getOne(wrapper);
        if(one==null){
            throw new XGException(20001,"用户名或密码错误!");
        }
        session.setAttribute("name",one.getName());
        return R.ok();
    }

    @PostMapping("list")
    public R list(){
        List<Admin> list = adminService.list();
        return R.ok().data("list",list);
    }

}


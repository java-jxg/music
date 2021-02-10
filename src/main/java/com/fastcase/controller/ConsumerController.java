package com.fastcase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.Consumer;
import com.fastcase.hadler.XGException;
import com.fastcase.service.ConsumerService;
import com.fastcase.utils.MUtils;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 前端用户 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @PostMapping("addConsumer")
    public R addConsumer(@RequestBody Consumer consumer){
        boolean save = false;
        try {
            save = consumerService.save(consumer);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("用户名已占用");
        }
        return save ? R.ok() : R.error();
    }
    @PutMapping("updateConsumer")
    public R updateConsumer(@RequestBody Consumer consumer){
        boolean b = consumerService.updateById(consumer);
        return b ? R.ok() : R.error();
    }
    @DeleteMapping("deleteConsumer/{id}")
    public R deleteConsumer(@PathVariable String id){
        boolean b = consumerService.removeById(id);
        return b ? R.ok() : R.error();
    }
    @GetMapping("selectByPrimaryKey/{id}")
    public R selectByPrimaryKey(@PathVariable String id){
        Consumer consumer = consumerService.getById(id);
        if(consumer == null) throw new XGException(20001,"查询失败");
        return R.ok().data("consumer",consumer);
    }

    @GetMapping("allConsumer")
    public R allConsumer(){
        List<Consumer> list = consumerService.list();
        if(list.size()==0) throw new XGException(20001,"查询失败");
        return R.ok().data("consumerList",list);
    }
    @PostMapping("updateConsumerPic")
    public R updateConsumerPic(@RequestParam("file") MultipartFile file, @RequestParam("id")int id){

        String path = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"consumerPic";
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        boolean b = MUtils.uploadFile(file,fileName, path);

        if(b){
            String storeAvatorPath = "/img/consumerPic/"+fileName;
            Consumer consumer = new Consumer();
            consumer.setAvator(storeAvatorPath);
            consumer.setId(id);
            boolean result = consumerService.updateById(consumer);
            return result ? R.ok() : R.error();
        }else{
            return R.error("上传图片失败");
        }
    }
    @PostMapping("loginIn")
    public R loginIn(@RequestBody Consumer consumer){

        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        wrapper.eq("username",consumer.getUsername());
        wrapper.eq("password",consumer.getPassword());
        Consumer consumer1 = consumerService.getOne(wrapper);
        if(consumer1==null){
            return R.error();
        }else{
            return R.ok().data("consumer",consumer1);
        }
    }
}


package com.fastcase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.Collect;
import com.fastcase.service.CollectService;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 收藏 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    @PostMapping("addCollect")
    public R addCollect(@RequestBody Collect collect){
        boolean save = collectService.save(collect);
        return save ? R.ok() : R.error();
    }
    @PutMapping("updateCollect")
    public R updateCollect(@RequestBody Collect collect){
        boolean b = collectService.updateById(collect);
        return b ? R.ok() : R.error();
    }
    @DeleteMapping("deleteCollect/{id}")
    public R deleteCollect(@PathVariable String id){
        boolean b = collectService.removeById(id);
        return b ? R.ok() : R.error();
    }

    @DeleteMapping("deleteCollectByUidSid/{userId}/{songId}")
    public R deleteCollectByUidSid(@PathVariable String userId,@PathVariable String songId){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("song_id",songId);
        boolean remove = collectService.remove(wrapper);
        return remove ? R.ok() : R.error();
    }
    @GetMapping("getByUserId/{userId}")
    public R getByUserId(@PathVariable String userId){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Collect> list = collectService.list(wrapper);
        return R.ok().data("collectList",list);
    }

    

}


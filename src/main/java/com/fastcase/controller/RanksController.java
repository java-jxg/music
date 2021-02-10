package com.fastcase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.Ranks;
import com.fastcase.hadler.XGException;
import com.fastcase.service.RanksService;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 评价 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-02-05
 */
@RestController
@RequestMapping("/ranks")
public class RanksController {

    @Autowired
    private RanksService ranksService;

    @PostMapping("addRanks")
    public R addRanks(@RequestBody Ranks ranks){
        boolean save = ranksService.save(ranks);
        return save ? R.ok() : R.error();
    }

    @GetMapping("average/{songListId}")
    public R average(@PathVariable String songListId){
        QueryWrapper<Ranks> wrapper = new QueryWrapper<>();
        wrapper.select("AVG(score) as avge");
        wrapper.eq("song_list_id",songListId);
        Map<String, Object> map = null;
        try {
            map = ranksService.getMap(wrapper);
            return R.ok().data("avge",map.get("avge"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new XGException(2001,"您已经给过评分了!");
        }

    }

}


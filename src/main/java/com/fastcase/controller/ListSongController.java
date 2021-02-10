package com.fastcase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.ListSong;
import com.fastcase.entity.Song;
import com.fastcase.service.ListSongService;
import com.fastcase.service.SongService;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 歌单包含歌曲列表 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/listSong")
public class ListSongController {


    @Autowired
    private ListSongService listSongService;

    @Autowired
    private SongService songService;

    @GetMapping("getSongBySongListId/{id}")
    public R getSongBySongListId(@PathVariable String id){
        QueryWrapper<ListSong> wr = new QueryWrapper<>();
        wr.eq("song_list_id",id);
        List<ListSong> list = listSongService.list(wr);
        Collection<Integer> collection = new HashSet<>();
        for (ListSong listSong :list) {
            collection.add(listSong.getSongId());
        }

        List<Song> songs = songService.listByIds(collection);
        return R.ok().data("songs",songs);

    }


}


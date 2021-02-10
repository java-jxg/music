package com.fastcase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.Song;
import com.fastcase.entity.SongList;
import com.fastcase.service.SongListService;
import com.fastcase.utils.MUtils;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 歌单 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @PostMapping("addSongList")
    public R addSongList(@RequestBody SongList songList){
        boolean save = songListService.save(songList);
        return save ? R.ok() : R.error();
    }


    @DeleteMapping("deleteSongList/{id}")
    public R deleteSongList(@PathVariable String id){
        boolean b = songListService.removeById(id);
        return b ? R.ok() : R.error();
    }
    @PutMapping("updateSongList")
    public R updateSongList(@RequestBody SongList songList){
        boolean b = songListService.updateById(songList);
        return b ? R.ok() : R.error();
    }

    @GetMapping("allSongList")
    public R allSongList(){
        List<SongList> list = songListService.list();
        if(list == null) return R.error("查询失败");
        return R.ok().data("songLists",list);
    }

    /**
     * 修改图片地址
     */
    @PostMapping("updateSongListPic")
    public R updateSongListPic(@RequestParam("file") MultipartFile file, @RequestParam("id")int id){

        String path = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songListPic";
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        boolean b = MUtils.uploadFile(file,fileName, path);

        if(b){
            String storeAvatorPath = "/img/songListPic/"+fileName;
            SongList songList = new SongList();
            songList.setPic(storeAvatorPath);
            songList.setId(id);

            boolean result = songListService.updateById(songList);
            return result ? R.ok() : R.error();
        }else{
            return R.error("上传图片失败");
        }


    }


    @GetMapping("getSongListByTitle/{title}")
    public R getSongListByTitle(@PathVariable String title){
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.like("title",title);
        List<SongList> list = songListService.list(wrapper);
        return R.ok().data("songLists",list);
    }

    @GetMapping("getSongListByStyle/{style}")
    public R getSongListByStyle(@PathVariable String style){
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.like("style",style);
        List<SongList> list = songListService.list(wrapper);
        return R.ok().data("songLists",list);
    }





}


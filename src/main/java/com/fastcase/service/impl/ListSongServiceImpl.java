package com.fastcase.service.impl;

import com.fastcase.entity.ListSong;
import com.fastcase.mapper.ListSongMapper;
import com.fastcase.service.ListSongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌单包含歌曲列表 服务实现类
 * </p>
 *
 * @author Reg
 * @since 2021-01-29
 */
@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {

}

import {get,post,put,del} from './http'

//查询歌手
export const getAllSongList =() => get(`songList/allSongList`);

//添加歌手
export const addSongList = (params) => post('songList/addSongList',params)

//修改歌手
export const editSongList = (params) => put('songList/updateSongList',params)

//修改歌手
export const deleteSongList = (id) => del(`songList/deleteSongList/${id}`)


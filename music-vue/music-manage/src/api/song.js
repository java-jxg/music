import {get,post,put,del} from './http'

// export const getSongById = (singerId) => {
//   return request({
//     url: `song/getSongById/${singerId}`,
//     method: 'get'
//   })
// }
//
// //添加歌手
export const getSongById = (singerId) => get(`song/getSongById/${singerId}`)
export const addSong = (params) => post('song/addSong',params)
export const updateSong = (params) => put('song/updateSong',params)
export const deleteSong = (id) => del(`song/deleteSong/${id}`)
export const getAllSong =() => get(`song/allSong`);
export const getSongByKId = (id) => get(`song/getSongByKId/${id}`)


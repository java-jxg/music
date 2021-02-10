import {get,post,put,del} from './http'

export const getByUserId = (id) => get(`collect/getByUserId/${id}`)
export const deleteCollectByUidSid = (userId,songId) => del(`collect/deleteCollectByUidSid/${userId}/${songId}`)


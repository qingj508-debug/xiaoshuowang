import request from '../utils/request'

export function bookCommendList() {
    return request.get('/home/homeBook/bookCommendList');
}

export function latestNewsList() {
    return request.get('/home/homeNews/latestNewsList');
}

export function updateRankList() {
    return request.get('/home/updateRankList');
}

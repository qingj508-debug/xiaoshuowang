import request from '../utils/request'

export function listCategorys(params) {
    return request.get('/book/bookCategory/listByWorkDirection', { params });
}

export function searchBooks(params) {
    return request.get('/search/searchByPage', { params });
}

export function getBookById(bookId) {
    return request.get(`/book/book/getBookById/${bookId}`);
}


export function getLastChapterAbout(params) {
    return request.get('/book/book/lastChapter/about', { params });
}

export function listRecBooks(params) {
    return request.get('/book/book/recList', { params });
}

export function listChapters(params) {
    return request.get('/book/bookIndex/chapterList', { params });
}


export function listVisitRankBooks() {
    return request.get('/book/book/visit_rank');
}

export function listNewestRankBooks() {
    return request.get('/book/book/newest_rank');
}

export function listUpdateRankBooks() {
    return request.get('/book/book/update_rank');
}

export function listNewestComments(params) {
    return request.get('/book/book/comment/newestList',{ params });
}
export function comment(params) {
    return request.post('/book/bookComment/comment',params );
}


export function getBookContent(chapterId) {
    return request.get(`/book/bookContent/content/${chapterId}`);
}

export function getPreChapterId(chapterId) {
    return request.get(`/book/bookContent/preChapterId/${chapterId}`);
}

export function getNextChapterId(chapterId) {
    return request.get(`/book/bookContent/nextChapterId/${chapterId}`);
}



export function addBookVisitCount(bookId) {
    return request.post(`/book/book/addVisitCount/${bookId}` );
}   
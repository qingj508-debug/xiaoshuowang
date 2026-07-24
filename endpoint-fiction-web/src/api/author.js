import request from '../utils/request'

export function getAuthorStatus() {
    return request.get('/author/author/status');
}

export function register(params) {
    return request.post('/author/author/register', params);
}

export function listBooks(params) {
    return request.get('/author/author/books', { params });
}

export function publishBook(params) {
    return request.post('/author/author/book', params);
}

export function listChapters(bookId, params) {
    return request.get(`/author/author/chapters/${bookId}`, { params });
}

export function saveBookChapter(bookId,params) {
    return request.post(`/author/author/chapter/${bookId}`, params);
}
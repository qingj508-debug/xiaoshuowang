import request from '../utils/request'

export function sendSmsCode(params) {
    return request.get('/member/register/sms/sendCode',{ params });
}
export function register(params) {
    return request.post('/member/register', params);
}


export function login(params) {
    return request.post('/member/login', params);
}


export function oauthLoginWeibo(params) {
    return request.get('/member/oauth2/weibo/login/success', { params });
}


export function getAccountBalance() {
    return request.get('/member/member/getAccountBalance');
}

export function orderAliPay(params) {
    return request.get('/thirdparty/orderPay/aliPay', {params} );
}

export function buyBookByIndexId(chapterId) {
    return request.post(`/member/memberBuyRecord/buyBookIndex/${chapterId}`);
}

export function isAddToBookshelf(bookId) {
    return request.get(`/member/memberBookshelf/isAddToBookshelf/${bookId}`);
}

export function addToBookshelf(params) {
    return request.post('/member/memberBookshelf/addToBookshelf',params);
}


export function addReadHistory(params) {
    return request.post('/member/memberReadHistory/addReadHistory',params);
}   


export function getBookShelfList() {
    return request.get('/member/memberBookshelf/getBookShelfList');
}


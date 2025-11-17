(function () {
    'use strict';
    var t = null, n = 1e4, r = !1;

    function i() {
        console.debug(t, n, r)
        if (t) clearTimeout(t);
        t = setTimeout(c, n)
    }

    function c() {
        var e = localStorage.getItem('access_token');
        if (e && !r) {
            if (!function (e) {
                try {
                    var t = JSON.parse(atob(e.split('.')[1])), n = t.exp * 1e3, r = Date.now(), o = 3e5;
                    return r >= n - o
                } catch (e) {
                    return !0
                }
            }(e)) return;
            r = !0;
            var o = localStorage.getItem('refresh_token');
            if (o) try {
                fetch('/api/auth/refresh', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({refresh_token: o})
                }).then(function (t) {
                    return t.json()
                }).then(function (t) {
                    if (t.success && t.data) {
                        localStorage.setItem('access_token', t.data.access_token), localStorage.setItem('refresh_token', t.data.refresh_token);
                        r = !1
                    } else throw new Error(t.msg || '刷新失败')
                }).catch(function (e) {
                    console.error('刷新token失败:', e), localStorage.removeItem('access_token'), localStorage.removeItem('refresh_token'), r = !1,
                        setTimeout(function () {
                            window.location.href = '/index.html'
                        }, 1e3)
                })
            } catch (e) {
                console.error('刷新请求失败:', e), r = !1
            }
        } else r = !1
    }

    document.addEventListener('click', i), setTimeout(c, 2e3)
})();
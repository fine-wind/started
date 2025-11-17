const {createApp} = Vue;

createApp({
    data() {
        return {
            userInfo: {
                username: '加载中...',
                email: ''
            },
            stats: {
                articles: 0,
                views: 0,
                likes: 0,
                followers: 0,
                favorites: 0,
                comments: 0
            },
            searchKeyword: '',
            showUserMenu: false,
            notification: {
                show: false,
                message: '',
                type: 'success'
            },
            isLoading: true,
            // iframe 相关状态
            iframeLoading: false,
            currentIframeSrc: 'main-content/main.html',
            // 树形菜单数据
            menuTree: [
                {
                    id: 1,
                    label: '首页',
                    icon: 'fas fa-home',
                    active: true,
                    expanded: false,
                    url: 'dashboard-content.html'
                },
                {
                    id: 1,
                    label: '分组管理',
                    icon: 'fas fa-home',
                    active: false,
                    expanded: false,
                    url: 'dashboard-content.html'
                },
                {
                    id: 2,
                    label: '内容管理',
                    icon: 'fas fa-edit',
                    expanded: false,
                    children: [
                        {
                            id: 21,
                            label: '写文章',
                            icon: 'fas fa-pen',
                            active: false,
                            url: 'editor.html'
                        },
                        {
                            id: 22,
                            label: '我的文章',
                            icon: 'fas fa-file-alt',
                            active: false,
                            url: 'articles.html',
                            badge: 0
                        },
                        {
                            id: 23,
                            label: '文章草稿',
                            icon: 'fas fa-file-contract',
                            active: false,
                            url: 'drafts.html',
                            badge: 3
                        },
                        {
                            id: 24,
                            label: '分类管理',
                            icon: 'fas fa-folder',
                            active: false,
                            url: 'categories.html'
                        }
                    ]
                },
                {
                    id: 5,
                    label: '设置',
                    icon: 'fas fa-cog',
                    expanded: false,
                    children: [
                        {
                            id: 51,
                            label: '个人资料',
                            icon: 'fas fa-user',
                            active: false,
                            url: './main-content/profile.html'
                        },
                        {
                            id: 52,
                            label: '账户设置',
                            icon: 'fas fa-cog',
                            active: false,
                            url: 'settings.html'
                        },
                        {
                            id: 53,
                            label: '安全设置',
                            icon: 'fas fa-shield-alt',
                            active: false,
                            url: 'security.html'
                        }
                    ]
                }
            ]
        };
    },
    methods: {
        // 显示页面内容
        showPage() {
            const app = document.getElementById('app');
            const loadingOverlay = document.getElementById('loadingOverlay');

            if (app && loadingOverlay) {
                app.classList.add('loaded');
                setTimeout(() => {
                    loadingOverlay.classList.add('hidden');
                }, 300);
            }
        },

        // 初始化数据
        async initData() {
            try {
                await this.loadUserInfo();
                this.updateMenuBadges();
            } catch (error) {
                console.error('初始化数据失败:', error);
                this.showNotification('初始化数据失败', 'error');
            } finally {
                // 数据加载完成后显示页面
                this.showPage();
                this.isLoading = false;
            }
        },

        // 更新菜单徽章
        updateMenuBadges() {
            // 更新文章数量徽章
            const articlesItem = this.findMenuItem(this.menuTree, 22);
            if (articlesItem) {
                articlesItem.badge = this.stats.articles;
            }

            // 更新收藏数量徽章
            const favoritesItem = this.findMenuItem(this.menuTree, 32);
            if (favoritesItem) {
                favoritesItem.badge = this.stats.favorites;
            }

            // 更新评论数量徽章
            const commentsItem = this.findMenuItem(this.menuTree, 31);
            if (commentsItem) {
                commentsItem.badge = this.stats.comments;
            }
        },

        // 查找菜单项
        findMenuItem(menu, id) {
            for (let item of menu) {
                if (item.id === id) {
                    return item;
                }
                if (item.children) {
                    const found = this.findMenuItem(item.children, id);
                    if (found) return found;
                }
            }
            return null;
        },

        // 切换菜单项展开状态
        toggleMenuItem(item) {
            if (item.children) {
                // 有子菜单的项：切换展开状态
                item.expanded = !item.expanded;
            } else {
                // 没有子菜单的项：设置为激活状态并加载页面
                this.loadPage(item);
            }
        },

        // 设置激活的菜单项
        setActiveMenuItem(activeId) {
            const setActive = (menu) => {
                menu.forEach(item => {
                    item.active = item.id === activeId;
                    if (item.children) {
                        setActive(item.children);
                    }
                });
            };
            setActive(this.menuTree);
        },

        // 加载页面到iframe
        loadPage(menuItem) {
            this.setActiveMenuItem(menuItem.id);

            // 更新iframe源
            this.currentIframeSrc = menuItem.url;

            // 显示加载状态
            this.iframeLoading = true;
        },

        // 处理菜单点击
        handleMenuClick(menuItem) {
            this.loadPage(menuItem);
        },

        // iframe 加载完成
        onIframeLoad() {
            this.iframeLoading = false;
            console.log('iframe加载完成:', this.currentIframeSrc);
        },

        // iframe 加载错误
        onIframeError() {
            this.iframeLoading = false;
            this.showNotification('页面加载失败，请重试', 'error');
        },

        // 加载用户信息
        async loadUserInfo() {
            try {
                const token = localStorage.getItem('access_token');
                if (!token) {
                    this.redirectToLogin();
                    return;
                }

                const response = await fetch('/api/auth/validate', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (!response.ok) {
                    // Token无效，尝试刷新
                    const newToken = await this.refreshToken();
                    if (newToken) {
                        await this.loadUserInfo();
                        return;
                    } else {
                        this.redirectToLogin();
                        return;
                    }
                }

                const data = await response.json();
                if (data.success) {
                    this.userInfo.username = data.data.username;
                }
            } catch (error) {
                console.error('加载用户信息失败:', error);
                this.showNotification('加载用户信息失败', 'error');
            }
        },

        // 刷新Token
        async refreshToken() {
            const refreshToken = localStorage.getItem('refresh_token');
            if (!refreshToken) {
                return null;
            }

            try {
                const response = await fetch('/api/auth/refresh', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        refresh_token: refreshToken
                    })
                });

                const data = await response.json();

                if (response.ok && data.success) {
                    localStorage.setItem('access_token', data.data.access_token);
                    localStorage.setItem('refresh_token', data.data.refresh_token);
                    return data.data.access_token;
                } else {
                    this.redirectToLogin();
                    return null;
                }
            } catch (error) {
                console.error('Token刷新失败:', error);
                this.redirectToLogin();
                return null;
            }
        },

        // 切换用户菜单
        toggleUserMenu() {
            this.showUserMenu = !this.showUserMenu;
        },

        // 退出登录
        async logout() {
            try {
                const refreshToken = localStorage.getItem('refresh_token');

                if (refreshToken) {
                    await fetch('/api/auth/logout', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            refresh_token: refreshToken
                        })
                    });
                }

                // 清除本地存储
                localStorage.removeItem('access_token');
                localStorage.removeItem('refresh_token');

                // 跳转到登录页
                this.redirectToLogin();

            } catch (error) {
                console.error('退出登录失败:', error);
                // 即使API调用失败，也清除本地存储并跳转
                localStorage.removeItem('access_token');
                localStorage.removeItem('refresh_token');
                this.redirectToLogin();
            }
        },

        // 重定向到登录页
        redirectToLogin() {
            window.location.href = '/index.html';
        },

        // 显示通知
        showNotification(message, type) {
            this.notification.message = message;
            this.notification.type = type;
            this.notification.show = true;

            setTimeout(() => {
                this.notification.show = false;
            }, 3000);
        }
    },
    mounted() {
        // 初始化数据
        this.initData();

        // 点击页面其他地方关闭用户菜单
        document.addEventListener('click', (e) => {
            if (!e.target.closest('.user-menu')) {
                this.showUserMenu = false;
            }
        });
    }
}).mount('#app');
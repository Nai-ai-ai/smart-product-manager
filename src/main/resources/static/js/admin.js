// 后台管理系统主脚本
document.addEventListener('DOMContentLoaded', function() {
    // 菜单点击切换
    const menuItems = document.querySelectorAll('.menu-item');
    const mainFrame = document.getElementById('mainFrame');

    menuItems.forEach(function(item) {
        item.addEventListener('click', function() {
            // 移除所有active类
            menuItems.forEach(function(mi) {
                mi.classList.remove('active');
            });
            // 添加当前active类
            this.classList.add('active');
            // 切换iframe
            const src = this.getAttribute('data-src');
            if (src && mainFrame) {
                mainFrame.src = src;
            }
        });
    });
});

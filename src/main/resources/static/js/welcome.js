// 欢迎页脚本
document.addEventListener('DOMContentLoaded', function() {
    loadStats();
});

function loadStats() {
    // 加载商品数量
    fetch('/api/product/count')
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                document.getElementById('productCount').textContent = data.data;
            }
        })
        .catch(function(error) {
            console.error('加载商品数量失败:', error);
        });

    // 加载分类数量
    fetch('/api/category/list')
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                document.getElementById('categoryCount').textContent = data.data.length;
            }
        })
        .catch(function(error) {
            console.error('加载分类数量失败:', error);
        });
}

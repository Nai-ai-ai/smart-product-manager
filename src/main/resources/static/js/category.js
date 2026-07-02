// 分类管理脚本
var isEdit = false;

document.addEventListener('DOMContentLoaded', function() {
    loadCategoryList();
});

// 加载分类列表
function loadCategoryList() {
    fetch('/api/category/list')
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                renderCategoryTable(data.data);
            } else {
                alert('加载失败：' + data.msg);
            }
        })
        .catch(function(error) {
            console.error('加载分类列表失败:', error);
            document.getElementById('categoryTable').innerHTML =
                '<tr><td colspan="4" style="text-align: center; padding: 40px; color: #f00;">加载失败</td></tr>';
        });
}

// 渲染分类表格
function renderCategoryTable(categories) {
    var tbody = document.getElementById('categoryTable');
    if (!categories || categories.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 40px;">暂无数据</td></tr>';
        return;
    }

    var html = '';
    categories.forEach(function(cat) {
        html += '<tr>' +
            '<td>' + cat.id + '</td>' +
            '<td>' + cat.name + '</td>' +
            '<td>' + (cat.descp || '-') + '</td>' +
            '<td class="actions">' +
            '<button class="btn btn-sm btn-primary" onclick="showEditModal(' + cat.id + ')">编辑</button>' +
            '<button class="btn btn-sm btn-danger" onclick="deleteCategory(' + cat.id + ')">删除</button>' +
            '</td>' +
            '</tr>';
    });
    tbody.innerHTML = html;
}

// 显示新增模态框
function showAddModal() {
    isEdit = false;
    document.getElementById('modalTitle').textContent = '新增分类';
    document.getElementById('categoryId').value = '';
    document.getElementById('categoryName').value = '';
    document.getElementById('categoryDesc').value = '';
    document.getElementById('categoryModal').classList.add('active');
}

// 显示编辑模态框
function showEditModal(id) {
    isEdit = true;
    document.getElementById('modalTitle').textContent = '编辑分类';

    // 获取分类详情
    fetch('/api/category/' + id)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                var cat = data.data;
                document.getElementById('categoryId').value = cat.id;
                document.getElementById('categoryName').value = cat.name;
                document.getElementById('categoryDesc').value = cat.descp || '';
                document.getElementById('categoryModal').classList.add('active');
            } else {
                alert('获取分类详情失败：' + data.msg);
            }
        })
        .catch(function(error) {
            console.error('获取分类详情失败:', error);
            alert('获取分类详情失败');
        });
}

// 关闭模态框
function closeModal() {
    document.getElementById('categoryModal').classList.remove('active');
}

// 保存分类
function saveCategory() {
    var categoryData = {
        name: document.getElementById('categoryName').value.trim(),
        descp: document.getElementById('categoryDesc').value.trim()
    };

    if (!categoryData.name) {
        alert('请输入分类名称');
        return;
    }

    var url, method;
    if (isEdit) {
        categoryData.id = parseInt(document.getElementById('categoryId').value);
        url = '/api/category/update';
        method = 'POST';
    } else {
        url = '/api/category/save';
        method = 'POST';
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(categoryData)
    })
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                alert(isEdit ? '更新成功' : '新增成功');
                closeModal();
                loadCategoryList();
            } else {
                alert('操作失败：' + data.msg);
            }
        })
        .catch(function(error) {
            console.error('保存分类失败:', error);
            alert('操作失败');
        });
}

// 删除分类
function deleteCategory(id) {
    if (!confirm('确定要删除该分类吗？')) {
        return;
    }

    fetch('/api/category/delete/' + id, {
        method: 'POST'
    })
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                alert('删除成功');
                loadCategoryList();
            } else {
                alert('删除失败：' + data.msg);
            }
        })
        .catch(function(error) {
            console.error('删除分类失败:', error);
            alert('删除失败');
        });
}

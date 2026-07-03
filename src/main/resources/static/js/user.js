// 用户管理脚本
var isEdit = false;

document.addEventListener('DOMContentLoaded', function() {
    loadUserList();
});

// 加载用户列表
function loadUserList() {
    fetch('/api/user/list')
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                renderUserTable(data.data);
            } else {
                alert('加载失败：' + data.msg);
            }
        })
        .catch(function(error) {
            console.error('加载用户列表失败:', error);
            document.getElementById('userTable').innerHTML =
                '<tr><td colspan="4" style="text-align: center; padding: 40px; color: #f00;">加载失败</td></tr>';
        });
}

// 渲染用户表格
function renderUserTable(users) {
    var tbody = document.getElementById('userTable');
    if (!users || users.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 40px;">暂无数据</td></tr>';
        return;
    }

    var html = '';
    users.forEach(function(user) {
        var statusHtml = user.active === 1
            ? '<span style="color: #34c759;">启用</span>'
            : '<span style="color: #ff3b30;">禁用</span>';
        html += '<tr>' +
            '<td>' + user.id + '</td>' +
            '<td>' + user.username + '</td>' +
            '<td>' + statusHtml + '</td>' +
            '<td class="actions">' +
            '<button class="btn btn-sm btn-primary" onclick="showEditModal(' + user.id + ')">编辑</button>' +
            '<button class="btn btn-sm btn-danger" onclick="deleteUser(' + user.id + ')">删除</button>' +
            '</td>' +
            '</tr>';
    });
    tbody.innerHTML = html;
}

// 显示新增模态框
function showAddModal() {
    isEdit = false;
    document.getElementById('modalTitle').textContent = '新增用户';
    document.getElementById('userId').value = '';
    document.getElementById('userName').value = '';
    document.getElementById('userPassword').value = '';
    document.getElementById('userPassword').required = true;
    document.getElementById('passwordHint').style.display = 'none';
    document.getElementById('userActive').value = '1';
    document.getElementById('userModal').classList.add('active');
}

// 显示编辑模态框
function showEditModal(id) {
    isEdit = true;
    document.getElementById('modalTitle').textContent = '编辑用户';
    document.getElementById('userPassword').required = false;
    document.getElementById('passwordHint').style.display = 'block';

    // 获取用户详情
    fetch('/api/user/' + id)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                var user = data.data;
                document.getElementById('userId').value = user.id;
                document.getElementById('userName').value = user.username;
                document.getElementById('userPassword').value = '';
                document.getElementById('userActive').value = user.active;
                document.getElementById('userModal').classList.add('active');
            } else {
                alert('获取用户详情失败：' + data.msg);
            }
        })
        .catch(function(error) {
            console.error('获取用户详情失败:', error);
            alert('获取用户详情失败');
        });
}

// 关闭模态框
function closeModal() {
    document.getElementById('userModal').classList.remove('active');
}

// 保存用户
function saveUser() {
    var userData = {
        username: document.getElementById('userName').value.trim(),
        password: document.getElementById('userPassword').value,
        active: parseInt(document.getElementById('userActive').value)
    };

    if (!userData.username) {
        alert('请输入用户名');
        return;
    }

    var url, method;
    if (isEdit) {
        userData.id = parseInt(document.getElementById('userId').value);
        // 编辑时如果密码为空，不传密码
        if (!userData.password) {
            delete userData.password;
        }
        url = '/api/user/update';
        method = 'POST';
    } else {
        // 新增时密码必填
        if (!userData.password) {
            alert('请输入密码');
            return;
        }
        url = '/api/user/save';
        method = 'POST';
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                alert(isEdit ? '更新成功' : '新增成功');
                closeModal();
                loadUserList();
            } else {
                alert('操作失败：' + data.msg);
            }
        })
        .catch(function(error) {
            console.error('保存用户失败:', error);
            alert('操作失败');
        });
}

// 删除用户
function deleteUser(id) {
    if (!confirm('确定要删除该用户吗？')) {
        return;
    }

    fetch('/api/user/delete/' + id, {
        method: 'POST'
    })
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data.code === 200) {
                alert('删除成功');
                loadUserList();
            } else {
                alert('删除失败：' + data.msg);
            }
        })
        .catch(function(error) {
            console.error('删除用户失败:', error);
            alert('删除失败');
        });
}

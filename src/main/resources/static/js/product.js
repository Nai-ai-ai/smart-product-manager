// 全局缓存分类下拉HTML，仅首次页面加载请求一次分类接口
let categoryHtmlCache = null;

// 页面初始化
$(function () {
    loadCategoryOptions();
    loadProductTable("", null, 1);
});

/**
 * 加载商品分类下拉选项
 */
function loadCategoryOptions() {
    // 已有缓存直接复用，不重复发请求
    if (categoryHtmlCache) {
        $("#searchCategory").html(categoryHtmlCache);
        $("#productCategory").html(categoryHtmlCache);
        return;
    }
    $.get("/api/category/list", function (res) {
        if (res.code === 200) {
            let optHtml = '<option value="">全部分类</option>';
            res.data.forEach(item => {
                optHtml += `<option value="${item.id}">${item.name}</option>`;
            });
            // 存入全局缓存
            categoryHtmlCache = optHtml;
            $("#searchCategory").html(optHtml);
            $("#productCategory").html(optHtml);
        }
    })
}

/**
 * 加载商品分页列表
 */
function loadProductTable(name, categoryId, pageNum) {
    $.get("/api/product/page", {
        name: name,
        categoryId: categoryId,
        pageNum: pageNum,
        pageSize: 10
    }, function (res) {
        if (res.code !== 200) {
            alert("加载失败：" + res.msg);
            return;
        }
        let page = res.data;
        let html = "";
        page.list.forEach(item => {
            let categoryName = item.categoryName ? item.categoryName : "-";
            let imgHtml = item.photoUrl 
                ? `<img src="${item.photoUrl}" style="width:50px;height:50px;object-fit:cover;border-radius:4px;" onerror="this.outerHTML='📦'">`
                : '📦';
            html += `
                <tr>
                    <td>${item.id}</td>
                    <td>${imgHtml}</td>
                    <td>${item.name}</td>
                    <td>${categoryName}</td>
                    <td>¥${item.price}</td>
                    <td>${item.releaseDate || '-'}</td>
                    <td>
                        <button class="btn btn-sm btn-blue" onclick="editProduct(${item.id})">编辑</button>
                        <button class="btn btn-sm btn-red" onclick="deleteProduct(${item.id})">删除</button>
                    </td>
                </tr>
            `;
        });
        $("#productTable").html(html);
        renderPage(page.pageNum, page.pages, page.total);
    })
}

/**
 * 搜索按钮点击事件
 */
function searchProduct() {
    let name = $("#searchName").val();
    let categoryId = $("#searchCategory").val();
    loadProductTable(name, categoryId, 1);
}

/**
 * 重置筛选条件按钮
 */
function resetSearch() {
    $("#searchName").val("");
    $("#searchCategory").val("");
    loadProductTable("", null, 1);
}

/**
 * 关闭模态框
 */
function closeModal() {
    $("#productModal").removeClass("active");
}

/**
 * 打开新增商品模态框
 */
function openAddProductModal() {
    $("#productModalLabel").text("新增商品");
    $("#productId").val("");
    $("#productName").val("");
    $("#productCategory").val("");
    $("#productPrice").val("");
    $("#productDesc").val("");
    $("#productImage").val("");
    $("#productReleaseDate").val("");
    $("#productModal").addClass("active");
}

/**
 * 编辑商品
 */
function editProduct(id) {
    $.get("/api/product/" + id, function (res) {
        if (res.code === 200) {
            let item = res.data;
            $("#productModalLabel").text("编辑商品");
            $("#productId").val(item.id);
            $("#productName").val(item.name);
            $("#productCategory").val(item.catId);
            $("#productPrice").val(item.price);
            $("#productDesc").val(item.descp);
            $("#productImage").val(item.photoUrl);
            $("#productReleaseDate").val(item.releaseDate);
            $("#productModal").addClass("active");
        } else {
            alert("获取商品详情失败：" + res.msg);
        }
    })
}

/**
 * 保存商品（新增/编辑）
 */
function saveProduct() {
    let id = $("#productId").val();
    let data = {
        name: $("#productName").val().trim(),
        catId: $("#productCategory").val() ? parseInt($("#productCategory").val()) : null,
        price: $("#productPrice").val() ? parseFloat($("#productPrice").val()) : null,
        descp: $("#productDesc").val().trim(),
        photoUrl: $("#productImage").val().trim(),
        releaseDate: $("#productReleaseDate").val() || null
    };

    if (!data.name) {
        alert("请输入商品名称");
        return;
    }

    let url = id ? "/api/product/update" : "/api/product/save";
    if (id) {
        data.id = parseInt(id);
    }

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (res) {
            if (res.code === 200) {
                alert(id ? "更新成功" : "新增成功");
                $("#productModal").removeClass("active");
                searchProduct();
            } else {
                alert("操作失败：" + res.msg);
            }
        },
        error: function () {
            alert("请求失败");
        }
    });
}

/**
 * 删除商品
 */
function deleteProduct(id) {
    if (!confirm("确定要删除该商品吗？")) {
        return;
    }
    $.post("/api/product/delete/" + id, function (res) {
        if (res.code === 200) {
            alert("删除成功");
            searchProduct();
        } else {
            alert("删除失败：" + res.msg);
        }
    })
}

/**
 * 渲染分页控件
 */
function renderPage(pageNum, pages, total) {
    let html = '';
    html += `<span class="page-info">共 ${total} 条记录，第 ${pageNum}/${pages} 页</span>`;
    if (pageNum > 1) {
        html += `<button class="btn btn-sm" onclick="loadProductTable($('#searchName').val(), $('#searchCategory').val(), ${pageNum - 1})">上一页</button>`;
    }
    if (pageNum < pages) {
        html += `<button class="btn btn-sm" onclick="loadProductTable($('#searchName').val(), $('#searchCategory').val(), ${pageNum + 1})">下一页</button>`;
    }
    $("#pagination").html(html);
}

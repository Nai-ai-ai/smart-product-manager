package com.guat.lzp.product.controller;

import com.guat.lzp.product.entity.ApiResult;
import com.guat.lzp.product.entity.Category;
import com.guat.lzp.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 商品分类控制器
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /** 查询所有分类 */
    @GetMapping("/list")
    public ApiResult<List<Category>> list() {
        List<Category> categories = categoryService.findAll();
        return ApiResult.success(categories);
    }

    /** 根据ID查询分类 */
    @GetMapping("/{id}")
    public ApiResult<Category> getById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return ApiResult.error("分类不存在");
        }
        return ApiResult.success(category);
    }

    /** 新增分类 */
    @PostMapping("/save")
    public ApiResult<String> add(@RequestBody Category category) {
        boolean success = categoryService.save(category);
        return success ? ApiResult.success("新增成功", null) : ApiResult.error("新增失败");
    }

    /** 更新分类 */
    @PostMapping("/update")
    public ApiResult<String> update(@RequestBody Category category) {
        boolean success = categoryService.update(category);
        return success ? ApiResult.success("更新成功", null) : ApiResult.error("更新失败");
    }

    /** 删除分类 */
    @PostMapping("/delete/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        try {
            boolean success = categoryService.deleteById(id);
            return success ? ApiResult.success("删除成功", null) : ApiResult.error("删除失败");
        } catch (RuntimeException e) {
            return ApiResult.error(e.getMessage());
        }
    }

    /** 获取分类总数 */
    @GetMapping("/count")
    public ApiResult<Integer> count() {
        int total = categoryService.countAll();
        return ApiResult.success(total);
    }
}

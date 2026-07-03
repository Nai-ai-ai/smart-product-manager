package com.guat.lzp.product.controller;

import com.guat.lzp.product.entity.ApiResult;
import com.guat.lzp.product.entity.PageResult;
import com.guat.lzp.product.entity.Product;
import com.guat.lzp.product.entity.ProductVO;
import com.guat.lzp.product.service.ProductService;
import com.guat.lzp.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理控制器
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    /** 分页查询商品列表 */
    @RequestMapping(value = {"/page", "/list"}, method = RequestMethod.GET)
    public ApiResult<PageResult<ProductVO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        PageResult<ProductVO> result = productService.findByPage(pageNum, pageSize, name, categoryId, status, minPrice, maxPrice);
        return ApiResult.success(result);
    }

    /** 根据ID查询商品详情 */
    @GetMapping("/{id}")
    public ApiResult<ProductVO> getById(@PathVariable Long id) {
        ProductVO product = productService.findById(id);
        if (product == null) {
            return ApiResult.error("商品不存在");
        }
        return ApiResult.success(product);
    }

    /** 新增商品 */
    @PostMapping("/save")
    public ApiResult<String> add(@RequestBody Product product) {
        boolean success = productService.save(product);
        return success ? ApiResult.success("新增成功", null) : ApiResult.error("新增失败");
    }

    /** 更新商品信息 */
    @PostMapping("/update")
    public ApiResult<String> update(@RequestBody Product product) {
        ProductVO updated = productService.update(product);
        return updated != null ? ApiResult.success("更新成功", null) : ApiResult.error("更新失败");
    }

    /** 删除商品 */
    @PostMapping("/delete/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        boolean success = productService.deleteById(id);
        return success ? ApiResult.success("删除成功", null) : ApiResult.error("删除失败");
    }

    /** 获取商品总数 */
    @GetMapping("/count")
    public ApiResult<Integer> count() {
        int total = productService.countAll();
        return ApiResult.success(total);
    }

    /** 获取用户总数 */
    @GetMapping("/user-count")
    public ApiResult<Integer> userCount() {
        int total = userService.countAll();
        return ApiResult.success(total);
    }
}

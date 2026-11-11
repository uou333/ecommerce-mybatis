package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.mapper.ProductMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品测试 Controller：对外暴露 REST 接口，便于逐一验证 CRUD 与动态条件查询。
 * 实验阶段直接注入 Mapper，省略 Service 层。
 *
 * @author 实验小组
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductMapper productMapper;

    // 构造器注入
    public ProductController(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /** 新增商品，返回回填后的自增主键与影响行数 */
    @PostMapping
    public Map<String, Object> add(@RequestBody Product product) {
        int rows = productMapper.insert(product);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("id", product.getId()); // 主键回显
        result.put("product", product);
        return result;
    }

    /** 按主键查询 */
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productMapper.selectById(id);
    }

    /** 查询全部上架商品 */
    @GetMapping("/list")
    public List<Product> listAll() {
        return productMapper.selectAll();
    }

    /** 修改商品 */
    @PutMapping
    public Map<String, Object> update(@RequestBody Product product) {
        int rows = productMapper.update(product);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        return result;
    }

    /** 按主键删除 */
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        int rows = productMapper.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        return result;
    }

    /** 动态条件多条件查询（条件均可为空） */
    @GetMapping("/search")
    public List<Product> search(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) BigDecimal minPrice,
                               @RequestParam(required = false) BigDecimal maxPrice) {
        return productMapper.search(keyword, categoryId, minPrice, maxPrice);
    }

    /** 批量插入 */
    @PostMapping("/batch")
    public Map<String, Object> addBatch(@RequestBody List<Product> products) {
        int rows = productMapper.insertBatch(products);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        return result;
    }

    /** 批量删除 */
    @DeleteMapping("/batch")
    public Map<String, Object> deleteBatch(@RequestParam List<Long> ids) {
        int rows = productMapper.deleteBatchIds(ids);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        return result;
    }

    /** 动态排序查询：price_asc / price_desc / 其他默认按创建时间倒序 */
    @GetMapping("/order")
    public List<Product> order(@RequestParam(required = false) String orderType) {
        return productMapper.searchWithOrder(orderType);
    }
}

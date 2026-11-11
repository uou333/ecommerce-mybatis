package com.ecommerce;

import com.ecommerce.entity.Product;
import com.ecommerce.mapper.ProductMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 商品 Mapper 功能测试：逐一验证 CRUD、主键回显、动态条件查询与批量操作。
 * 运行前请先在 MySQL 中执行 sql/init.sql 准备好库表与测试数据。
 *
 * @author 实验小组
 */
@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    /** 测试新增商品并验证自增主键回显 */
    @Test
    void testInsertAndSelectById() {
        Product product = new Product();
        product.setName("测试无线耳机");
        product.setPrice(new BigDecimal("299.00"));
        product.setStock(100);
        product.setCategoryId(1L);
        product.setImageUrl("https://example.com/headset.jpg");
        product.setDescription("降噪无线耳机");
        product.setStatus(1);
        product.setCreateTime(LocalDateTime.now());

        int rows = productMapper.insert(product);
        Assertions.assertEquals(1, rows);
        // 主键应被 useGeneratedKeys 回填
        Assertions.assertNotNull(product.getId());

        Product db = productMapper.selectById(product.getId());
        Assertions.assertNotNull(db);
        Assertions.assertEquals("测试无线耳机", db.getName());
    }

    /** 测试查询全部上架商品 */
    @Test
    void testSelectAll() {
        List<Product> list = productMapper.selectAll();
        Assertions.assertNotNull(list);
        list.forEach(p -> Assertions.assertEquals(1, p.getStatus()));
    }

    /** 测试修改商品信息 */
    @Test
    void testUpdate() {
        Product product = new Product();
        product.setName("测试无线耳机-改价");
        product.setPrice(new BigDecimal("259.00"));
        product.setStock(80);
        // 先查一条存在的记录再更新
        List<Product> all = productMapper.selectAll();
        Assertions.assertFalse(all.isEmpty());
        product.setId(all.get(0).getId());
        int rows = productMapper.update(product);
        Assertions.assertEquals(1, rows);
    }

    /** 测试按主键删除 */
    @Test
    void testDeleteById() {
        Product product = new Product();
        product.setName("待删除商品");
        product.setPrice(new BigDecimal("9.90"));
        product.setStock(1);
        product.setStatus(1);
        product.setCreateTime(LocalDateTime.now());
        productMapper.insert(product);

        int rows = productMapper.deleteById(product.getId());
        Assertions.assertEquals(1, rows);
        Assertions.assertNull(productMapper.selectById(product.getId()));
    }

    /** 动态 SQL：无条件查询（应返回全部上架商品） */
    @Test
    void testSearchNoCondition() {
        List<Product> list = productMapper.search(null, null, null, null);
        Assertions.assertNotNull(list);
    }

    /** 动态 SQL：部分条件（仅关键字 + 分类） */
    @Test
    void testSearchPartialCondition() {
        List<Product> list = productMapper.search("手机", 1L, null, null);
        Assertions.assertNotNull(list);
    }

    /** 动态 SQL：全条件（关键字 + 分类 + 价格区间） */
    @Test
    void testSearchAllCondition() {
        List<Product> list = productMapper.search("手机", 1L,
                new BigDecimal("1000"), new BigDecimal("5000"));
        Assertions.assertNotNull(list);
        list.forEach(p -> {
            Assertions.assertEquals(1, p.getStatus());
            Assertions.assertTrue(p.getPrice().compareTo(new BigDecimal("1000")) >= 0);
            Assertions.assertTrue(p.getPrice().compareTo(new BigDecimal("5000")) <= 0);
        });
    }

    /** 选做：批量插入与批量删除 */
    @Test
    void testBatchInsertAndDelete() {
        Product p1 = buildProduct("批量商品1", new BigDecimal("19.90"));
        Product p2 = buildProduct("批量商品2", new BigDecimal("29.90"));
        int rows = productMapper.insertBatch(Arrays.asList(p1, p2));
        Assertions.assertEquals(2, rows);

        int delRows = productMapper.deleteBatchIds(Arrays.asList(p1.getId(), p2.getId()));
        Assertions.assertEquals(2, delRows);
    }

    /** 选做：动态排序切换 */
    @Test
    void testSearchWithOrder() {
        List<Product> priceAsc = productMapper.searchWithOrder("price_asc");
        List<Product> priceDesc = productMapper.searchWithOrder("price_desc");
        List<Product> defaultOrder = productMapper.searchWithOrder(null);
        Assertions.assertNotNull(priceAsc);
        Assertions.assertNotNull(priceDesc);
        Assertions.assertNotNull(defaultOrder);
    }

    private Product buildProduct(String name, BigDecimal price) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setStock(50);
        p.setCategoryId(2L);
        p.setStatus(1);
        p.setCreateTime(LocalDateTime.now());
        return p;
    }
}

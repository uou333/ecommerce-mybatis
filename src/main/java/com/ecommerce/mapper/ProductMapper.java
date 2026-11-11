package com.ecommerce.mapper;

import com.ecommerce.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品 Mapper 接口。
 * 接口方法名与 ProductMapper.xml 中 SQL 语句的 id 一一对应，
 * XML 的 namespace 必须与本接口全限定名一致。
 *
 * @author 实验小组
 */
public interface ProductMapper {

    /**
     * 新增商品，使用 useGeneratedKeys 将自增主键回填到实体对象中
     *
     * @param product 商品对象
     * @return 影响行数
     */
    int insert(Product product);

    /**
     * 按主键查询商品
     *
     * @param id 商品主键
     * @return 商品对象，不存在返回 null
     */
    Product selectById(@Param("id") Long id);

    /**
     * 查询全部上架商品（status = 1）
     *
     * @return 商品列表
     */
    List<Product> selectAll();

    /**
     * 修改商品信息（按主键更新非空字段）
     *
     * @param product 商品对象
     * @return 影响行数
     */
    int update(Product product);

    /**
     * 按主键删除商品
     *
     * @param id 商品主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 动态条件多条件查询（核心任务）：
     * 关键字、分类、价格区间均为可选条件，任意组合传入。
     *
     * @param keyword    商品名称模糊关键字（可空）
     * @param categoryId 分类 ID（可空）
     * @param minPrice   最低价格（可空）
     * @param maxPrice   最高价格（可空）
     * @return 符合条件的上架商品列表，按创建时间倒序
     */
    List<Product> search(@Param("keyword") String keyword,
                         @Param("categoryId") Long categoryId,
                         @Param("minPrice") BigDecimal minPrice,
                         @Param("maxPrice") BigDecimal maxPrice);

    /**
     * 【选做拓展】批量插入商品
     *
     * @param products 商品列表
     * @return 影响行数
     */
    int insertBatch(List<Product> products);

    /**
     * 【选做拓展】按主键集合批量删除商品
     *
     * @param ids 主键集合
     * @return 影响行数
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    /**
     * 【选做拓展】动态排序查询：
     * 根据 orderType 选择按价格升序 / 价格降序 / 创建时间排序。
     *
     * @param orderType 排序方式：price_asc 价格升序；price_desc 价格降序；其他值默认按创建时间倒序
     * @return 商品列表
     */
    List<Product> searchWithOrder(@Param("orderType") String orderType);
}

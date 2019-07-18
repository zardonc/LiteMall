package dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 类目
 * 表名product_category
 */
@Entity
@DynamicUpdate // 仅更新有变化的字段
@Data // lombok生成getter,setter,tostring
public class ProductCategory {
    // 类目id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    // 类目名字
    private String categoryName;
    // 类目编号
    private Integer categoryType;
    // 时间属性交由数据库维护
    @Column(name = "create_time",insertable = false, updatable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Column(name = "create_time",insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updateTime;
}

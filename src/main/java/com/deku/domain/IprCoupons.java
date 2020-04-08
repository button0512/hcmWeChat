package com.deku.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author EDZ
 */
@Data
@NoArgsConstructor
@Table(name="ipr_coupons")
public class IprCoupons {
    @Column(name = "id")
    private int id;
    @Column(name = "bmcid")
    private BigDecimal bmcid;
    @Column(name = "coupons_type")
    private String couponsType;
    @Column(name = "access_type")
    private String accessType;
    @Column(name = "item_id")
    private String itemId;
    @Column(name = "full_price")
    private Double fullPrice;
    @Column(name = "reduction_price")
    private Double reductionPrice;
    @Column(name = "privilege")
    private String privilege;
    @Column(name = "createtime")
    private String createtime;
    @Column(name = "updatetime")
    private String updatetime;
}

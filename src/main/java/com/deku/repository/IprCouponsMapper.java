package com.deku.repository;
import com.deku.domain.IprCoupons;
import com.github.support.mybatis.mapper.ICrudMapper;
import com.github.support.mybatis.mapper.ICrudPaginationMapper;
import com.github.support.mybatis.pagination.Page;
import com.github.support.mybatis.provider.CrudProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IprCouponsMapper extends ICrudPaginationMapper<IprCoupons> {


    @Select("select  * from  ipr_coupons")
    List<IprCoupons> selectAll();
}

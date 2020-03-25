package com.yuan.redis.dao;


import com.yuan.redis.entity.CurdTemplate;
import com.yuan.redis.service.GenericEntity;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author yuan
 * @Date 2020/3/25 22:24
 * @Version 1.0
 */
public interface GenericDao<T extends GenericEntity, PK extends Serializable> {
    @InsertProvider(
            type = CurdTemplate.class,
            method = "getInsertSql"
    )
    Long insertEntity(T var1);

    @UpdateProvider(
            type = CurdTemplate.class,
            method = "getUpdateSql"
    )
    Long updateEntity(T var1);

    @DeleteProvider(
            type = CurdTemplate.class,
            method = "getDeleteSql"
    )
    Long deleteEntity(T var1);

    Long insert(T var1);

    Long update(T var1);

    Long delete(PK var1);

    Long deleteAll(Map<String, Object> var1);

    Long count();

    Long count(Map<String, Object> var1);

    T find(PK var1);

    List<T> findAll();

    List<T> findByParams(Map<String, Object> var1);


}

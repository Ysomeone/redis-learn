package com.yuan.redis.entity;

import com.yuan.redis.authorization.Column;
import com.yuan.redis.authorization.Table;
import com.yuan.redis.service.GenericEntity;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author yuan
 * @Date 2020/3/25 22:55
 * @Version 1.0
 */
public class CurdTemplate<T extends GenericEntity> {
    public static transient Logger log = LoggerFactory.getLogger(CurdTemplate.class);
    protected static final transient String insertable = "insertable";
    protected static final transient String updateable = "updateable";
    protected static transient Map<Class<? extends GenericEntity>, LinkedHashMap<Object, Object>> idColumnMap = new HashMap();
    protected static transient Map<Class<? extends GenericEntity>, LinkedHashMap<Object, Object>> columnDefineMap = new HashMap();

    public CurdTemplate() {
    }

    public String getInsertSql(T entity) {
        StringBuilder dbColumn = new StringBuilder();
        StringBuilder propertyColumn = new StringBuilder();
        LinkedHashMap<Object, Object> columnMap = this.getColumnMap(entity, "insertable");
        int i = 0;
        Iterator var7 = columnMap.entrySet().iterator();

        while (var7.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry) var7.next();
            if (i++ != 0) {
                dbColumn.append(',');
                propertyColumn.append(',');
            }

            propertyColumn.append("#{").append(entry.getKey()).append("}");
            dbColumn.append(entry.getValue());
        }

        return ((SQL) ((SQL) (new SQL()).INSERT_INTO(this.tablename(entity))).VALUES(String.valueOf(dbColumn), String.valueOf(propertyColumn))).toString();
    }

    public String getUpdateSql(T entity) {
        StringBuilder setSql = new StringBuilder();
        StringBuilder whereSql = new StringBuilder();
        LinkedHashMap<Object, Object> idMap = this.getIdColumnMap(entity, "updateable");
        LinkedHashMap<Object, Object> columnMap = this.getColumnMap(entity, "updateable");
        if (columnMap != null && !columnMap.isEmpty()) {
            int i = 0;
            Iterator var8 = columnMap.entrySet().iterator();

            while (var8.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry) var8.next();
                String key = String.valueOf(entry.getKey());
                if (idMap.get(key) == null) {
                    if (i++ != 0) {
                        setSql.append(',');
                    }

                    setSql.append(String.valueOf(entry.getValue())).append("=#{").append(key).append('}');
                }
            }
        }

        if (idMap != null && !idMap.isEmpty()) {
            int j = 0;

            String key;
            Map.Entry entry;
            for (Iterator var13 = idMap.entrySet().iterator(); var13.hasNext(); whereSql.append(String.valueOf(entry.getValue())).append("=#{").append(key).append('}')) {
                entry = (Map.Entry) var13.next();
                key = String.valueOf(entry.getKey());
                if (j++ != 0) {
                    whereSql.append(" AND ");
                }
            }
        }

        return ((SQL) ((SQL) ((SQL) (new SQL()).UPDATE(this.tablename(entity))).SET(String.valueOf(setSql))).WHERE(String.valueOf(whereSql))).toString();
    }

    public String getDeleteSql(T entity) {
        StringBuilder whereSql = new StringBuilder();
        LinkedHashMap<Object, Object> idMap = this.getIdColumnMap(entity, "insertable");
        if (idMap != null && !idMap.isEmpty()) {
            int j = 0;

            Map.Entry entry;
            String key;
            for (Iterator var6 = idMap.entrySet().iterator(); var6.hasNext(); whereSql.append(String.valueOf(entry.getValue())).append("=#{").append(key).append('}')) {
                entry = (Map.Entry) var6.next();
                key = String.valueOf(entry.getKey());
                if (j++ != 0) {
                    whereSql.append(" AND ");
                }
            }
        }

        return ((SQL) ((SQL) (new SQL()).DELETE_FROM(this.tablename(entity))).WHERE(String.valueOf(whereSql))).toString();
    }

    protected String tablename(T entity) {
        Table table = (Table) entity.getClass().getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        } else {
//            log.error((new FrameworkException("undefine POJO @Table, need name(@Table(name)) not setting table name")).toString());
//            throw new FrameworkException("undefine POJO @Table, need name(@Table(name)) not setting table name");
            return null;
        }
    }

    protected LinkedHashMap<Object, Object> getIdColumnMap(T entity, String insertOrUpdate) {
        if (idColumnMap != null && !idColumnMap.isEmpty() && idColumnMap.get(entity.getClass()) != null) {
            return (LinkedHashMap) idColumnMap.get(entity.getClass());
        } else {
            LinkedHashMap<Object, Object> idMap = new LinkedHashMap();
            List<Field> fields = this.getAnnotationFieldLst(entity);
            if (fields != null && !fields.isEmpty()) {
                Iterator var6 = fields.iterator();

                while (var6.hasNext()) {
                    Field field = (Field) var6.next();
                    Column column = (Column) field.getAnnotation(Column.class);
                    if ("insertable".equals(insertOrUpdate)) {
                        if (field.isAnnotationPresent(Column.class) && column.id() && column.insertable()) {
                            idMap.put(field.getName(), column.name());
                        }
                    } else if ("updateable".equals(insertOrUpdate) && field.isAnnotationPresent(Column.class) && column.id() && column.updatable()) {
                        idMap.put(field.getName(), column.name());
                    }
                }
            }

            idColumnMap.put(entity.getClass(), idMap);
            return idMap;
        }
    }

    protected LinkedHashMap<Object, Object> getColumnMap(T entity, String insertOrUpdate) {
        if (columnDefineMap != null && !columnDefineMap.isEmpty() && columnDefineMap.get(entity.getClass()) != null) {
            return (LinkedHashMap) columnDefineMap.get(entity.getClass());
        } else {
            LinkedHashMap<Object, Object> columnMap = new LinkedHashMap();
            List<Field> fields = this.getAnnotationFieldLst(entity);
            if (fields != null && !fields.isEmpty()) {
                Iterator var6 = fields.iterator();

                while (var6.hasNext()) {
                    Field field = (Field) var6.next();
                    Column column = (Column) field.getAnnotation(Column.class);
                    if ("insertable".equals(insertOrUpdate)) {
                        if (field.isAnnotationPresent(Column.class) && column.insertable()) {
                            columnMap.put(field.getName(), column.name());
                        }
                    } else if ("updateable".equals(insertOrUpdate)) {
                        if (field.isAnnotationPresent(Column.class) && column.updatable()) {
                            columnMap.put(field.getName(), column.name());
                        }
                    } else {
                        columnMap.put(field.getName(), column.name());
                    }
                }
            }

            columnDefineMap.put(entity.getClass(), columnMap);
            return columnMap;
        }
    }

    private <E> List<Field> getAnnotationFieldLst(T entity) {
        List<Field> list = new ArrayList();

        Field[] objFields;
        Field field;
        int var6;
        int var7;
        Field[] var8;
        for (Class superClass = entity.getClass().getSuperclass(); superClass != null; superClass = superClass.getSuperclass()) {
            objFields = superClass.getDeclaredFields();
            if (objFields != null && objFields.length > 0) {
                var8 = objFields;
                var7 = objFields.length;

                for (var6 = 0; var6 < var7; ++var6) {
                    field = var8[var6];
                    if (field.isAnnotationPresent(Column.class)) {
                        list.add(field);
                    }
                }
            }
        }

        objFields = entity.getClass().getDeclaredFields();
        if (objFields != null && objFields.length > 0) {
            var8 = objFields;
            var7 = objFields.length;

            for (var6 = 0; var6 < var7; ++var6) {
                field = var8[var6];
                if (field.isAnnotationPresent(Column.class)) {
                    list.add(field);
                }
            }
        }

        return list;
    }
}

package org.rdlinux.ezmybatis.core.content;

import org.apache.commons.lang3.StringUtils;
import org.rdlinux.ezmybatis.core.utils.Assert;
import org.rdlinux.ezmybatis.core.utils.HumpLineStringUtils;
import org.rdlinux.ezmybatis.core.utils.SqlReflectionUtils;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityClassInfo {
    private Class<?> entityClass;
    private String tableName;
    private List<EntityFieldInfo> fieldInfos;
    private Map<String, EntityFieldInfo> columnMapFieldInfo;
    private EntityFieldInfo primaryKeyInfo;

    public EntityClassInfo(Class<?> entityClass, boolean isMapUnderscoreToCamelCase) {
        Assert.notNull(entityClass);
        this.tableName = HumpLineStringUtils.humpToLine(entityClass.getSimpleName());
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table annotation = entityClass.getAnnotation(Table.class);
            String tn = annotation.name();
            if (StringUtils.isNotEmpty(tn)) {
                this.tableName = tn;
            }
        }
        this.entityClass = entityClass;
        this.fieldInfos = new LinkedList<>();
        this.columnMapFieldInfo = new HashMap<>((int) (this.fieldInfos.size() / 0.75) + 1);
        List<Field> fields = SqlReflectionUtils.getSupportFields(entityClass);
        fields.forEach(field -> {
            EntityFieldInfo fieldInfo = new EntityFieldInfo(field, isMapUnderscoreToCamelCase);
            this.fieldInfos.add(fieldInfo);
            if (fieldInfo.isPrimaryKey()) {
                this.primaryKeyInfo = fieldInfo;
            }
        });
        this.fieldInfos.forEach(fieldInfo -> this.columnMapFieldInfo.put(fieldInfo.getColumnName(), fieldInfo));
    }

    public Class<?> getEntityClass() {
        return this.entityClass;
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<EntityFieldInfo> getFieldInfos() {
        return this.fieldInfos;
    }

    public Map<String, EntityFieldInfo> getColumnMapFieldInfo() {
        return this.columnMapFieldInfo;
    }

    public EntityFieldInfo getPrimaryKeyInfo() {
        return this.primaryKeyInfo;
    }

}
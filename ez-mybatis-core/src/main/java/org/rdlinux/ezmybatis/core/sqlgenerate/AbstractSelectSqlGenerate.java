package org.rdlinux.ezmybatis.core.sqlgenerate;

import org.apache.ibatis.session.Configuration;
import org.rdlinux.ezmybatis.core.content.EntityClassInfo;
import org.rdlinux.ezmybatis.core.content.EzEntityClassInfoFactory;
import org.rdlinux.ezmybatis.core.utils.Assert;

import java.util.List;

public abstract class AbstractSelectSqlGenerate implements SelectSqlGenerate {
    /**
     * 获取关键字引号
     */
    protected abstract String getKeywordQM();

    @Override
    public String getSelectByIdSql(Configuration configuration, Class<?> ntClass, Object id) {
        Assert.notNull(id, "id can not be null");
        EntityClassInfo entityClassInfo = EzEntityClassInfoFactory.forClass(ntClass,
                configuration.isMapUnderscoreToCamelCase());
        String table = entityClassInfo.getTableName();
        String idColumn = entityClassInfo.getPrimaryKeyInfo().getColumnName();
        String escape = MybatisParamEscape.getEscapeChar(id);
        String kwQM = this.getKeywordQM();
        return "SELECT * FROM " + kwQM + table + kwQM + " WHERE " + kwQM + idColumn + kwQM + " = " + escape + "{id}";
    }

    @Override
    public String getSelectByIdsSql(Configuration configuration, Class<?> ntClass, List<?> ids) {
        Assert.notEmpty(ids, "ids can not be null");
        EntityClassInfo entityClassInfo = EzEntityClassInfoFactory.forClass(ntClass,
                configuration.isMapUnderscoreToCamelCase());
        String table = entityClassInfo.getTableName();
        String idColumn = entityClassInfo.getPrimaryKeyInfo().getColumnName();
        String kwQM = this.getKeywordQM();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM " + kwQM + table + kwQM + " WHERE " + kwQM +
                idColumn + kwQM + " IN ( ");
        for (int i = 0; i < ids.size(); i++) {
            Object id = ids.get(i);
            Assert.notNull(id, "id can not be null");
            String escape = MybatisParamEscape.getEscapeChar(id);
            sqlBuilder.append(escape).append("{ids[").append(i).append("]}");
            if (i + 1 != ids.size()) {
                sqlBuilder.append(", ");
            }
        }
        sqlBuilder.append(" )");
        return sqlBuilder.toString();
    }
}
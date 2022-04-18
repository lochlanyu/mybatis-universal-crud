package org.rdlinux.ezmybatis.core.sqlgenerate;

import org.apache.ibatis.session.Configuration;
import org.rdlinux.ezmybatis.core.EzUpdate;

import java.util.Collection;

public interface UpdateSqlGenerate {
    String getUpdateSql(Configuration configuration, MybatisParamHolder mybatisParamHolder, Object entity,
                        boolean isReplace);

    String getBatchUpdateSql(Configuration configuration, MybatisParamHolder mybatisParamHolder,
                             Collection<Object> entitys, boolean isReplace);

    String getUpdateSql(Configuration configuration, MybatisParamHolder mybatisParamHolder, EzUpdate update);

    String getUpdateSql(Configuration configuration, MybatisParamHolder mybatisParamHolder,
                        Collection<EzUpdate> updates);
}

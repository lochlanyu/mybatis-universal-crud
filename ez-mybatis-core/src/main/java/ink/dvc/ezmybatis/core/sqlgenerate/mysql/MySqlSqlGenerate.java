package ink.dvc.ezmybatis.core.sqlgenerate.mysql;

import org.apache.ibatis.session.Configuration;
import ink.dvc.ezmybatis.core.EzDelete;
import ink.dvc.ezmybatis.core.EzQuery;
import ink.dvc.ezmybatis.core.EzUpdate;
import ink.dvc.ezmybatis.core.sqlgenerate.SqlGenerate;

import java.util.List;
import java.util.Map;

public class MySqlSqlGenerate implements SqlGenerate {
    private static volatile MySqlSqlGenerate instance;

    private MySqlSqlGenerate() {
    }

    public static MySqlSqlGenerate getInstance() {
        if (instance == null) {
            synchronized (MySqlSqlGenerate.class) {
                if (instance == null) {
                    instance = new MySqlSqlGenerate();
                }
            }
        }
        return instance;
    }


    @Override
    public String getInsertSql(Configuration configuration, Object entity) {
        return MySqlInsertSqlGenerate.getInstance().getInsertSql(configuration, entity);
    }

    @Override
    public String getBatchInsertSql(Configuration configuration, List<Object> entitys) {
        return MySqlInsertSqlGenerate.getInstance().getBatchInsertSql(configuration, entitys);
    }

    @Override
    public String getSelectByIdSql(Configuration configuration, Class<?> ntClass, Object id) {
        return MySqlSelectSqlGenerate.getInstance().getSelectByIdSql(configuration, ntClass, id);
    }

    @Override
    public String getSelectByIdsSql(Configuration configuration, Class<?> ntClass, List<?> ids) {
        return MySqlSelectSqlGenerate.getInstance().getSelectByIdsSql(configuration, ntClass, ids);
    }

    @Override
    public String getQuerySql(Configuration configuration, EzQuery<?> query, Map<String, Object> mybatisParam) {
        return MySqlSelectSqlGenerate.getInstance().getQuerySql(configuration, query, mybatisParam);
    }

    @Override
    public String getQueryCountSql(Configuration configuration, EzQuery<?> query, Map<String, Object> mybatisParam) {
        return MySqlSelectSqlGenerate.getInstance().getQueryCountSql(configuration, query, mybatisParam);
    }

    @Override
    public String getUpdateSql(Configuration configuration, Object entity, boolean isReplace) {
        return MySqlUpdateSqlGenerate.getInstance().getUpdateSql(configuration, entity, isReplace);
    }

    @Override
    public String getBatchUpdateSql(Configuration configuration, List<Object> entitys, boolean isReplace) {
        return MySqlUpdateSqlGenerate.getInstance().getBatchUpdateSql(configuration, entitys, isReplace);
    }

    @Override
    public String getUpdateSql(Configuration configuration, EzUpdate update, Map<String, Object> mybatisParam) {
        return MySqlUpdateSqlGenerate.getInstance().getUpdateSql(configuration, update, mybatisParam);
    }

    @Override
    public String getUpdateSql(Configuration configuration, List<EzUpdate> updates, Map<String, Object> mybatisParam) {
        return MySqlUpdateSqlGenerate.getInstance().getUpdateSql(configuration, updates, mybatisParam);
    }

    @Override
    public String getDeleteByIdSql(Configuration configuration, Class<?> ntClass, Object id) {
        return MySqlDeleteSqlGenerate.getInstance().getDeleteByIdSql(configuration, ntClass, id);
    }

    @Override
    public String getBatchDeleteByIdSql(Configuration configuration, Class<?> ntClass, List<?> ids) {
        return MySqlDeleteSqlGenerate.getInstance().getBatchDeleteByIdSql(configuration, ntClass, ids);
    }

    @Override
    public String getDeleteSql(Configuration configuration, EzDelete delete, Map<String, Object> mybatisParam) {
        return MySqlDeleteSqlGenerate.getInstance().getDeleteSql(configuration, delete, mybatisParam);
    }

    @Override
    public String getDeleteSql(Configuration configuration, List<EzDelete> deletes, Map<String, Object> mybatisParam) {
        return MySqlDeleteSqlGenerate.getInstance().getDeleteSql(configuration, deletes, mybatisParam);
    }
}
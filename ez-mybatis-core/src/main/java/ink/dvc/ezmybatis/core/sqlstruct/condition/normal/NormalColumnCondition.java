package ink.dvc.ezmybatis.core.sqlstruct.condition.normal;

import ink.dvc.ezmybatis.core.sqlgenerate.DbKeywordQMFactory;
import ink.dvc.ezmybatis.core.sqlstruct.condition.Operator;
import ink.dvc.ezmybatis.core.sqlstruct.table.EntityTable;
import ink.dvc.ezmybatis.core.utils.DbTypeUtils;
import lombok.Getter;
import org.apache.ibatis.session.Configuration;

/**
 * 普通列条件
 */
@Getter
public class NormalColumnCondition extends NormalCondition {
    private EntityTable table;
    private String column;

    public NormalColumnCondition(LoginSymbol loginSymbol, EntityTable table, String column, Operator operator,
                                 Object value) {
        super(loginSymbol, operator, value);
        this.table = table;
        this.column = column;
    }

    @Override
    protected String getSqlField(Configuration configuration) {
        String keywordQM = DbKeywordQMFactory.getKeywordQM(DbTypeUtils.getDbType(configuration));
        return this.getTable().getAlias() + "." + keywordQM + this.column + keywordQM;
    }
}

package ink.dvc.ezmybatis.core.sqlstruct.condition.between;

import ink.dvc.ezmybatis.core.sqlgenerate.DbKeywordQMFactory;
import ink.dvc.ezmybatis.core.utils.DbTypeUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.Configuration;

/**
 * between 条件
 */
public class BetweenAliasCondition extends BetweenCondition {
    @Getter
    @Setter
    protected String alias;

    public BetweenAliasCondition(LoginSymbol loginSymbol, String alias, Object minValue, Object maxValue) {
        super(loginSymbol, minValue, maxValue);
        this.alias = alias;
    }

    @Override
    protected String getSqlField(Configuration configuration) {
        String keywordQM = DbKeywordQMFactory.getKeywordQM(DbTypeUtils.getDbType(configuration));
        return " " + keywordQM + this.alias + keywordQM + " ";
    }
}

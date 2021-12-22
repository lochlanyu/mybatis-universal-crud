package ink.dvc.ezmybatis.core.sqlstruct.selectitem;

import ink.dvc.ezmybatis.core.sqlgenerate.DbKeywordQMFactory;
import ink.dvc.ezmybatis.core.sqlstruct.table.Table;
import ink.dvc.ezmybatis.core.utils.Assert;
import ink.dvc.ezmybatis.core.utils.DbTypeUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.Configuration;

@Getter
@Setter
public class SelectColumn extends AbstractSelectItem {
    protected Table table;
    protected String column;

    public SelectColumn(Table table, String column) {
        Assert.notNull(table, "table can not be null");
        Assert.notNull(column, "column can not be null");
        this.table = table;
        this.column = column;
    }

    public SelectColumn(Table table, String column, String alias) {
        this(table, column);
        this.setAlias(alias);
    }

    @Override
    public String toSqlPart(Configuration configuration) {
        String keywordQM = DbKeywordQMFactory.getKeywordQM(DbTypeUtils.getDbType(configuration));
        String sql = " " + this.table.getAlias() + "." + this.column + " ";
        String alias = this.getAlias();
        if (alias != null && !alias.isEmpty()) {
            sql = sql + keywordQM + alias + keywordQM + " ";
        }
        return sql;
    }
}

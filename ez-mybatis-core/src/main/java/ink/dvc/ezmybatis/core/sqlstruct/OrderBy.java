package ink.dvc.ezmybatis.core.sqlstruct;

import ink.dvc.ezmybatis.core.EzParam;
import ink.dvc.ezmybatis.core.EzQuery;
import ink.dvc.ezmybatis.core.constant.DbType;
import ink.dvc.ezmybatis.core.content.EzEntityClassInfoFactory;
import ink.dvc.ezmybatis.core.content.entityinfo.EntityClassInfo;
import ink.dvc.ezmybatis.core.sqlgenerate.DbKeywordQMFactory;
import ink.dvc.ezmybatis.core.sqlgenerate.MybatisParamHolder;
import ink.dvc.ezmybatis.core.sqlstruct.table.EntityTable;
import ink.dvc.ezmybatis.core.utils.DbTypeUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrderBy implements SqlStruct {
    private static final Map<DbType, SqlStruct> CONVERT = new HashMap<>();

    static {
        SqlStruct defaultConvert = (sqlBuilder, configuration, ezParam, mybatisParamHolder) ->
                OrderBy.defaultOrderByToSql(sqlBuilder, configuration, (EzQuery<?>) ezParam, mybatisParamHolder);
        CONVERT.put(DbType.MYSQL, defaultConvert);
        CONVERT.put(DbType.ORACLE, defaultConvert);
        CONVERT.put(DbType.DM, defaultConvert);
    }

    private List<OrderItem> items;

    public OrderBy(List<OrderItem> items) {
        this.items = items;
    }

    private static StringBuilder defaultOrderByToSql(StringBuilder sqlBuilder, Configuration configuration,
                                                     EzQuery<?> ezParam, MybatisParamHolder mybatisParamHolder) {
        OrderBy order = ezParam.getOrderBy();
        if (order == null || order.getItems() == null) {
            return sqlBuilder;
        } else {
            String keywordQM = DbKeywordQMFactory.getKeywordQM(DbTypeUtils.getDbType(configuration));
            StringBuilder sql = new StringBuilder(" ORDER BY ");
            for (int i = 0; i < order.getItems().size(); i++) {
                OrderBy.OrderItem orderItem = order.getItems().get(i);
                EntityClassInfo entityClassInfo = EzEntityClassInfoFactory.forClass(configuration,
                        orderItem.getTable().getEtType());
                sql.append(orderItem.getTable().getAlias()).append(".")
                        .append(keywordQM)
                        .append(entityClassInfo.getFieldInfo(orderItem.getField()).getColumnName())
                        .append(keywordQM)
                        .append(" ").append(orderItem.getType().name());
                if (i + 1 < order.getItems().size()) {
                    sql.append(", ");
                } else {
                    sql.append(" ");
                }
            }
            return sqlBuilder.append(sql);
        }
    }

    @Override
    public StringBuilder toSqlPart(StringBuilder sqlBuilder, Configuration configuration, EzParam<?> ezParam,
                                   MybatisParamHolder mybatisParamHolder) {
        return CONVERT.get(DbTypeUtils.getDbType(configuration)).toSqlPart(sqlBuilder, configuration, ezParam,
                mybatisParamHolder);
    }

    /**
     * 排序类型
     */
    public static enum OrderType {
        DESC,
        ASC;
    }

    @Getter
    public static class OrderItem {
        private EntityTable table;
        private String field;
        private OrderType type;

        public OrderItem(EntityTable table, String field, OrderType type) {
            this.table = table;
            this.field = field;
            this.type = type;
        }

        public OrderItem(EntityTable table, String field) {
            this.table = table;
            this.field = field;
            this.type = OrderType.ASC;
        }
    }

    public static class OrderBuilder<T> {
        private T target;
        private EntityTable table;
        private OrderBy orderBy;

        public OrderBuilder(T target, OrderBy orderBy, EntityTable table) {
            this.target = target;
            this.orderBy = orderBy;
            this.table = table;
        }

        public OrderBuilder<T> add(String field) {
            this.orderBy.getItems().add(new OrderBy.OrderItem(this.table, field));
            return this;
        }

        public OrderBuilder<T> add(boolean sure, String field) {
            if (sure) {
                this.add(field);
            }
            return this;
        }

        public OrderBuilder<T> add(String field, OrderBy.OrderType type) {
            this.orderBy.getItems().add(new OrderBy.OrderItem(this.table, field, type));
            return this;
        }

        public OrderBuilder<T> add(boolean sure, String field, OrderBy.OrderType type) {
            if (sure) {
                this.add(field, type);
            }
            return this;
        }

        public OrderBuilder<T> add(EntityTable table, String field) {
            this.orderBy.getItems().add(new OrderBy.OrderItem(table, field));
            return this;
        }

        public OrderBuilder<T> add(boolean sure, EntityTable table, String field) {
            if (sure) {
                this.add(table, field);
            }
            return this;
        }

        public OrderBuilder<T> add(EntityTable table, String field, OrderBy.OrderType type) {
            this.orderBy.getItems().add(new OrderBy.OrderItem(table, field, type));
            return this;
        }

        public OrderBuilder<T> add(boolean sure, EntityTable table, String field, OrderBy.OrderType type) {
            if (sure) {
                this.add(table, field, type);
            }
            return this;
        }

        public T done() {
            return this.target;
        }
    }
}
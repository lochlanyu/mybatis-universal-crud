package ink.dvc.ezmybatis.core.sqlstruct.group;

import org.apache.ibatis.session.Configuration;

public interface GroupItem {
    String toSqlStruct(Configuration configuration);
}

package org.rdlinux.ezmybatis.core.sqlstruct.update;

import org.rdlinux.ezmybatis.core.sqlstruct.table.EntityTable;
import org.rdlinux.ezmybatis.utils.Assert;

public class KeywordsUpdateFieldItem extends UpdateItem {
    private String field;
    private String keywords;

    public KeywordsUpdateFieldItem(EntityTable table, String field, String keywords) {
        super(table);
        Assert.notEmpty(field, "column can not be null");
        Assert.notNull(keywords, "keywords can not be null");
        this.field = field;
        this.keywords = keywords;
    }

    public String getField() {
        return this.field;
    }

    public String getKeywords() {
        return this.keywords;
    }
}
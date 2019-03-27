
package com.exch.platform.core.common.constant.dictmap;

import com.exch.platform.core.common.constant.dictmap.base.AbstractDictMap;
import com.exch.platform.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * 字典map
 *
 */
public class DictMap extends AbstractDictMap {

    @Override
    public void init() {
        put("dictId", "字典名称");
        put("dictName", "字典名称");
        put("dictValues", "字典内容");
    }

    @Override
    protected void initBeWrapped() {

    }
}

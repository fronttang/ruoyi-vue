package com.ruoyi.electrical.type;

import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.alibaba.fastjson2.TypeReference;

@MappedJdbcTypes({ JdbcType.LONGVARCHAR, JdbcType.NULL })
@MappedTypes({ List.class })
public class ListLongTypeHandler extends ListTypeHandler<Long> {

	@Override
	protected TypeReference<List<Long>> specificType() {
		return new TypeReference<List<Long>>() {
		};
	}

}

package com.ruoyi.electrical.type;

import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.alibaba.fastjson2.TypeReference;

@MappedJdbcTypes({ JdbcType.LONGVARCHAR, JdbcType.NULL })
@MappedTypes({ List.class })
public class ListStringTypeHandler extends ListTypeHandler<String> {

	@Override
	protected TypeReference<List<String>> specificType() {
		return new TypeReference<List<String>>() {
		};
	}

}

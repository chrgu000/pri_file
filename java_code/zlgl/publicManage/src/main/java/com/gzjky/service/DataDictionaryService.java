package com.gzjky.service;

import com.gzjky.bean.Message;

public interface DataDictionaryService {
	public Message getDataDictionaryByParentId(Long parentId);
}

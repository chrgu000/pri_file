package com.gzjky.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjky.bean.Message;
import com.gzjky.repository.SysChilddictionariesRepository;
import com.gzjky.service.DataDictionaryService;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService{
	
	@Autowired
	private SysChilddictionariesRepository sysChilddictionariesRepository;

	@Override
	public Message getDataDictionaryByParentId(Long parentId) {
		if (parentId == null || parentId < 1) {
			return new Message(400, "10", "参数错误");
		}
		return new Message(200, "1", sysChilddictionariesRepository.findByMainNo(parentId));
	}

}

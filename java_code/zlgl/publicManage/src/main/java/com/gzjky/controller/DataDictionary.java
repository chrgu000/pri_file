package com.gzjky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gzjky.bean.Message;
import com.gzjky.service.DataDictionaryService;

@RestController
@RequestMapping("/dataDictionary")
public class DataDictionary {
	
	@Autowired
	private DataDictionaryService service;
	
	@RequestMapping("/getDataDictionaryByParentId")
	public Message getDataDictionaryByParentId(Long parentId) {
		return service.getDataDictionaryByParentId(parentId);
	}
}

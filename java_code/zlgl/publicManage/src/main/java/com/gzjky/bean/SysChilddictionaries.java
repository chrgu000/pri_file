package com.gzjky.bean;

import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Data
@Entity(name = "t_sys_childdictionaries")
public class SysChilddictionaries{
	//自增编号
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	//主字典编号
	@Column(name="mainno")
	private Long mainNo;
	//子字典编号
	@Column(name="childno")
	private Long childNo;
	//主字典名称
	@Column(name="childname")
	private String childName;
}

package com.gzjky.bean;

import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Data
@Entity(name = "t_sys_maindictionaries")
public class SysMaindictionaries{
	//自增编号
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	//主字典编号
	@Id
	@Column(name="mainno")
	private Long mainNo;
	//主字典名称
	@Column(name="mainname")
	private String mainName;
}

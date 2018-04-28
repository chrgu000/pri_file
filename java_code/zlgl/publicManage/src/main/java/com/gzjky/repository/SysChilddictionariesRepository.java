package com.gzjky.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gzjky.bean.SysChilddictionaries;

public interface SysChilddictionariesRepository extends JpaRepository<SysChilddictionaries,Long>{
	List<SysChilddictionaries> findByMainNo(Long mainNo);
}

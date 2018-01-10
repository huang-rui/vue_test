package com.syfri.userserver.repository;

import com.syfri.userserver.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole,Long>{
	//@Query("select t from Role r where r.rolename like :name")
	//Page<SysRole> findByRolename(@Param("name") String name,Pageable pageRequest);
}

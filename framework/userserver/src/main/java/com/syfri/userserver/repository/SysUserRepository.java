package com.syfri.userserver.repository;

import com.syfri.userserver.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long>{
	SysUser findByUsername(String name);
}

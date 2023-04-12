package com.donghyukki.multidatasource.infra.jpa.second

import com.donghyukki.multidatasource.domain.entity.second.UserSecurityInfo
import com.donghyukki.multidatasource.domain.repository.UserSecurityInfoRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaUserSecurityInfoRepository : UserSecurityInfoRepository, JpaRepository<UserSecurityInfo, Long>

package com.donghyukki.multidatasource.domain.repository

import com.donghyukki.multidatasource.domain.entity.second.UserSecurityInfo

interface UserSecurityInfoRepository {
    fun save(userSecurityInfo: UserSecurityInfo): UserSecurityInfo
}

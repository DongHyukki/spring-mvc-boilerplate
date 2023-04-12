package com.donghyukki.multidatasource.application

import com.donghyukki.multidatasource.domain.entity.first.User
import com.donghyukki.multidatasource.domain.entity.second.UserSecurityInfo
import com.donghyukki.multidatasource.domain.repository.UserRepository
import com.donghyukki.multidatasource.domain.repository.UserSecurityInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userSecurityInfoRepository: UserSecurityInfoRepository,
) {

    @Transactional(value = "jtaTransactionManager")
    fun save() {
        val user = User("testName")
        val savedUser = userRepository.save(user)
        val userSecurityInfo = UserSecurityInfo(1L, "920000-1223333", "010-1111-2222")
        val savedUserSecurityInfo = userSecurityInfoRepository.save(userSecurityInfo)
    }
}

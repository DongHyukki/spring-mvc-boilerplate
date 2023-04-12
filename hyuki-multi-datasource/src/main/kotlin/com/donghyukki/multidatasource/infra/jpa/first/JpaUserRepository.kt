package com.donghyukki.multidatasource.infra.jpa.first

import com.donghyukki.multidatasource.domain.entity.first.User
import com.donghyukki.multidatasource.domain.repository.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaUserRepository : UserRepository, JpaRepository<User, Long>

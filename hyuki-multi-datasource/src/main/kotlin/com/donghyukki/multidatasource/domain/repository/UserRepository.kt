package com.donghyukki.multidatasource.domain.repository

import com.donghyukki.multidatasource.domain.entity.first.User

interface UserRepository {
    fun save(user: User): User
    fun saveAndFlush(user: User): User
}

package com.donghyukki.multidatasource.domain.entity.second

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class UserSecurityInfo private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val userId: Long,

    val rrn: String,

    val phoneNo: String
) {

    constructor(
        userId: Long,
        rrn: String,
        phoneNo: String
    ) : this(
        id = 0,
        userId = userId,
        rrn = rrn,
        phoneNo = phoneNo
    )
}

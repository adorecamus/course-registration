package com.teamsparta.courseregistration.domain.user.model

import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplication
import jakarta.persistence.*

@Entity
@Table(name = "app_user") // user는 여러 DBMS에서 예약어로 쓰이는 경우가 많아 다른 이름 사용
// 테이블 이름이 클래스 이름과 다르므로 name 꼭 명시해야 함
class User(
    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var courseApplications: MutableList<CourseApplication> = mutableListOf()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
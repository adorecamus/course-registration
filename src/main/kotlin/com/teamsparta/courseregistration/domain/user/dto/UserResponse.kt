package com.teamsparta.courseregistration.domain.user.dto

data class UserResponse(
    val id: String,
    val email: String,
//    val password: String,   // 비밀번호처럼 민감한 정보는 response로 주지 않음. 해커가 볼 수 있음;
    val nickname: String,
    val role: String
)

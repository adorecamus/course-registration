package com.teamsparta.courseregistration.domain.courseapplication.dto

data class ApplyCourseRequest (
    val userId: Long    // 인증 작업이 되어있다면 현재 인증된 사람을 찾아 입력하기 때문에 필요없는 프로퍼티
)

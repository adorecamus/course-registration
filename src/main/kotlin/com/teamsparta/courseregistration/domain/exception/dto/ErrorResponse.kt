package com.teamsparta.courseregistration.domain.exception.dto

data class ErrorResponse(
    val message: String?,

    // 만약 같은 exception이어도 각각 다른 이유를 표기해야 하는 경우
    // 예) 로그인 실패 이유 - 이메일이 존재하지 않아서 / 패스워드가 달라서
    // enum class 를 만들어 에러 코드를 정의할 수 있음
//    val errorCode: ErrorCode
    // enum은 JSON으로 변환이 되지 않기 때문에 별도의 어노테이션(@JvmStatic) 필요
    // 또는 String 으로 반환하고 앞단에서 코드를 관리
)

//enum class ErrorCode {
//    EMAIL_NOT_EXIST,
//    INVALID_PASSWORD
//}
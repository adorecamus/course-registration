package com.teamsparta.courseregistration.domain.course.dto


data class CourseResponse(
// toString(), equals(), hashCode() 등의 메서드를 자동으로 생성해 편리
// 기본적으로 불변적(immutable)이라 DTO에 적합
// componentN() 자동으로 생성 - 구조 분해 선언, 패턴 매칭 기능

    val id: Long,   // dto는 데이터 전달 용도, 값이 바뀌지 않으므로 val로 선언
    val title: String,
    val description: String?, // optional한 프로퍼티는 nullable로 선언
    val status: String,
    val maxApplicants: Int,
    val numApplicants: Int,
)

fun main(args:Array<String>) {
    val courseResponse = CourseResponse(
        id = 1L,
        title = "abc",
        description = null,
        status = "CLOSED",
        maxApplicants = 30,
        numApplicants = 30
    )

    println(courseResponse.id)
    println(courseResponse.title)

    // componentN() 자동 생성
    // 괄호를 통해 변수를 표기하고 바로 사용 가능
    val (id, title) = courseResponse
    println(id)
    println(title)

    // toString() 자동 생성
    println(courseResponse.toString())
    // CourseResponse(id=1, title=abc, description=null, status=CLOSED, maxApplicants=30, numApplicants=30)

    val courseResponse2 = CourseResponse(
        id = 1L,
        title = "abc",
        description = null,
        status = "CLOSED",
        maxApplicants = 30,
        numApplicants = 30
    )

    // equals() 자동 생성
    println(courseResponse == courseResponse2)
    // true
}
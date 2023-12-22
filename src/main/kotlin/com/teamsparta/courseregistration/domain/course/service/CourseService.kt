package com.teamsparta.courseregistration.domain.course.service

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest

// 인터페이스로 함수가 어떤 argument를 받아서 어떤 response를 보내는지 정의
// 구현을 제외하고 인터페이스를 controller에서 사용해 OCP 원칙을 지킬 수 있음
// 하지만 한 service 인터페이스에서 여러 구현체가 필요한 경우가 거의 없음. 보통 1:1 대응.
// 일단 클래스로 작성한 뒤 나중에 인터페이스로 리팩토링해도 됨
interface CourseService {

    fun getCourseList(): List<CourseResponse>

    fun getCourseById(courseId: Long): CourseResponse

    fun createCourse(request: CreateCourseRequest): CourseResponse

    fun updateCourse(courseId: Long, request: UpdateCourseRequest): CourseResponse

    fun deleteCourse(courseId: Long)
}
package com.teamsparta.courseregistration.domain.course.service

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.courseapplication.dto.ApplyCourseRequest
import com.teamsparta.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import com.teamsparta.courseregistration.domain.courseapplication.dto.UpdateApplicationStatusRequest
import com.teamsparta.courseregistration.domain.lecture.dto.AddLectureRequest
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLectureRequest

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

    // 1. LectureService와 CourseApplicationService 를 만들고 CourseService가 이들을 주입받아 사용하거나
    // 2. Application Service와 Domain Service를 나누는 방식으로
    // CourseApplicationService와 LectureDomainService, ApplicationDomainService를 만들고
    // CourseService가 이들을 주입받아 사용할 수 있지만
    // 예시로는 단순화를 위해 CourseService에서 Lecture와 Course Application 에 관련된 Command 모두 구현
    // 이 경우 CourseService가 너무 커진다는 단점이 있음

    fun getLectureList(courseId: Long): List<LectureResponse>

    fun getLecture(courseId: Long, lectureId: Long): LectureResponse

    fun addLecture(courseId: Long, request: AddLectureRequest): LectureResponse

    fun updateLecture(
        courseId: Long,
        lectureId: Long,
        request: UpdateLectureRequest
    ): LectureResponse

    fun removeLecture(courseId: Long, lectureId: Long)

    fun getCourseApplicationList(courseId: Long): List<CourseApplicationResponse>

    fun getCourseApplication(
        courseId: Long,
        applicationId: Long
    ): CourseApplicationResponse

    fun applyCourse(courseId: Long, request: ApplyCourseRequest): CourseApplicationResponse

    fun updateCourseApplicationStatus(
        courseId: Long,
        applicationId: Long,
        request: UpdateApplicationStatusRequest
    ): CourseApplicationResponse
}
package com.teamsparta.courseregistration.domain.course.service

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseServiceImpl: CourseService {
    // control(^)+O 눌러서 메서드 오버라이드

    override fun getCourseList(): List<CourseResponse> {
        // TODO 를 주석에 써주면 커밋할 때 알려줌
        // TODO: DB에서 모든 Course 목록 가져와서 CourseResponse 목록으로 변환 후 반환
        TODO("Not yet implemented")
    }

    override fun getCourseById(courseId: Long): CourseResponse {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO: DB에서 ID 기반으로 Course 가져와서 CourseResponse로 변환 후 반환
//        TODO("Not yet implemented")
        throw ModelNotFoundException("Course", 1L)
    }

    @Transactional
    override fun createCourse(request: CreateCourseRequest): CourseResponse {
        // TODO: request를 Course(Entity)로 변환 후 DB에 저장
        TODO()
    }

    @Transactional
    override fun updateCourse(courseId: Long, request: UpdateCourseRequest): CourseResponse {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO: DB에서 courseId에 해당하는 Course 가져와서 request 내용으로 업데이트 후 DB에 저장, 결과를 CourseResponse로 변환 후 반환
        TODO()
    }

    @Transactional
    override fun deleteCourse(courseId: Long) {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        // TODO: DB에서 courseId에 해당하는 Course 삭제, (Policy에 따라) 연관된 CourseApplication과 Lecture도 모두 삭제
        TODO()
    }
}
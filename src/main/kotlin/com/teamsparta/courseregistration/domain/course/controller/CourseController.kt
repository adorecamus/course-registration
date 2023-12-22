package com.teamsparta.courseregistration.domain.course.controller

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.course.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/courses") // 어떤 url을 담당하는지 알려줌
// 하위의 GetMapping, PostMapping 등은 해당 url 뒤에 매핑됨
// RequestMapiing 없이 HTTP Method Mapping 어노테이션만 사용 가능 (전체 url 표기)

@RestController // data만 리턴하는 Controller
class CourseController(
    private val courseService: CourseService
) {

    @GetMapping
    fun getCourseList(): ResponseEntity<List<CourseResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getCourseList())
    }

//    @GetMapping("/{id}")
//    fun getCourse(@PathVariable(name = "id") courseId: Long) {
//  위와 같이 이름을 알려주는 방식도 있지만

    //  아래처럼 변수와 PathVariable의 이름을 일치시키는 방식을 많이 사용
    @GetMapping("/{courseId}")
    fun getCourse(@PathVariable courseId: Long): ResponseEntity<CourseResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getCourseById(courseId))
    }

    @PostMapping
    fun createCourse(@RequestBody createCourseRequest: CreateCourseRequest)
            : ResponseEntity<CourseResponse>
    //  ResponseEntity<DTO> - status code 등을 함께 넣어 상세한 응답 객체 반환
    {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(courseService.createCourse(createCourseRequest))
    }

    @PutMapping("/{courseId}")
    fun updateCourse(
        @PathVariable courseId: Long,
        @RequestBody updateCourseRequest: UpdateCourseRequest
    ): ResponseEntity<CourseResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateCourse(courseId, updateCourseRequest))
    }

    @DeleteMapping("/{courseId}")
    fun deleteCourse(@PathVariable courseId: Long): ResponseEntity<Unit> {
        courseService.deleteCourse(courseId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build() // body가 없을 경우
    }
}
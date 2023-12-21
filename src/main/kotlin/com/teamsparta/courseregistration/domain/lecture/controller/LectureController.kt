package com.teamsparta.courseregistration.domain.courseapplication.controller

import com.teamsparta.courseregistration.domain.lecture.dto.AddLectureRequest
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLectureRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/courses/{courseId}/lectures")
@RestController
class LectureController {

    @GetMapping()
    fun getLectureList(@PathVariable courseId: Long): ResponseEntity<List<LectureResponse>> {
        TODO()
    }

    @GetMapping("/{lectureId}")
    fun getLecture(@PathVariable courseId: Long, @PathVariable lectureId: Long): ResponseEntity<LectureResponse> {
        TODO()
    }

    @PostMapping
    fun addLecture(
        @PathVariable courseId: Long,
        @RequestBody addLectureRequest: AddLectureRequest
    ): ResponseEntity<LectureResponse> {
        TODO()
    }

    @PutMapping("/{lectureId}")
    fun updateLecture(
        @PathVariable courseId: Long,
        @PathVariable lectureId: Long,
        @RequestBody updateLectureRequest: UpdateLectureRequest
    ): ResponseEntity<LectureResponse> {
        TODO()
    }

    @DeleteMapping("/{lectureId}")
    fun removeLecture(
        @PathVariable courseId: Long,
        @PathVariable lectureId: Long
    ): ResponseEntity<Unit> {
        TODO()
    }

}
package com.teamsparta.courseregistration.domain.course.service

import com.teamsparta.courseregistration.domain.course.dto.CourseResponse
import com.teamsparta.courseregistration.domain.course.dto.CreateCourseRequest
import com.teamsparta.courseregistration.domain.course.dto.UpdateCourseRequest
import com.teamsparta.courseregistration.domain.course.model.Course
import com.teamsparta.courseregistration.domain.course.model.CourseStatus
import com.teamsparta.courseregistration.domain.course.model.toResponse
import com.teamsparta.courseregistration.domain.course.repository.CourseRepository
import com.teamsparta.courseregistration.domain.courseapplication.dto.ApplyCourseRequest
import com.teamsparta.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import com.teamsparta.courseregistration.domain.courseapplication.dto.UpdateApplicationStatusRequest
import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplication
import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplicationStatus
import com.teamsparta.courseregistration.domain.courseapplication.model.toResponse
import com.teamsparta.courseregistration.domain.courseapplication.repository.CourseApplicationRepository
import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.courseregistration.domain.lecture.dto.AddLectureRequest
import com.teamsparta.courseregistration.domain.lecture.dto.LectureResponse
import com.teamsparta.courseregistration.domain.lecture.dto.UpdateLectureRequest
import com.teamsparta.courseregistration.domain.lecture.model.Lecture
import com.teamsparta.courseregistration.domain.lecture.model.toResponse
import com.teamsparta.courseregistration.domain.lecture.repository.LectureRepository
import com.teamsparta.courseregistration.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val lectureRepository: LectureRepository,
    private val userRepository: UserRepository,
    private val courseApplicationRepository: CourseApplicationRepository
): CourseService {
    // control(^)+O 눌러서 메서드 오버라이드

    override fun getCourseList(): List<CourseResponse> {
        // TODO 를 주석에 써주면 커밋할 때 알려줌
        return courseRepository.findAll().map { it.toResponse() }
    }

    override fun getCourseById(courseId: Long): CourseResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
//        val courseResponse = CourseResponse(
//            id = course.id!!,
//            title = course.title,
//            description = course.description,
//            status = course.status.name,
//            maxApplicants = course.maxApplicants,
//            numApplicants = course.numApplicants
//        )
//        return courseResponse
        // Service에서 Entity를 Response로 변환하는 부분은 중복되기 때문에 함수로 정의
        return course.toResponse()
    }

    @Transactional
    override fun createCourse(request: CreateCourseRequest): CourseResponse {
        return courseRepository.save(
            Course(
                title = request.title,
                description = request.description,
                status = CourseStatus.OPEN
            )
        )   // save 하면 id가 담긴 Course 반환
        .toResponse()
    }

    @Transactional
    override fun updateCourse(courseId: Long, request: UpdateCourseRequest): CourseResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        // find하면 persistent context 안에 들어감

        // course.title = request.title
        // course.description = request.description
        val (title, description) = request
        course.title = title
        course.description = description
        // course가 변경되면 dirty checking 발생

        return courseRepository.save(course) // DB에 반영될 때 course의 변경을 감지해 UPDATE 쿼리가 나감
            .toResponse()
    }

    @Transactional
    override fun deleteCourse(courseId: Long) {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        courseRepository.delete(course)
    }

    override fun getLectureList(courseId: Long): List<LectureResponse> {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        return course.lectures.map { it.toResponse() }
    }

    override fun getLecture(courseId: Long, lectureId: Long): LectureResponse {
//        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
//        val lecture = course.lectures.find { it.id == lectureId }
        // 위 방식은 모든 lecture를 가져오고, 이 중 id가 일치하는 lecture를 찾기 때문에 비효율적

        val lecture = lectureRepository.findByCourseIdAndId(courseId, lectureId)
            ?: throw ModelNotFoundException("Lecture", lectureId)

        return lecture.toResponse()
    }

    @Transactional
    override fun addLecture(courseId: Long, request: AddLectureRequest): LectureResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        val lecture = Lecture(
            title = request.title,
            videoUrl = request.videoUrl,
            course = course
        )
//        return lectureRepository.save(lecture).toResponse()

        // aggregate의 루트인 Course를 통해 Lecture의 라이프사이클을 관리하기 위해 아래와 같이 작성

        course.addLecture(lecture) // Course에 Lecture 추가

        courseRepository.save(course) // Lecture에 영속성 전파
        // CascadeType.ALL이라 persist할 때 Course의 자식 Entity인 Lecture에도 영속성 전파됨
        // course에 lecture를 추가해 save해도 lecture가 새로 생기고 영속성 컨텍스트 안에 들어가 DB에서 동기화됨

        return lecture.toResponse()
    }

    @Transactional
    override fun updateLecture(courseId: Long, lectureId: Long, request: UpdateLectureRequest): LectureResponse {
//        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
//        val lecture = lectureRepository.findByIdOrNull(lectureId) ?: throw ModelNotFoundException("Lecture", lectureId)
//        course.updateLecture(lecture)
        // 위 방식은 course의 lecture 중 id 기반으로 동일한 lecture를 찾아야 하므로 비효율적

        val lecture = lectureRepository.findByCourseIdAndId(courseId, lectureId) ?: throw ModelNotFoundException("Lecture", lectureId)

        val (title, videoUrl) = request
        lecture.title = title
        lecture.videoUrl = videoUrl

        return lectureRepository.save(lecture).toResponse()
    }

    @Transactional
    override fun removeLecture(courseId: Long, lectureId: Long) {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val lecture = lectureRepository.findByIdOrNull(lectureId) ?: throw ModelNotFoundException("Lecture", lectureId)
//        lectureRepository.delete(lecture)
        // Lecture의 라이프사이클을 Course가 관리하기 위해 위 방식 사용하지 않음 (선택)

        course.removeLecture(lecture)

        courseRepository.save(course) // Lecture에 영속성 전파
    }

    override fun getCourseApplicationList(courseId: Long): List<CourseApplicationResponse> {
        // TODO: 만약 courseId에 해당하는 Course가 없다면 throw ModelNotFoundException
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        return course.courseApplications.map { it.toResponse() }
    }

    override fun getCourseApplication(courseId: Long, applicationId: Long): CourseApplicationResponse {
        val application = courseApplicationRepository.findByCourseIdAndId(courseId, applicationId) ?: throw ModelNotFoundException("CourseApplication", applicationId)

        return application.toResponse()
    }

    @Transactional
    override fun applyCourse(courseId: Long, request: ApplyCourseRequest): CourseApplicationResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val user = userRepository.findByIdOrNull(request.userId) ?: throw ModelNotFoundException("User", request.userId)

        // 미리 정의했던 Policy - 마감 시 신청 불가
        if (course.isClosed()) {
            throw IllegalStateException("Course is already closed. courseId: $courseId")
        }

        // Policy - 중복 신청 불가
        if (courseApplicationRepository.existsByCourseIdAndUserId(courseId, request.userId)) {
            throw IllegalStateException("Already applied. courseId: $courseId, userId: ${request.userId}")
        }
        // CourseApplicationService가 따로 있다면 ~~service.checkDuplicate 함수로 따로 빼 사용

        val courseApplication = CourseApplication(
            course = course,
            user = user
            // status는 PENDING으로 초기화되기 때문에 명시하지 않아도 됨
        )
        course.addCourseApplication(courseApplication)
        courseRepository.save(course) // CourseApplicatio에 영속성 전파

        return courseApplication.toResponse()
    }

    @Transactional
    override fun updateCourseApplicationStatus(
        courseId: Long,
        applicationId: Long,
        request: UpdateApplicationStatusRequest
    ): CourseApplicationResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val application = courseApplicationRepository.findByCourseIdAndId(courseId, applicationId)
            ?: throw ModelNotFoundException("CourseApplication", applicationId)

        // Policy - 이미 상태 변경한 경우 다시 변경 불가
        if (application.isProceeded()) {
            throw IllegalStateException("Application is already proceeded. applicationId: $applicationId")
        }

        // Policy - 마감 시 승인 불가
        if (course.isClosed()) {
            throw IllegalStateException("Course is already closed. courseId: $courseId")
        }

        when (request.status) {
            // 승인 일 때
            CourseApplicationStatus.ACCEPTED.name -> {
                // 승인 처리
                application.accept()
                // Course의 신청 인원 늘림
                course.addApplicant()
                // Policy - 승인 인원과 최대 인원이 동일하면 마감으로 상태 변경
                if (course.isFull()) {
//                    course.status = CourseStatus.CLOSED
                    // 직접 쓰는 것보다 함수를 사용하는 게 가독성이 좋음
                    course.close()
                    // 도메인 로직을 Entity 안에 Encapsulation할 수 있음
                }
                courseRepository.save(course)
            }

            // 거절 일 때
            CourseApplicationStatus.REJECTED.name -> {
                // 거절 처리
                application.reject()
            }

            // 승인/거절이 아닌 경우 예외
            else -> {
                throw IllegalArgumentException("Invalid status: ${request.status}")
            }
        }

        return courseApplicationRepository.save(application).toResponse()
    }

}
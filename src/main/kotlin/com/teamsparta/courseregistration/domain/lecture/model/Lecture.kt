package com.teamsparta.courseregistration.domain.lecture.model

import com.teamsparta.courseregistration.domain.course.model.Course
import jakarta.persistence.*

@Entity
@Table(name = "lecture")
class Lecture(

    @Column(name = "title")
    var title: String,

    @Column(name = "video_url")
    var videoUrl: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id") // FK 명시해줌. 필수 아님
    val course: Course

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
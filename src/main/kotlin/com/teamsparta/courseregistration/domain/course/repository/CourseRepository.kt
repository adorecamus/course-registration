package com.teamsparta.courseregistration.domain.course.repository

import com.teamsparta.courseregistration.domain.course.model.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long> // JpaRepository<객체의 타입, id의 타입>
{

}
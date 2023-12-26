package com.teamsparta.courseregistration.domain.course.model

import com.teamsparta.courseregistration.domain.courseapplication.model.CourseApplication
import com.teamsparta.courseregistration.domain.lecture.model.Lecture
import jakarta.persistence.*

@Entity
@Table(name = "course") // name 생략하면 클래스 이름과 같은 테이블을 자동으로 매핑

// Entity class 는 Entity manager를 통해 상태가 변경되기도 하고 자체적으로 도메인의 요구사항을 갖기도 하므로 데이터 전달 용도 그 이상.
// 따라서 data class가 아닌 일반 class로 정의

// Hibernate 가이드 - Entity class는 final 불가, no-argument constructor(변수 없는 생성자) 필요
// 따라서 open class로 설정하고 constructor를 써줘야 하지만, 매번 그러려면 귀찮으니까..
// allopen, noarg 플러그인을 추가해 entity 관련 클래스(Entity, MappedSuperclass, Embeddable)에 적용
class Course(
    @Column(name = "title") // nullable = false 와 같은 설정은 ddl할 때 사용
    var title: String,

    @Column(name = "description")
    var description: String? = null,

    @Enumerated(EnumType.STRING) // enum을 숫자가 아닌 String으로 DB에 저장
    @Column(name = "status")
    var status: CourseStatus,

    @Column(name = "max_applicants")
    val maxApplicants: Int = 30,

    @Column(name = "num_applicants")
    val numApplicants: Int = 0,

    @OneToMany(mappedBy = "course",     // 양방향관계에서 연관관계의 주인(FK 소유)이 아닌 쪽에 mappedBy 명시
        fetch = FetchType.LAZY,         // 지연 로딩 LAZY, 즉시 로딩 EAGER - 성능 향상 위해 기본적으로 LAZY로 설정
        cascade = [CascadeType.ALL],    // 부모 Entity의 모든 변경 작업(저장, 삭제, 갱신)을 자식에 전파. Array 형태로 표기.
        orphanRemoval = true)           // 관계가 끊어진 고아 상태의 자식 객체를 자동으로 삭제.
                                        // (자식 객체 삭제 시 FK가 null이 되며 부모와 관계가 끊어지고 삭제되지 않음.)
    var lectures: MutableList<Lecture> = mutableListOf(),

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var courseApplications: MutableList<CourseApplication> = mutableListOf()

) {
//    constructor() : this(title = "", description = null, status = CourseStatus.OPEN, maxApplicants = 30, numApplicants = 0)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에 자동 생성 위임
    var id: Long? = null // DB에 위임하기 때문에 nullable 로 설정
}
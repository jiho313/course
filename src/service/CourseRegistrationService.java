package service;

import java.util.List;

import dao.CourseDao;
import dao.CourseRegistrationDao;
import dto.CourseRegistrationDto;
import vo.Course;
import vo.CourseRegistration;

public class CourseRegistrationService {
	private CourseDao courseDao = new CourseDao();
	private CourseRegistrationDao courseRegistrationDao = new CourseRegistrationDao();

	// 학생 아이디로 신청한 과정 모두 조회하기
	public List<CourseRegistrationDto> getAllRegistrationsByNoI(String studentId) {
		List<CourseRegistrationDto> dtos = courseRegistrationDao.getRegistrationsById(studentId);
		
		if (dtos.isEmpty()) {
			throw new RuntimeException("신청한 과정이 존재하지 않습니다.");
		}
		return dtos;
	}

	// 학생 신청한 과정신청 취소하기
	public void cancelCourseRegistration(int regNo, String studentId) {
		Course course = courseDao.getCouseByRegNo(regNo);
		CourseRegistration reg = courseRegistrationDao.getRegistrationsByRegNoStudentId(regNo, studentId);

		if (!reg.getStudentId().equals(studentId)) {
			throw new RuntimeException("입력한 신청 과정이 존재하지 않습니다.");
		}
		if (course.getStatus().equals("모집완료")) {
			course.setStatus("모집중");
		}
		course.setReqCnt(course.getReqCnt() - 1);
		courseDao.updateCourse(course);
		courseRegistrationDao.canceledCourseRegistrationByRegNo(regNo);
	}

	// 과정 신청하기
	public void registerCourse(String studentId, int courseNo) {
		Course course = courseDao.getCouseByNo(courseNo);
		List<CourseRegistrationDto> reg = courseRegistrationDao.getRegistrationsById(studentId);

		if (course == null) {
			throw new RuntimeException("개설된 과정이 존재하지 않습니다.");
		} else {
			if (course.getStatus().equals("모집완료")) {
				throw new RuntimeException("이미 모집이 완료된 과정입니다.");
			}

			if (!reg.isEmpty()) {
				throw new RuntimeException("이미 신청한 과정입니다.");
			}

			course.setReqCnt(course.getReqCnt() + 1);
			if (course.getquota() == course.getReqCnt()) {
				course.setStatus("모집완료");
			}
			courseDao.updateCourse(course);
			courseRegistrationDao.insertCourseRegistration(studentId, courseNo);
		}
	}
}

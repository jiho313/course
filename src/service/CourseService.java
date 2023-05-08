package service;

import dao.CourseDao;
import vo.Course;

public class CourseService {
	
	CourseDao courseDao = CourseDao.getInstance();
	
	// 컨트롤러 단에서 사용되진 않지만 추후 필요에 의해 로직 구현 가능성을 대비해서 만들어놓음
	public Course getCouseByCourseNo(int courseNo) {
		
		return courseDao.getCouseByNo(courseNo);
	}
}

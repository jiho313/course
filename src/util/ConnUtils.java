package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnUtils {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERNAME = "hr";
	private static final String PASSWORD = "zxcv1234";
	
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		}catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
	
		}
	}
	
	// static 메서드 안에 올 수 있는 변수만 static 변수 뿐이다.
	// 1. static 메서드는 객체를 만들지 않아도 사용할 수 있다.
	// 2. 인스턴스 변수는 객체를 생성해야만 사용가능하기 때문에 static메서드 블록안에서는 사용할수 없다.
	// 3. 따라서 static메서드에서 필요한 변수가 인스턴스 변수라면, static변수로 바꿔서 사용하여 사용한다.
	// 4. 아니면 해당 메서드 인스터르 메서드로 바꾸고 사용할 때마다 메서드 클래스 객체를 생성한 후 변수를 통해서 메서드를 사용하는 방법이 있다.
	//	* 하지만 지금 우리의 의도는 별도로 ConnUtil객체를 생성하지 않고 ConnUtil.getConnection 코드를 통해서 바로 사용할 수 있도록
	//	  메서드와 메서드에 필요한 변수를 모두 static으로 생성하여 클래스를 구현했다.
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}


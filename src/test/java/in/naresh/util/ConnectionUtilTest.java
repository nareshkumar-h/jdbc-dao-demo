package in.naresh.util;

import java.sql.Connection;

public class ConnectionUtilTest {

	public static void main(String[] args) {

		Connection connection = ConnectionUtil.getConnection();
		System.out.println(connection);
	}

}

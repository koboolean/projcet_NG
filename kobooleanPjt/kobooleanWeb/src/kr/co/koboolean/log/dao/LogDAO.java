package kr.co.koboolean.log.dao;

import static kr.co.koboolean.db.JdbcUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import kr.co.koboolean.vo.User;


public class LogDAO {
	// singleton 패턴사용
	private static LogDAO logDAO;
	private Connection con;

	private LogDAO() {

	}

	public static LogDAO getInstance() {
		if (logDAO == null) {
			logDAO = new LogDAO();
		}
		return logDAO;
	}

	public void setConnection(Connection con) {
		this.con = con;
	}

	public int createUser(User user) {
		int insertMem = 0;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO USERS(user_id, user_pw, user_name, user_phone, user_num)"
				+ "VALUES(?,?,?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user.getUser_id());
			pstmt.setString(2, user.getUser_pw());
			pstmt.setString(3, user.getUser_name());
			pstmt.setString(4, user.getUser_phone());
			pstmt.setString(5, user.getUser_num());
			
			insertMem = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return insertMem;
	}

	public String loginUser(User user) {
		boolean success = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User users = new User();
		String user_name = null;
		
		String sql = "SELECT user_name FROM users WHERE user_id = ? and user_pw = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUser_id());
			pstmt.setString(2, user.getUser_pw());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user_name = rs.getString("user_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return user_name;
	}

	
	
}

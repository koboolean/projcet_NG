package kr.co.koboolean.log.service;

import java.sql.Connection;
import static kr.co.koboolean.db.JdbcUtil.*;

import kr.co.koboolean.log.dao.LogDAO;
import kr.co.koboolean.vo.Users;

public class SigninProcessService {
	
	public boolean createUserService(Users user) {
		Connection con = getConnection();
		LogDAO logDAO = LogDAO.getInstance();
		logDAO.setConnection(con);
		boolean successCount = false;
		int count = logDAO.createUser(user);
		if(count > 0) {
			successCount = true;
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return successCount;
		
	}

	public String loginUserService(Users user) {
		Connection con = getConnection();
		LogDAO logDAO = LogDAO.getInstance();
		logDAO.setConnection(con);
		
		String count = logDAO.loginUser(user);
		
		close(con);
		
		return count;
	}
	
}

package kr.co.koboolean.main.svc;


import java.sql.Connection;
import static kr.co.koboolean.db.JdbcUtil.*;

import kr.co.koboolean.main.dao.AreaDAO;
import kr.co.koboolean.vo.Areas;

public class AreaService {

	public boolean insertArea(Areas area) {
		// TODO Auto-generated method stub
		
		Connection con = getConnection();
		AreaDAO areaDAO = AreaDAO.getInstance();
		areaDAO.setConnection(con);
		boolean success = false;
		System.out.println(area.getArea_name()+"  "+area.getArea_id() +"  "+ area.getArea_intro()+"  "+ area.getArea_address()+"  "+ area.getArea_num());
		int count = areaDAO.createArea(area);
		if(count > 0) {
			success = true;
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		
		return success;
	}

	public Areas searchArea(String user_id) {
		
		Connection con = getConnection();
		AreaDAO areaDAO = AreaDAO.getInstance();
		areaDAO.setConnection(con);
		Areas success = areaDAO.selectArea(user_id);
		
		return success;
	}

}

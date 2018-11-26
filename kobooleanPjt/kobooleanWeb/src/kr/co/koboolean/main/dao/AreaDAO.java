package kr.co.koboolean.main.dao;

import static kr.co.koboolean.db.JdbcUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import kr.co.koboolean.vo.Areas;


public class AreaDAO {
	private static AreaDAO areaDAO;
	private Connection con;
	
	private AreaDAO() {
		
	}
	
	public static AreaDAO getInstance() {
		if(areaDAO == null) {
			areaDAO = new AreaDAO();
		}
		return areaDAO;
	}
	
	
	public void setConnection(Connection con) {
		// TODO Auto-generated method stub
		this.con = con;
	}

	public int createArea(Areas area) {
		int insertSuccess = 0;
		PreparedStatement pstmt = null;
		System.out.println(area.getArea_name()+"  "+area.getArea_id() +"  "+ area.getArea_intro()+"  "+ area.getArea_address()+"  "+ area.getArea_num());
		String sql = "INSERT INTO AREAS(AREA_ID, AREA_NAME, AREA_ADDRESS, AREA_NUM, AREA_INTRO) VALUES(?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, area.getArea_id());
			pstmt.setString(2, area.getArea_name());
			pstmt.setString(3, area.getArea_address());
			pstmt.setString(4, area.getArea_num());
			pstmt.setString(5, area.getArea_intro());
			
			insertSuccess = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return insertSuccess;
	}

}

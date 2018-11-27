package kr.co.koboolean.main.dao;

import static kr.co.koboolean.db.JdbcUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

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

	public Areas selectArea(String user_id) {
		Areas area = new Areas();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String sql = "select * from areas where area_id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				area.setArea_id(rs.getString("area_id"));
				area.setArea_name(rs.getString("area_name"));
				area.setArea_address(rs.getString("area_address"));
				area.setArea_num(rs.getString("area_num"));
				area.setArea_intro(rs.getString("area_intro"));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(rs);
			close(pstmt);
		}
		return area;
	}

	public int updateArea(Areas area) {
		PreparedStatement pstmt = null;
		int success = 0;
		String sql = "update areas set area_name = ? , area_address = ?, area_num = ? , area_intro = ? where area_id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, area.getArea_name());
			pstmt.setString(2, area.getArea_address());
			pstmt.setString(3, area.getArea_num());
			pstmt.setString(4, area.getArea_intro());
			pstmt.setString(5, area.getArea_id());
			
			success = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			close(pstmt);
		}
		
		
		return success;
	}

	

}

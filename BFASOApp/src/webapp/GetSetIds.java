package webapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class GetSetIds {
	public static int pageid = 1;
	public static int wordid = 1;
	public static int linkid = 1;
	static Connection con = DBConnection.getDBConnection();
	
	
	public static int getPageid() {
		try {
			PreparedStatement ps = con.prepareStatement("select ID from page order by ID Desc limit 1");
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				pageid = rs.getInt("ID") + 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pageid;
	}
	public static int getWordid() {
		try {
			PreparedStatement ps = con.prepareStatement("select ID from words order by ID Desc limit 1");
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				wordid = rs.getInt("ID") + 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return wordid;
	}
	public static int getLinkid() {
		
		try {
			PreparedStatement ps = con.prepareStatement("select ID from links order by ID Desc limit 1");
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				linkid = rs.getInt("ID") + 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return linkid;
	}
	
	
}

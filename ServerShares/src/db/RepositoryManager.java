package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import unacloud.entities.RepositoryEntity;

/**
 * Generic class used to process queries and updates on Repository entity 
 * @author Cesar
 *
 */
public class RepositoryManager {

	/**
	 * Return the main repository in system
	 * @return
	 */
	public static RepositoryEntity getRepositoryByName(String name){
		try {
			Connection con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT re.id, re.name, re.capacity, re.path FROM repository re WHERE re.name = ?;");
			ps.setString(1,name);
			ResultSet rs = ps.executeQuery();			
			if(rs.next())return new RepositoryEntity(rs.getLong(1), rs.getString(2), rs.getInt(3), rs.getString(4));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Return a repository entity from database
	 * @param id
	 * @return
	 */
	public static RepositoryEntity getRepository(Long id){
		try {
			Connection con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT re.id, re.name, re.capacity, re.path FROM repository re WHERE re.id = ?;");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();			
			if(rs.next())return new RepositoryEntity(rs.getLong(1), rs.getString(2), rs.getInt(3), rs.getString(4));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
	package com.fssa.projectprovision.dao;
	
	import com.fssa.projectprovision.exception.DAOException;
	import com.fssa.projectprovision.model.User;
	import com.fssa.projectprovision.utils.ConnectionUtil;
	
	import java.sql.Connection;
	import java.sql.Date;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;
	
	public class UserDAO {
	
	    private UserDAO() {
	    }
	
	    public static boolean createUser(User user) throws DAOException {
	        String query = "INSERT INTO users (name, gender, mobile_number, date_of_birth, address, about_me, " +
	                "email, password, profile_pic, mytodos, user_id) " +
	                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        try (Connection connection = ConnectionUtil.getConnection();
	             PreparedStatement pst = connection.prepareStatement(query)) {
	
	            pst.setString(1, user.getName());
	            pst.setString(2, user.getGender());
	            pst.setString(3, user.getMobileNumber());
	            pst.setDate(4, Date.valueOf(user.getDateOfBirth()));
	            pst.setString(5, user.getAddress());
	            pst.setString(6, user.getAboutMe());
	            pst.setString(7, user.getEmail());
	            pst.setString(8, user.getPassword());
	            pst.setString(9, user.getProfilePic());
	            pst.setString(10, user.getMyTodos());
	            pst.setLong(11, user.getUserId());
	
	            int rowsAffected = pst.executeUpdate();
	            return rowsAffected > 0;
	
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }
	
	    public static User getUserByEmail(String email) throws DAOException {
	        User user = null;
	        String query = "SELECT id, name, gender, mobile_number, date_of_birth, address, about_me, email, " +
	                "password, profile_pic, mytodos, user_id " +
	                "FROM users WHERE email = ?";
	        try (Connection connection = ConnectionUtil.getConnection();
	             PreparedStatement pst = connection.prepareStatement(query)) {
	
	            pst.setString(1, email);
	
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    user = buildUserFromResultSet(rs);
	                }
	            }
	
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	        return user;
	    }
	
	    public static List<User> getAllUsers() throws DAOException {
	        List<User> userList = new ArrayList<>();
	        String query = "SELECT id, name, gender, mobile_number, date_of_birth, address, about_me, email, " +
	                "password, profile_pic, mytodos, user_id " +
	                "FROM users";
	        try (Connection connection = ConnectionUtil.getConnection();
	             PreparedStatement pst = connection.prepareStatement(query);
	             ResultSet rs = pst.executeQuery()) {
	
	            while (rs.next()) {
	                User user = buildUserFromResultSet(rs);
	                userList.add(user);
	            }
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	        return userList;
	    }
	
	    public static boolean updateUser(User user) throws DAOException {
	        String query = "UPDATE users SET " +
	                "name = ?, gender = ?, mobile_number = ?, date_of_birth = ?, address = ?, about_me = ?, " +
	                "password = ?, profile_pic = ?, mytodos = ? " +
	                "WHERE email = ?";
	
	        try (Connection connection = ConnectionUtil.getConnection();
	             PreparedStatement pst = connection.prepareStatement(query)) {
	
	            pst.setString(1, user.getName());
	            pst.setString(2, user.getGender());
	            pst.setString(3, user.getMobileNumber());
	            pst.setDate(4, Date.valueOf(user.getDateOfBirth()));
	            pst.setString(5, user.getAddress());
	            pst.setString(6, user.getAboutMe());
	            pst.setString(7, user.getPassword());
	            pst.setString(8, user.getProfilePic());
	            pst.setString(9, user.getMyTodos());
	            pst.setString(10, user.getEmail());
	
	            int rowsAffected = pst.executeUpdate();
	            return rowsAffected > 0;
	
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    } 
	    
	    
	    public static boolean deleteUserByEmail(String email) throws DAOException {
	        User userToDelete = getUserByEmail(email);

	        if (userToDelete == null) {
	            throw new DAOException("User with email " + email + " not found.");
	        }

	        if (userToDelete.isDeleted()) {
	            // Handle the case when the user is already marked as deleted
	            return false;
	        }

	        String query = "UPDATE users SET mytodos = NULL, profile_pic = NULL, isDeleted = true WHERE email = ?";
	        try (Connection connection = ConnectionUtil.getConnection();
	             PreparedStatement pst = connection.prepareStatement(query)) {

	            pst.setString(1, email);

	            int rowsAffected = pst.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }
	    
	    
//	
//	    public static boolean deleteUserByEmail(String email) throws DAOException {
//	        User userToDelete = getUserByEmail(email);
//	
//	        if (userToDelete == null) {
//	            throw new DAOException("User with email " + email + " not found.");
//	        }
//	
//	        if (!userToDelete.isActive()) {
//	            // Handle the case when the user is already inactive
//	            return false;
//	        }
//	
//	        String query = "UPDATE users SET mytodos = ?, profile_pic = NULL WHERE email = ?";
//	        try (Connection connection = ConnectionUtil.getConnection();
//	             PreparedStatement pst = connection.prepareStatement(query)) {
//	
//	            pst.setString(1, null);
//	            pst.setString(2, email);
//	
//	            int rowsAffected = pst.executeUpdate();
//	            return rowsAffected > 0;
//	        } catch (SQLException e) {
//	            throw new DAOException(e);
//	        }
//	    }
	    public static boolean loginUser(String email, String password) throws DAOException {
	        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
	        try (Connection connection = ConnectionUtil.getConnection();
	             PreparedStatement pst = connection.prepareStatement(query)) {

	            pst.setString(1, email);
	            pst.setString(2, password);

	            try (ResultSet rs = pst.executeQuery()) {
	                return rs.next(); // Return true if a user was found, false otherwise
	            }

	        } catch (SQLException e) {
	            throw new DAOException(e);
	        }
	    }
	
	    private static User buildUserFromResultSet(ResultSet rs) throws SQLException {
	        User user = new User();
	        user.setId(rs.getLong("id"));
	        user.setName(rs.getString("name"));
	        user.setGender(rs.getString("gender"));
	        user.setMobileNumber(rs.getString("mobile_number"));
	        user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
	        user.setAddress(rs.getString("address"));
	        user.setAboutMe(rs.getString("about_me"));
	        user.setEmail(rs.getString("email"));
	        user.setPassword(rs.getString("password"));
	        user.setProfilePic(rs.getString("profile_pic"));
	        user.setMyTodos(rs.getString("mytodos"));
	        user.setUserId(rs.getLong("user_id"));
	        return user;
	    }
	}

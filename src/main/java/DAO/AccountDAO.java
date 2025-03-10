package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {


    // create account for user
    public Account insertAccount(Account account){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            ResultSet pkResultSet = ps.getGeneratedKeys();
            if (pkResultSet.next()){
                int generated_account_id = (int) pkResultSet.getLong(1);
                account.setAccount_id(generated_account_id);
                return account;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    };
    //retrieve account for user
    public Account getAccountByUserAndPass(String username, String password){
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Account acc = new Account(
                rs.getString("username"),
                rs.getString("password"));
                acc.setAccount_id(rs.getInt("account_id"));
                return acc; 
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
        
    }
    public Account getAccountByUserName(String username){
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
           
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Account acc = new Account(
                rs.getString("username"),
                rs.getString("password"));
                acc.setAccount_id(rs.getInt("account_id"));
                return acc; 
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

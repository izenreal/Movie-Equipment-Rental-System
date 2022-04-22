/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package gui_tutorial;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DataHandler {
// DB details

    private static final String dbURL = "jdbc:ucanaccess://Cinema_DB.accdb;sysSchema=true";
    private static java.sql.Connection con;
    private static java.sql.Statement stm;
    private static java.sql.ResultSet rs;
    private static java.sql.ResultSetMetaData rsMeta;
    private static int columnCount;
    
    // public variables
    public static int log_in = 0;
   
    public static String ques_id = "";

    
    public static Vector<String> getTables() {
        Vector<String> l = new Vector<>();
        
        String sqlQuery = "SELECT Name FROM sys.MSysObjects WHERE Type=1 AND Flags=0";
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            stm = con.createStatement();
            rs = stm.executeQuery(sqlQuery);
            while (rs.next()) {
            // each row is an array of objects
                    l.add((String) rs.getObject(1));
            }
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } catch (Exception ex) {
            System.err.println(ex);
            //ex.printStackTrace();
        } finally {
            try {
                if (null != con) {
                    // cleanup resources, once after processing
                    rs.close();
                    stm.close();
                    // and then finally close connection
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return l;
    }

    public static void searchRecords(String table) {
        String sqlQuery = "SELECT * FROM " + table ;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            stm = con.createStatement(
                    java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sqlQuery);
            rsMeta = rs.getMetaData();
            columnCount = rsMeta.getColumnCount();

        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } catch (Exception ex) {
            System.err.println(ex);
            //ex.printStackTrace();
        }
    }
    
        public static void getAdminID(String admin_user) {
        String sqlQuery = "SELECT admin_id FROM Admin WHERE admin_user=?";
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1,admin_user); // assign the user_id 
            
            rs = pst.executeQuery();
            rsMeta = rs.getMetaData();
            columnCount = rsMeta.getColumnCount();
            if(rs.next()) { // assign the result to the variables
                login.admin_id = rs.getInt(1); 
            }
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } catch (Exception ex) {
            System.err.println(ex);
            //ex.printStackTrace();
        }
    }    
        
    public static void addEquipment(String eq_name, String eq_brand, double price,int quantity) {
        String sqlQuery = "INSERT INTO Equipment (equip_name,brand,price,quantity,admin_id) VALUES(?,?,?,?,?)";
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1,eq_name); // assign the user_id 
            pst.setString(2,eq_brand);
            pst.setDouble(3,price);
            pst.setInt(4,quantity);
            pst.setInt(5,login.admin_id);
            pst.executeUpdate();
            
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } catch (Exception ex) {
            System.err.println(ex);
            //ex.printStackTrace();
        }
    }   
    
    public static void editEquipment(String eq_name, String eq_brand, double price,int quantity) {
        String sqlQuery = "UPDATE Equipment SET equip_name = ?, brand = ?, price = ?, quantity = ?, admin_id = ? WHERE equip_id=?";
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1,eq_name); // assign the user_id 
            pst.setString(2,eq_brand);
            pst.setDouble(3,price);
            pst.setInt(4,quantity);
            pst.setInt(5,login.admin_id);
            pst.setInt(6,edit_equip.equip_id);
            pst.executeUpdate();
            
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } catch (Exception ex) {
            System.err.println(ex);
            //ex.printStackTrace();
        }
    }   
    
    public static void delEquipment(int equip_id) {
        String sqlQuery = "DELETE FROM Equipment WHERE equip_id="+equip_id;
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.executeUpdate();
            
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } catch (Exception ex) {
            System.err.println(ex);
            //ex.printStackTrace();
        }
    }   
    
    // function to find if the login details entered by the user is in the database
    public static void searchUser(String admin_user, String admin_pass) {
        String sqlQuery = "SELECT * FROM Admin where admin_user=? and admin_pass=?"; 
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1,admin_user); // assign the user_id 
            pst.setString(2, admin_pass); // assign the user_name
            rs = pst.executeQuery();
            rsMeta = rs.getMetaData();
            columnCount = rsMeta.getColumnCount();
            if(rs.next()) { // if the query gets a result, set the login variable to 1
                log_in = 1;
                
                
            } else { // if the query doesnt get any result, set the login variable to 0
                log_in = 0;
                
            }
           
            
            
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } catch (Exception ex) {
            
            System.err.println(ex);
            //ex.printStackTrace();
        }
    }

    public static Object[] getTitles(String table) {
        Object[] columnNames = new Object[columnCount];
        try {
            for (int col = columnCount; col > 0; col--) {
                columnNames[col - 1]
                        = rsMeta.getColumnName(col);
            }
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } finally {
            try {
                if (null != con) {
                    // cleanup resources, once after processing
                    rs.close();
                    stm.close();
                    // and then finally close connection
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return columnNames;
    }

    public static Object[][] getRows(String table) {
        searchRecords(table);
        Object[][] content;
        try {
// determine the number of rows
            rs.last();
            int number = rs.getRow();
            content = new Object[number][columnCount];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
// each row is an array of objects
                for (int col = 1; col <= columnCount; col++) {
                    content[i][col - 1] = rs.getObject(col);
                }
                i++;
            }
            return content;
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        }
        return null;
    }
}

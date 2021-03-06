/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package gui_tutorial;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    
    // public variable to set if the user/pass is valid
    public static int log_in = 0;
   
    
    // get the customer id of all customers (used for combobox)
    public static Vector<Integer> getCustomer() {
        Vector<Integer> l = new Vector<>();
        
        String sqlQuery = "SELECT cust_id FROM Customer";
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            stm = con.createStatement();
            rs = stm.executeQuery(sqlQuery);
            while (rs.next()) {
            // each row is an array of objects
                    l.add((int) rs.getObject(1));
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

    // get the equipment id of the equipment that has a quantity of more than 0
    public static Vector<Integer> getEquipment() {
        Vector<Integer> l = new Vector<>();
        
        String sqlQuery = "SELECT equip_id FROM Equipment WHERE quantity>0";
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            stm = con.createStatement();
            rs = stm.executeQuery(sqlQuery);
            while (rs.next()) {
            // each row is an array of objects
                    l.add((int) rs.getObject(1));
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

    // get the rent id of the equipment that has not yet returned by the customer
    public static Vector<Integer> getRent() {
        Vector<Integer> l = new Vector<>();
        
        String sqlQuery = "SELECT rent_id FROM Rent WHERE returned_date IS NULL";
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            stm = con.createStatement();
            rs = stm.executeQuery(sqlQuery);
            while (rs.next()) {
            // each row is an array of objects
                    l.add((int) rs.getObject(1));
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

    // get the customer details
    public static void getCustDetails(int cust_id) {
        String sqlQuery = "SELECT f_Name,phoneNo FROM Customer WHERE cust_id="+cust_id;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            rs = pst.executeQuery();
            rsMeta = rs.getMetaData();
            columnCount = rsMeta.getColumnCount();
            if(rs.next()) { // assign the result to the variables
                rent.cust_name = rs.getString(1); 
                rent.cust_phone = rs.getInt(2);
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
    
    // get the equipment details
    public static void getEquipDetails(int equip_id) {
        String sqlQuery = "SELECT equip_name,price,quantity FROM Equipment WHERE equip_id="+equip_id;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            rs = pst.executeQuery();
            rsMeta = rs.getMetaData();
            columnCount = rsMeta.getColumnCount();
            if(rs.next()) { // assign the result to the variables
                rent.equip_name = rs.getString(1); 
                rent.equip_price = rs.getDouble(2);
                rent.quantity = rs.getInt(3);
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
    
    // get the rent details
    public static void getRentDetails(int rent_id) {
        String sqlQuery = """
                          SELECT c.f_Name, e.equip_name, r.return_date, r.quantity, e.equip_id
                          FROM Equipment e, Customer c, Rent r
                          WHERE c.cust_id = r.cust_id AND e.equip_id = r.equipment_id AND r.rent_id="""+rent_id;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            rs = pst.executeQuery();
            rsMeta = rs.getMetaData();
            columnCount = rsMeta.getColumnCount();
            if(rs.next()) { // assign the result to the variables
                return_equipment.cust_name = rs.getString(1); 
                return_equipment.equip_name = rs.getString(2);
                return_equipment.return_date = rs.getTimestamp(3);
                return_equipment.quantity = rs.getInt(4);
                return_equipment.equip_id = rs.getInt(5);
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
    
    // return the equipment by updating the quantity and setting the find and returned date
    public static void returnEquipment(int equip_id, int rent_id, int fine, Date returned_date, int quantity) {
        String sqlQuery = "UPDATE Equipment SET quantity = quantity + ? WHERE equip_id="+equip_id;
        String sqlQuery2 = "UPDATE Rent SET fine = ?, returned_date = ? WHERE rent_id="+rent_id;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setInt(1,quantity); // assign the quantity
            pst.executeUpdate(); // execute the query
            
            PreparedStatement pst2 = con.prepareStatement(sqlQuery2);
            pst2.setInt(1,fine); // assign the fine
            pst2.setDate(2, new java.sql.Date(returned_date.getTime())); // asign the returned date
            pst2.executeUpdate(); // execute the query
            
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
        } catch (Exception ex) {
            System.err.println(ex);
            //ex.printStackTrace();
        }}
    
    // add the equipment to the table  
    public static void addEquipment(String eq_name, String eq_brand, double price,int quantity) {
        String sqlQuery = "INSERT INTO Equipment (equip_name,brand,price,quantity,admin_id) VALUES(?,?,?,?,?)";
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1,eq_name); // assign the equipment name
            pst.setString(2,eq_brand); // assign the brand name
            pst.setDouble(3,price); // assign the price
            pst.setInt(4,quantity); // assign the quantity
            pst.setInt(5,login.admin_id); // assign the admin id that added the equipment
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
    
    // edit the equipment from the table
    public static void editEquipment(String eq_name, String eq_brand, double price,int quantity) {
        String sqlQuery = "UPDATE Equipment SET equip_name = ?, brand = ?, price = ?, quantity = ?, admin_id = ? WHERE equip_id=?";
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1,eq_name); // assign the equipment name
            pst.setString(2,eq_brand); // assign the brand name
            pst.setDouble(3,price); // assign the price
            pst.setInt(4,quantity); // assign the quantity
            pst.setInt(5,login.admin_id); // assign the admin id that added the equipment
            pst.setInt(6,edit_equip.equip_id); // assign the equipment id that is being edited
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
    
    // delete the equipment from the table
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
    
    // add the customer to the table
    public static void addCustomer(String f_name, String l_name, String email,int phoneNo) {
        String sqlQuery = "INSERT INTO Customer (email,f_Name,l_Name,phoneNo,admin_id) VALUES(?,?,?,?,?)";
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1,email); // assign the email 
            pst.setString(2,f_name); // assign the first name
            pst.setString(3,l_name); // assign the last name
            pst.setInt(4,phoneNo); // assign the phone number
            pst.setInt(5,login.admin_id); // assign the admin id of the admin that added the customer
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Customer Added Successfully");
            
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Email/Phone number must be unique!");
           
        } catch (Exception ex) {
            System.err.println(ex);
            //ex.printStackTrace();
        }
    }   
    
    // edit the selected customer
    public static void editCustomer(String f_name, String l_name, String email,int phoneNo) {
        String sqlQuery = "UPDATE Customer SET email=?,f_Name=?,l_Name=?,phoneNo=?,admin_id=? WHERE cust_id=?";
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1,email); // assign the email 
            pst.setString(2,f_name); // assign the first name
            pst.setString(3,l_name); // assign the last name
            pst.setInt(4,phoneNo); // assign the phone number
            pst.setInt(5,login.admin_id); // assign the admin id of the admin that edited the customer
            pst.setInt(6,customer.cust_id); // assign the customer id thats being edited
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Customer Edited Successfully");
            
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
    
    // delete the selected customer
    public static void delCustomer(int cust_id) {
        String sqlQuery = "DELETE FROM Customer WHERE cust_id="+cust_id;
        
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
    
    // add rent order to the table
    public static void addRent(int cust_id, Date rent_start, Date rent_end,int quantity, int equip_id, double price) {
        // first sql query to insert the rent details
        String sqlQuery = "INSERT INTO Rent (cust_id,rent_start,return_date,quantity,equipment_id,price) VALUES(?,?,?,?,?,?)";
        // second sql query to subtract the number of quantity of the equipment
        String sqlQuery2 = "UPDATE Equipment SET quantity = quantity - ? WHERE equip_id=?";
        
        // convert the time to sql time
        java.sql.Date sqlStart = new java.sql.Date(rent_start.getTime());
        java.sql.Date sqlEnd = new java.sql.Date(rent_end.getTime());
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection(dbURL, "", "");
            // query 1
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setInt(1,cust_id); // assign the customer id
            pst.setDate(2,sqlStart); // assign the start date of the rent
            pst.setDate(3,sqlEnd); // assign the end of the rent
            pst.setInt(4,quantity); // assign the quantity
            pst.setInt(5,equip_id); // assign the equipment id
            pst.setDouble(6,price); // assign the price
            pst.executeUpdate();
            // query 2
            PreparedStatement pst2 = con.prepareStatement(sqlQuery2);
            pst2.setInt(1,quantity); // assign the quantity
            pst2.setInt(2,equip_id); // assign the equipment id
            pst2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Rent Added Successfully");
            
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Issue with the JDBC driver.");
            System.exit(1); // terminate program - cannot recover
        } catch (java.sql.SQLException sqlex) {
            System.err.println(sqlex);
           
        } catch (Exception ex) {
            System.err.println(ex);
        }}
        
        

    // function to find if the login details entered by the user is in the database
    public static void searchUser(String admin_user, String admin_pass) {
        String sqlQuery = "SELECT admin_id FROM Admin where admin_user=? and admin_pass=?"; 
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
                login.admin_id = rs.getInt(1); // assign the admin id
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
            
        }
    }
    
    // search for the records of the certain table
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
    
    // get the difference in days between two dates
    public static int getDateDiff(Date date1, Date date2) {
    
    LocalDate localdate1, localdate2;
    localdate1 = convertToLocalDateViaInstant(date1); // convert the date into your local date
    localdate2 = convertToLocalDateViaInstant(date2); // convert the date into your local date
    long diff = ChronoUnit.DAYS.between(localdate1,localdate2); // find the difference
   
    return (int) diff; // cast diff into int 
}
    // convert the date into local date
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
}
}

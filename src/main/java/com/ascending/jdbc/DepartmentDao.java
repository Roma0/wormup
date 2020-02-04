package com.ascending.jdbc;

import com.ascending.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //Step 1: Database information
    static final String DB_URL = "jdbc:postgresql://localhost:5431/test1_dev";
    static final String USER = "admin";
    static final String PASS = "password";

    public Department save(Department d){
        Department department = d;
        Connection conn = null;
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            logger.debug("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            logger.debug("Creating insert statement...");
            String sql = "insert into department (name, description, location)" + "values(?,?,?);";
            preparedStmt = conn.prepareStatement(sql, preparedStmt.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, department.getName());
            preparedStmt.setString(2,department.getDescription());
            preparedStmt.setString(3, department.getLocation());
            preparedStmt.execute();
            rs = preparedStmt.getGeneratedKeys();
            if(rs.next()){
                logger.debug("Getting generatedKey: " + rs.getLong("id"));
                long generatedKey = rs.getLong("id");
                department.setId(generatedKey);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null)preparedStmt.close();
                if(preparedStmt != null)preparedStmt.close();
                if(conn != null)conn.close();
                logger.debug("Database connection closed");
        }
            catch (SQLException se){
                se.printStackTrace();
            }
        }
        return department;
    }

    public boolean delete(long id){
//        Department department = new Department();
        Connection conn = null;
        Statement stmt = null;
        String sql;
        boolean rs = true;

        try{
            logger.debug("Connecting database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            logger.debug("Creating delete statement...");
            stmt = conn.createStatement();
            sql = "delete from department where id = " + id;
            rs = stmt.execute(sql);
            logger.debug("Delete method return result:"+ rs + "");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(stmt != null)stmt.close();
                if(conn != null)conn.close();
                logger.debug("Database connection closed");
            }
            catch (SQLException se){
                se.printStackTrace();
            }
        }
        return rs;
    }

    public List<Department> getDepartments(){
        List<Department> departments = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 2: Open a connection
            logger.debug("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            logger.debug("Creating select statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM department";
            rs = stmt.executeQuery(sql);
            //STEP 4: Extract data from result set
            while(rs.next()) {
                //Retrieve by column name
                long id  = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");
                //Fill the object
                Department department = new Department();
                department.setId(id);
                department.setName(name);
                department.setDescription(description);
                department.setLocation(location);
                departments.add(department);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            //STEP 6: finally block used to close resources
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
                logger.debug("Database connection closed");
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return departments;
//        return new ArrayList<entity>();
        //entity=table
    }
}


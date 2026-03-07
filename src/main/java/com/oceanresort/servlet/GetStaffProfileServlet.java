package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/getStaffProfile")
public class GetStaffProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{

        response.setContentType("application/json");

        PrintWriter out=response.getWriter();

        HttpSession session=request.getSession(false);

        if(session==null){

            out.print("{}");

            return;

        }

        String staffId=(String)session.getAttribute("staffId");

        try{

            Connection con=DBConnection.getConnection();

            String sql="SELECT * FROM users WHERE id=?";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1,staffId);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                String json="{"
                        +"\"id\":\""+rs.getString("id")+"\","
                        +"\"name\":\""+rs.getString("username")+"\","
                        +"\"email\":\""+rs.getString("email")+"\","
                        +"\"phone\":\""+rs.getString("phone")+"\","
                        +"\"address\":\""+rs.getString("address")+"\","
                        +"\"role\":\""+rs.getString("role")+"\","
                        +"\"age\":\""+rs.getString("age")+"\""
                        +"}";

                out.print(json);

            }

            rs.close();

            ps.close();

            con.close();

        }catch(Exception e){

            e.printStackTrace();

        }

    }
}
package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/getProfileImage")
public class GetProfileImageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{

        HttpSession session=request.getSession(false);

        if(session==null) return;

        String staffId=(String)session.getAttribute("staffId");

        try{

            Connection con=DBConnection.getConnection();

            String sql="SELECT profile_image FROM users WHERE id=?";

            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,staffId);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                byte[] img=rs.getBytes("profile_image");

                if(img!=null){

                    response.setContentType("image/jpeg");

                    OutputStream os=response.getOutputStream();
                    os.write(img);
                    os.flush();

                }

            }

            rs.close();
            ps.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
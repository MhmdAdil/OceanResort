package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/uploadProfileImage")
@MultipartConfig(maxFileSize = 16177215)
public class UploadProfileImageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{

        response.setContentType("text/plain");
        PrintWriter out=response.getWriter();

        HttpSession session=request.getSession(false);

        if(session==null){
            out.print("error");
            return;
        }

        String staffId=(String)session.getAttribute("staffId");

        Part filePart=request.getPart("profileImage");

        InputStream inputStream=filePart.getInputStream();

        try{

            Connection con=DBConnection.getConnection();

            String sql="UPDATE users SET profile_image=? WHERE id=?";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setBlob(1,inputStream);
            ps.setString(2,staffId);

            int result=ps.executeUpdate();

            if(result>0){
                out.print("success");
            }else{
                out.print("error");
            }

            ps.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
            out.print("error");
        }

    }
}
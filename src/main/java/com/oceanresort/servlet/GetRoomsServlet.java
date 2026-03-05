package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/getRooms")
public class GetRoomsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{

        response.setContentType("application/json");

        PrintWriter out=response.getWriter();

        StringBuilder json=new StringBuilder();

        json.append("[");

        boolean first=true;

        try{

            Connection con= DBConnection.getConnection();

            String sql="SELECT room_id,room_type,capacity,price,status FROM rooms";

            PreparedStatement ps=con.prepareStatement(sql);

            ResultSet rs=ps.executeQuery();

            while(rs.next()){

                if(!first) json.append(",");

                first=false;

                json.append("{")
                        .append("\"roomId\":\"").append(rs.getString("room_id")).append("\",")
                        .append("\"roomType\":\"").append(rs.getString("room_type")).append("\",")
                        .append("\"capacity\":\"").append(rs.getString("capacity")).append("\",")
                        .append("\"price\":\"").append(rs.getString("price")).append("\",")
                        .append("\"status\":\"").append(rs.getString("status")).append("\"")
                        .append("}");

            }

            json.append("]");

            out.print(json.toString());

            rs.close();
            ps.close();
            con.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
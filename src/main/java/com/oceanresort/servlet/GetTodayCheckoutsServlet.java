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

@WebServlet("/getTodayCheckouts")
public class GetTodayCheckoutsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder();

        json.append("[");

        boolean first = true;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT r.reservation_id, g.name, r.room_type, r.check_out " +
                            "FROM reservations r " +
                            "JOIN guests g ON r.guest_id = g.guest_id " +
                            "WHERE DATE(r.check_out) = CURDATE()";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                if(!first){
                    json.append(",");
                }

                first = false;

                json.append("{")
                        .append("\"reservationId\":\"").append(rs.getString("reservation_id")).append("\",")
                        .append("\"name\":\"").append(rs.getString("name")).append("\",")
                        .append("\"roomType\":\"").append(rs.getString("room_type")).append("\",")
                        .append("\"checkOut\":\"").append(rs.getString("check_out")).append("\"")
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
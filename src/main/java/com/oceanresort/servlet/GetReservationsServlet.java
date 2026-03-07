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

@WebServlet("/getReservations")
public class GetReservationsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder();
        json.append("[");

        boolean first = true;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT r.reservation_id, g.name, g.phone, g.email, g.nic, g.address, " +
                            "r.room_type, r.guest_count, r.check_in, r.check_out, r.special_note " +
                            "FROM reservations r " +
                            "INNER JOIN guests g ON r.guest_id = g.guest_id";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                if(!first){
                    json.append(",");
                }
                first = false;

                json.append("{")
                        .append("\"id\":\"").append(escapeJson(rs.getString("reservation_id"))).append("\",")
                        .append("\"name\":\"").append(escapeJson(rs.getString("name"))).append("\",")
                        .append("\"phone\":\"").append(escapeJson(rs.getString("phone"))).append("\",")
                        .append("\"email\":\"").append(escapeJson(rs.getString("email"))).append("\",")
                        .append("\"nic\":\"").append(escapeJson(rs.getString("nic"))).append("\",")
                        .append("\"address\":\"").append(escapeJson(rs.getString("address"))).append("\",")
                        .append("\"roomType\":\"").append(escapeJson(rs.getString("room_type"))).append("\",")
                        .append("\"guestCount\":\"").append(escapeJson(rs.getString("guest_count"))).append("\",")
                        .append("\"checkIn\":\"").append(rs.getString("check_in")).append("\",")
                        .append("\"checkOut\":\"").append(rs.getString("check_out")).append("\",")
                        .append("\"note\":\"").append(escapeJson(rs.getString("special_note"))).append("\"")
                        .append("}");
            }

            json.append("]");

            out.print(json.toString());

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String escapeJson(String text){
        if(text == null) return "";
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}
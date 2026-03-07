package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/getReservationDetails")
public class GetReservationDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT r.room_type, r.guest_count, r.check_in, r.check_out, r.special_note, "
                    + "g.name, g.phone, g.email, g.nic, g.address "
                    + "FROM reservations r "
                    + "JOIN guests g ON r.guest_id = g.guest_id "
                    + "WHERE r.reservation_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String json = "{"
                        + "\"name\":\"" + rs.getString("name") + "\","
                        + "\"phone\":\"" + rs.getString("phone") + "\","
                        + "\"email\":\"" + rs.getString("email") + "\","
                        + "\"nic\":\"" + rs.getString("nic") + "\","
                        + "\"address\":\"" + rs.getString("address") + "\","
                        + "\"room_type\":\"" + rs.getString("room_type") + "\","
                        + "\"guest_count\":\"" + rs.getInt("guest_count") + "\","
                        + "\"check_in\":\"" + rs.getString("check_in").replace(" ", "T") + "\","
                        + "\"check_out\":\"" + rs.getString("check_out").replace(" ", "T") + "\","
                        + "\"special_note\":\"" + rs.getString("special_note") + "\""
                        + "}";

                out.print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\":\"Server Error\"}");
        }
    }
}
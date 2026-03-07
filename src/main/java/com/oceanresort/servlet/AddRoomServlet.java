package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/addRoom")
public class AddRoomServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String roomCode = request.getParameter("roomCode");
        String roomType = request.getParameter("roomType");
        String capacityStr = request.getParameter("capacity");
        String priceStr = request.getParameter("price");
        String status = request.getParameter("status");
        String description = request.getParameter("description");

        // Basic validation
        if (roomCode == null || roomCode.isEmpty()
                || roomType == null || roomType.isEmpty()
                || capacityStr == null || capacityStr.isEmpty()
                || priceStr == null || priceStr.isEmpty()
                || status == null || status.isEmpty()) {

            out.print("empty");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO rooms " +
                    "(room_code, room_type, capacity, price, status, description) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, roomCode);
            ps.setString(2, roomType);
            ps.setInt(3, Integer.parseInt(capacityStr));
            ps.setDouble(4, Double.parseDouble(priceStr));
            ps.setString(5, status);
            ps.setString(6, description);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.print("success");
            } else {
                out.print("error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("error");
        }
    }
}
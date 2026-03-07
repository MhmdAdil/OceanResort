package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/updateRoom")
public class UpdateRoomServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();

        String roomId = request.getParameter("roomId");
        String roomCode = request.getParameter("roomCode");
        String roomType = request.getParameter("roomType");
        String price = request.getParameter("price");
        String capacity = request.getParameter("capacity");
        String status = request.getParameter("status");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE rooms SET room_code=?, room_type=?, price=?, capacity=?, status=? WHERE room_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, roomCode);
            ps.setString(2, roomType);
            ps.setDouble(3, Double.parseDouble(price));
            ps.setInt(4, Integer.parseInt(capacity));
            ps.setString(5, status);
            ps.setInt(6, Integer.parseInt(roomId));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.print("Room updated successfully");
            } else {
                out.print("Update failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("Server error");
        }

    }

}
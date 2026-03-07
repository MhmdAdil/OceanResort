package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/getRoom")
public class GetRoomServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM rooms WHERE room_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(id));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String json = "{"
                        + "\"room_id\":\"" + rs.getInt("room_id") + "\","
                        + "\"room_code\":\"" + rs.getString("room_code") + "\","
                        + "\"room_type\":\"" + rs.getString("room_type") + "\","
                        + "\"capacity\":\"" + rs.getInt("capacity") + "\","
                        + "\"price\":\"" + rs.getDouble("price") + "\","
                        + "\"status\":\"" + rs.getString("status") + "\""
                        + "}";

                out.print(json);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

}
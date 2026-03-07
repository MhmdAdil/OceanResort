package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/getGuests")
public class GuestListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT guest_id, name, phone, nic FROM guests";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            StringBuilder json = new StringBuilder();
            json.append("[");

            boolean first = true;

            while (rs.next()) {

                if (!first) json.append(",");

                json.append("{")
                        .append("\"guest_id\":\"").append(rs.getString("guest_id")).append("\",")
                        .append("\"name\":\"").append(rs.getString("name")).append("\",")
                        .append("\"phone\":\"").append(rs.getString("phone")).append("\",")
                        .append("\"nic\":\"").append(rs.getString("nic")).append("\"")
                        .append("}");

                first = false;
            }

            json.append("]");

            out.print(json.toString());

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
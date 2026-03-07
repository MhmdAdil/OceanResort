package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/deleteGuest")
public class DeleteGuestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String guestId = request.getParameter("id");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "DELETE FROM guests WHERE guest_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, guestId);

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
package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/addReservation")
public class AddReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // ===== Get Parameters =====
        String guestName = request.getParameter("guestName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String nic = request.getParameter("nic");
        String address = request.getParameter("address");

        String roomType = request.getParameter("roomType");
        String guestCountStr = request.getParameter("guestCount");
        String checkInStr = request.getParameter("checkIn");
        String checkOutStr = request.getParameter("checkOut");
        String specialNote = request.getParameter("specialNote");

        // ===== Basic Validation =====
        if (guestName == null || guestName.isEmpty()
                || phone == null || phone.isEmpty()
                || nic == null || nic.isEmpty()
                || roomType == null || roomType.isEmpty()
                || guestCountStr == null || guestCountStr.isEmpty()
                || checkInStr == null || checkInStr.isEmpty()
                || checkOutStr == null || checkOutStr.isEmpty()) {

            out.print("empty");
            return;
        }

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Start transaction

            // ===============================
            // 1️⃣ Insert into guests table
            // ===============================
            String guestSql = "INSERT INTO guests (name, phone, email, nic, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement guestStmt = con.prepareStatement(guestSql, Statement.RETURN_GENERATED_KEYS);

            guestStmt.setString(1, guestName);
            guestStmt.setString(2, phone);
            guestStmt.setString(3, email);
            guestStmt.setString(4, nic);
            guestStmt.setString(5, address);

            int guestRows = guestStmt.executeUpdate();

            if (guestRows == 0) {
                con.rollback();
                out.print("error");
                return;
            }

            ResultSet rs = guestStmt.getGeneratedKeys();
            int guestId;

            if (rs.next()) {
                guestId = rs.getInt(1);
            } else {
                con.rollback();
                out.print("error");
                return;
            }

            // ===============================
            // 2️⃣ Insert into reservations table
            // ===============================
            String reservationSql =
                    "INSERT INTO reservations (guest_id, room_type, guest_count, check_in, check_out, special_note) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement resStmt = con.prepareStatement(reservationSql);

            resStmt.setInt(1, guestId);
            resStmt.setString(2, roomType);
            resStmt.setInt(3, Integer.parseInt(guestCountStr));

            // Convert datetime-local format
            Timestamp checkIn = Timestamp.valueOf(checkInStr.replace("T", " ") + ":00");
            Timestamp checkOut = Timestamp.valueOf(checkOutStr.replace("T", " ") + ":00");

            resStmt.setTimestamp(4, checkIn);
            resStmt.setTimestamp(5, checkOut);
            resStmt.setString(6, specialNote);

            int resRows = resStmt.executeUpdate();

            if (resRows == 0) {
                con.rollback();
                out.print("error");
                return;
            }

            con.commit(); // Commit transaction
            out.print("success");

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ignored) {}

            e.printStackTrace();
            out.print("error");

        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ignored) {}
        }
    }
}
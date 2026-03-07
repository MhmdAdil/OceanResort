package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String staffName = request.getParameter("staffName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String age = request.getParameter("age");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (staffName == null || phone == null || email == null || address == null ||
                age == null || role == null || password == null || confirmPassword == null ||
                staffName.isEmpty() || phone.isEmpty() || email.isEmpty() ||
                address.isEmpty() || age.isEmpty() || role.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {

            out.print("empty");
            return;
        }

        if (!password.equals(confirmPassword)) {
            out.print("mismatch");
            return;
        }

        try {

            Connection con = DBConnection.getConnection();

            // CHECK DUPLICATE PHONE NUMBER
            String check = "SELECT id FROM users WHERE phone=?";
            PreparedStatement ps1 = con.prepareStatement(check);
            ps1.setString(1, phone);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                out.print("exists");
                return;
            }

            String sql = "INSERT INTO users (username,password,role,phone,email,address,age) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, staffName);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setString(6, address);
            ps.setInt(7, Integer.parseInt(age));

            int result = ps.executeUpdate();

            if (result > 0) {
                out.print("success");
            } else {
                out.print("error");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.print("error");
        }
    }
}
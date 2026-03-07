package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/addRoomType")
public class AddRoomTypeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try {

            String roomTypeId = request.getParameter("roomTypeId");
            String name = request.getParameter("name");
            String price = request.getParameter("price");
            String maxGuests = request.getParameter("maxGuests");
            String description = request.getParameter("description");

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO room_types (room_type_id,name,price,max_guests,description) VALUES (?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, roomTypeId);
            ps.setString(2, name);
            ps.setDouble(3, Double.parseDouble(price));
            ps.setInt(4, Integer.parseInt(maxGuests));
            ps.setString(5, description);

            int result = ps.executeUpdate();

            if(result == 1){
                out.print("success");
            }else{
                out.print("error");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();  // shows error in console
            out.print("error");

        }
    }
}
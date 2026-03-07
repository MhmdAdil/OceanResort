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
@WebServlet("/updateReservation")
public class UpdateReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out=response.getWriter();

        String reservationId=request.getParameter("reservationId");
        String name=request.getParameter("guestName");
        String phone=request.getParameter("phone");
        String email=request.getParameter("email");
        String nic=request.getParameter("nic");
        String address=request.getParameter("address");

        String roomType=request.getParameter("roomType");
        String guestCount=request.getParameter("guestCount");
        String checkIn=request.getParameter("checkIn");
        String checkOut=request.getParameter("checkOut");
        String note=request.getParameter("note");

        try(Connection con=DBConnection.getConnection()){

            String getGuest="SELECT guest_id FROM reservations WHERE reservation_id=?";
            PreparedStatement ps1=con.prepareStatement(getGuest);
            ps1.setString(1,reservationId);

            ResultSet rs=ps1.executeQuery();

            int guestId=0;

            if(rs.next()){
                guestId=rs.getInt("guest_id");
            }

            String updateGuest="UPDATE guests SET name=?,phone=?,email=?,nic=?,address=? WHERE guest_id=?";
            PreparedStatement ps2=con.prepareStatement(updateGuest);

            ps2.setString(1,name);
            ps2.setString(2,phone);
            ps2.setString(3,email);
            ps2.setString(4,nic);
            ps2.setString(5,address);
            ps2.setInt(6,guestId);

            ps2.executeUpdate();


            String updateReservation="UPDATE reservations SET room_type=?,guest_count=?,check_in=?,check_out=?,special_note=? WHERE reservation_id=?";
            PreparedStatement ps3=con.prepareStatement(updateReservation);

            ps3.setString(1,roomType);
            ps3.setString(2,guestCount);
            ps3.setString(3,checkIn);
            ps3.setString(4,checkOut);
            ps3.setString(5,note);
            ps3.setString(6,reservationId);

            ps3.executeUpdate();

            out.print("Reservation updated successfully");

        }
        catch(Exception e){
            e.printStackTrace();
            out.print("Update failed");
        }

    }
}
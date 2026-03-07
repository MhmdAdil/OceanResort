package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/checkoutReservation")
public class CheckoutReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {

        PrintWriter out=response.getWriter();

        String reservationId=request.getParameter("reservationId");

        try(Connection con=DBConnection.getConnection()){

            String select="SELECT r.*,g.name,rm.price FROM reservations r "
                    +"JOIN guests g ON r.guest_id=g.guest_id "
                    +"JOIN rooms rm ON r.room_type=rm.room_type "
                    +"WHERE r.reservation_id=?";

            PreparedStatement ps=con.prepareStatement(select);

            ps.setString(1,reservationId);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                Timestamp checkIn=rs.getTimestamp("check_in");
                Timestamp checkOut=rs.getTimestamp("check_out");

                long diff=checkOut.getTime()-checkIn.getTime();
                long nights=diff/(1000*60*60*24);

                double price=rs.getDouble("price");

                double total=nights*price;

                String insert="INSERT INTO checkout_history "
                        +"(reservation_id,guest_name,room_type,guest_count,check_in,check_out,total_amount) "
                        +"VALUES(?,?,?,?,?,?,?)";

                PreparedStatement ps2=con.prepareStatement(insert);

                ps2.setInt(1,rs.getInt("reservation_id"));
                ps2.setString(2,rs.getString("name"));
                ps2.setString(3,rs.getString("room_type"));
                ps2.setInt(4,rs.getInt("guest_count"));
                ps2.setTimestamp(5,checkIn);
                ps2.setTimestamp(6,checkOut);
                ps2.setDouble(7,total);

                ps2.executeUpdate();

                String delete="DELETE FROM reservations WHERE reservation_id=?";

                PreparedStatement ps3=con.prepareStatement(delete);

                ps3.setString(1,reservationId);

                ps3.executeUpdate();

                out.print("Checkout completed and bill generated");

            }

        }catch(Exception e){

            e.printStackTrace();

            out.print("Checkout failed");

        }

    }
}
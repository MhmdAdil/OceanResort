package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/getBillingDetails")
public class GetBillingDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        PrintWriter out=response.getWriter();

        String id=request.getParameter("id");

        try(Connection con=DBConnection.getConnection()){

            String sql="SELECT g.name,r.room_type,r.check_in,r.check_out,rm.price "
                    +"FROM reservations r "
                    +"JOIN guests g ON r.guest_id=g.guest_id "
                    +"JOIN rooms rm ON r.room_type=rm.room_type "
                    +"WHERE r.reservation_id=?";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1,id);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                Timestamp checkIn=rs.getTimestamp("check_in");
                Timestamp checkOut=rs.getTimestamp("check_out");

                long diff=checkOut.getTime()-checkIn.getTime();

                long nights=diff/(1000*60*60*24);

                double price=rs.getDouble("price");

                double total=nights*price;

                String json="{"
                        +"\"name\":\""+rs.getString("name")+"\","
                        +"\"room_type\":\""+rs.getString("room_type")+"\","
                        +"\"price\":\""+price+"\","
                        +"\"check_in\":\""+checkIn+"\","
                        +"\"check_out\":\""+checkOut+"\","
                        +"\"nights\":\""+nights+"\","
                        +"\"total\":\""+total+"\""
                        +"}";

                out.print(json);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

    }
}
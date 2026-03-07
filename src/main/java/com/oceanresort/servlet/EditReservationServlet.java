package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;
import java.sql.*;

@WebServlet("/editReservation")
public class EditReservationServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{

        response.setContentType("application/json");

        String action=request.getParameter("action");

        PrintWriter out=response.getWriter();

        try(Connection con= DBConnection.getConnection()){

            if("getIds".equals(action)){

                String sql="SELECT reservation_id FROM reservations";

                PreparedStatement ps=con.prepareStatement(sql);
                ResultSet rs=ps.executeQuery();

                StringBuilder json=new StringBuilder("[");

                boolean first=true;

                while(rs.next()){

                    if(!first)json.append(",");
                    first=false;

                    json.append("\"").append(rs.getString(1)).append("\"");

                }

                json.append("]");

                out.print(json.toString());

            }



            if("getDetails".equals(action)){

                String id=request.getParameter("id");

                String sql="""
SELECT r.*,g.*
FROM reservations r
JOIN guests g ON r.guest_id=g.guest_id
WHERE r.reservation_id=?
""";

                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1,id);

                ResultSet rs=ps.executeQuery();

                if(rs.next()){

                    out.print("{"
                            +"\"name\":\""+rs.getString("name")+"\","
                            +"\"phone\":\""+rs.getString("phone")+"\","
                            +"\"email\":\""+rs.getString("email")+"\","
                            +"\"nic\":\""+rs.getString("nic")+"\","
                            +"\"address\":\""+rs.getString("address")+"\","

                            +"\"roomType\":\""+rs.getString("room_type")+"\","
                            +"\"guestCount\":\""+rs.getString("guest_count")+"\","
                            +"\"checkIn\":\""+rs.getString("check_in")+"\","
                            +"\"checkOut\":\""+rs.getString("check_out")+"\","
                            +"\"note\":\""+rs.getString("special_note")+"\""
                            +"}");

                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }



    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{

        BufferedReader reader=request.getReader();

        String body=reader.lines().reduce("",(a,b)->a+b);

        String reservationId=getValue(body,"reservationId");
        String name=getValue(body,"guestName");
        String phone=getValue(body,"phone");
        String email=getValue(body,"email");
        String nic=getValue(body,"nic");
        String address=getValue(body,"address");

        String roomType=getValue(body,"roomType");
        String guestCount=getValue(body,"guestCount");
        String checkIn=getValue(body,"checkIn");
        String checkOut=getValue(body,"checkOut");
        String note=getValue(body,"note");

        try(Connection con=DBConnection.getConnection()){

            String guestUpdate="""
UPDATE guests g
JOIN reservations r ON g.guest_id=r.guest_id
SET g.name=?,g.phone=?,g.email=?,g.nic=?,g.address=?
WHERE r.reservation_id=?
""";

            PreparedStatement ps1=con.prepareStatement(guestUpdate);

            ps1.setString(1,name);
            ps1.setString(2,phone);
            ps1.setString(3,email);
            ps1.setString(4,nic);
            ps1.setString(5,address);
            ps1.setString(6,reservationId);

            ps1.executeUpdate();



            String resUpdate="""
UPDATE reservations
SET room_type=?,guest_count=?,check_in=?,check_out=?,special_note=?
WHERE reservation_id=?
""";

            PreparedStatement ps2=con.prepareStatement(resUpdate);

            ps2.setString(1,roomType);
            ps2.setString(2,guestCount);
            ps2.setString(3,checkIn.replace("T"," "));
            ps2.setString(4,checkOut.replace("T"," "));
            ps2.setString(5,note);
            ps2.setString(6,reservationId);

            ps2.executeUpdate();

            response.getWriter().print("Reservation Updated Successfully");

        }catch(Exception e){
            e.printStackTrace();
        }

    }



    private String getValue(String body,String key){

        String[] parts=body.split("\""+key+"\":\"");

        if(parts.length<2)return"";

        return parts[1].split("\"")[0];

    }

}
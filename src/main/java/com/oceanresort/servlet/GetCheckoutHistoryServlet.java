package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/getCheckoutHistory")
public class GetCheckoutHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        PrintWriter out=response.getWriter();

        try(Connection con=DBConnection.getConnection()){

            String sql="SELECT * FROM checkout_history ORDER BY checkout_time DESC";

            PreparedStatement ps=con.prepareStatement(sql);

            ResultSet rs=ps.executeQuery();

            String json="[";

            boolean first=true;

            while(rs.next()){

                if(!first) json+=",";
                first=false;

                json+="{"
                        +"\"reservation_id\":\""+rs.getInt("reservation_id")+"\","
                        +"\"guest_name\":\""+rs.getString("guest_name")+"\","
                        +"\"room_type\":\""+rs.getString("room_type")+"\","
                        +"\"guest_count\":\""+rs.getInt("guest_count")+"\","
                        +"\"check_in\":\""+rs.getTimestamp("check_in")+"\","
                        +"\"check_out\":\""+rs.getTimestamp("check_out")+"\","
                        +"\"total_amount\":\""+rs.getDouble("total_amount")+"\","
                        +"\"checkout_time\":\""+rs.getTimestamp("checkout_time")+"\""
                        +"}";

            }

            json+="]";

            out.print(json);

        }catch(Exception e){

            e.printStackTrace();

        }

    }
}
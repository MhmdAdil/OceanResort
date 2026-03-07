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

@WebServlet("/getReservationIds")
public class GetReservationIdsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        try(Connection con = DBConnection.getConnection()){

            String sql = "SELECT reservation_id FROM reservations";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            StringBuilder json = new StringBuilder("[");

            boolean first = true;

            while(rs.next()){

                if(!first) json.append(",");

                json.append("{\"reservation_id\":\"")
                        .append(rs.getString("reservation_id"))
                        .append("\"}");

                first=false;

            }

            json.append("]");

            out.print(json.toString());

        }

        catch(Exception e){
            e.printStackTrace();
        }

    }
}
package com.oceanresort.servlet;

import com.oceanresort.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/getRoomTypes")
public class GetRoomTypesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        try(Connection con = DBConnection.getConnection()){

            String sql = "SELECT name FROM room_types";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            StringBuilder json = new StringBuilder();

            json.append("[");

            boolean first = true;

            while(rs.next()){

                if(!first){
                    json.append(",");
                }

                json.append("{");

                json.append("\"name\":\"")
                        .append(rs.getString("name"))
                        .append("\"");

                json.append("}");

                first = false;

            }

            json.append("]");

            out.print(json.toString());

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;
import org.json.JSONObject;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) FROM cars");
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            json.put("totalCars", rs1.getInt(1));

            PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM users");
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            json.put("totalUsers", rs2.getInt(1));

            PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) FROM bookings");
            ResultSet rs3 = ps3.executeQuery();
            rs3.next();
            json.put("totalBookings", rs3.getInt(1));

            LocalDate today = LocalDate.now();
            PreparedStatement ps4 = conn.prepareStatement("SELECT COUNT(*) FROM bookings WHERE start_date = ?");
            ps4.setString(1, today.toString());
            ResultSet rs4 = ps4.executeQuery();
            rs4.next();
            json.put("todayBookings", rs4.getInt(1));

        } catch (Exception e) {
            json.put("error", e.getMessage());
        }

        out.print(json.toString());
        out.flush();
    }
}

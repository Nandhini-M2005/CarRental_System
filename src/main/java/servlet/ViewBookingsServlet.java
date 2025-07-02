package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/viewBookings")
public class ViewBookingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONArray bookingList = new JSONArray();

        String email = request.getParameter("email");

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM bookings WHERE user_email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JSONObject b = new JSONObject();
                b.put("id", rs.getInt("id"));
                b.put("car_id", rs.getInt("car_id"));
                b.put("start_date", rs.getDate("start_date"));
                b.put("end_date", rs.getDate("end_date"));
                b.put("status", rs.getString("status"));
                bookingList.put(b);
            }
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("error", "Exception: " + e.getMessage());
            bookingList.put(error);
        }

        out.print(bookingList.toString());
        out.flush();
    }
}

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/adminBookings")
public class AdminBookingServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONArray bookings = new JSONArray();

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM bookings");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("user_email", rs.getString("user_email"));
                obj.put("car_id", rs.getInt("car_id"));
                obj.put("start_date", rs.getDate("start_date").toString());
                obj.put("end_date", rs.getDate("end_date").toString());
                obj.put("status", rs.getString("status"));
                bookings.put(obj);
            }
        } catch (Exception e) {
            JSONObject err = new JSONObject();
            err.put("error", e.getMessage());
            bookings.put(err);
        }

        out.print(bookings.toString());
        out.flush();
    }
}

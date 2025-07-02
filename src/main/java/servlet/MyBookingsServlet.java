package servlet;

import dao.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/myBookings")
public class MyBookingsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");

            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM bookings WHERE user_email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            JSONArray arr = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("car_id", rs.getInt("car_id"));
                obj.put("start_date", rs.getString("start_date"));
                obj.put("end_date", rs.getString("end_date"));
                obj.put("status", rs.getString("status"));
                arr.put(obj);
            }
            out.print(arr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

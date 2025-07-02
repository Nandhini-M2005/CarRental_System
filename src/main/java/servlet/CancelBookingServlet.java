package servlet;

import dao.DBConnection;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {
            int id = Integer.parseInt(request.getParameter("id"));

            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE bookings SET status = 'Cancelled' WHERE id = ?");
            ps.setInt(1, id);
            int result = ps.executeUpdate();

            JSONObject json = new JSONObject();
            json.put("status", result > 0 ? "success" : "error");
            json.put("message", result > 0 ? "Booking cancelled" : "Cancellation failed");

            out.print(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

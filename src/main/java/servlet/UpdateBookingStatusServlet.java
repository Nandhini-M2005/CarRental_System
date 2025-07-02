package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.DBConnection;
import org.json.JSONObject;

@WebServlet("/updateStatus")
public class UpdateBookingStatusServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE bookings SET status = ? WHERE id = ?");
            ps.setString(1, status);
            ps.setInt(2, id);
            int result = ps.executeUpdate();

            json.put("status", result > 0 ? "success" : "error");
            json.put("message", result > 0 ? "Status Updated" : "Failed to update");
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.getMessage());
        }

        out.print(json.toString());
        out.flush();
    }
}

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.DBConnection;
import org.json.JSONObject;

@WebServlet("/bookCar")
public class BookCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        String email = request.getParameter("email");
        int carId = Integer.parseInt(request.getParameter("car_id"));
        String start = request.getParameter("start_date");
        String end = request.getParameter("end_date");

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO bookings(user_email, car_id, start_date, end_date) VALUES (?, ?, ?, ?)");
            ps.setString(1, email);
            ps.setInt(2, carId);
            ps.setString(3, start);
            ps.setString(4, end);

            int result = ps.executeUpdate();

            json.put("status", result > 0 ? "success" : "fail");
            json.put("message", result > 0 ? "Booking Confirmed!" : "Booking Failed!");
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", "Error: " + e.getMessage());
        }

        out.print(json.toString());
        out.flush();
    }
}

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;
import org.json.JSONObject;

@WebServlet("/addCar")
public class AddCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        double price = Double.parseDouble(request.getParameter("price"));

        JSONObject json = new JSONObject();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO cars (brand, model, price_per_day) VALUES (?, ?, ?)");
            ps.setString(1, brand);
            ps.setString(2, model);
            ps.setDouble(3, price);

            int rows = ps.executeUpdate();
            json.put("status", rows > 0 ? "success" : "error");
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", e.getMessage());
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }
}

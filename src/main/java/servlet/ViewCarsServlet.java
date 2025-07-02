package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/viewCars")
public class ViewCarsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray carList = new JSONArray();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cars WHERE availability = true")) {

            while (rs.next()) {
                JSONObject car = new JSONObject();
                car.put("id", rs.getInt("id"));
                car.put("brand", rs.getString("brand"));
                car.put("model", rs.getString("model"));
                car.put("price_per_day", rs.getDouble("price_per_day"));
                carList.put(car);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(carList.toString());
        out.flush();
    }
}

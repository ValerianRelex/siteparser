package task1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import task1.service.ConnectionService;

public class AllNumbersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	ConnectionService connectionService = new ConnectionService();

	req.setAttribute("countryNumbers", connectionService.getCountryNumbers());

	getServletContext().getRequestDispatcher("/tasks/task1.jsp").forward(req, resp);
    }
}

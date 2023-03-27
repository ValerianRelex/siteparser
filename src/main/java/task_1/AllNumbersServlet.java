package task_1;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import task_1.service.ConnectionService;

public class AllNumbersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	ConnectionService cs = new ConnectionService();

	try {
	    req.setAttribute("countryNumbers", cs.getCountryNumbers());
	} catch (URISyntaxException e) {
	    e.printStackTrace();
	}

	getServletContext().getRequestDispatcher("/tasks/task1.jsp").forward(req, resp);
    }
}

package task_2;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import task_2.service.PriceParser;

public class PriceParserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	Map<String, Map<String, String>> price = Collections.EMPTY_MAP;

	try {
	    price = new PriceParser().parse();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	req.setAttribute("price", price);

	getServletContext().getRequestDispatcher("/tasks/task2.jsp").forward(req, res);
    }
}

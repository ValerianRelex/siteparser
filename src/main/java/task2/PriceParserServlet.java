package task2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import task2.service.PriceParser;

public class PriceParserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String price = new PriceParser().parse();

	req.setAttribute("price", price);

	getServletContext().getRequestDispatcher("/tasks/task2.jsp").forward(req, res);
    }
}

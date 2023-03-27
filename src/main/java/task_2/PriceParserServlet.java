package task_2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import task_2.service.PriceParser;

public class PriceParserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String price = null;

	try {
	    price = new PriceParser().startParse();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	getServletContext().getRequestDispatcher("/tasks/task2.jsp").forward(req, res);

	System.out.println("\n\n ==<><><<><><><><><><<>== \n\n" + price);
    }
}

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import model.Number;
import service.ConnectionService;
import service.ParsePrice;

public class MainServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String[] path = req.getRequestURI().split("/");
	String pathId = path[path.length - 1];

	if ("1".equals(pathId)) {
	    ConnectionService cs = new ConnectionService();

	    Map<String, List<Number>> countryNumbers = cs.getCountryNumbers();
	    req.setAttribute("countryNumbers", countryNumbers);

	    // http://localhost:8088/MavenFree_war/api/task/2
	    getServletContext().getRequestDispatcher("/tasks/task1.jsp").forward(req, resp);
	} else if ("2".equals(pathId)) {
	    Date date = new Date(); // TODO: delMe
	    req.setAttribute("date", date.toString()); // TODO: delMe

	    String price = new ParsePrice().startParse();

	    System.out.println("\n\n ==<><><<><><><><><><<>== \n\n" + price);
	}
    }
}

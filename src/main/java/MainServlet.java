import java.io.IOException;
import java.nio.file.InvalidPathException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private static final String TASK_1 = "1";
    private static final String TASK_2 = "2";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	String[] servletPath = req.getServletPath().split("/");
	String pathId = servletPath[servletPath.length - 1];

	switch (pathId) {
	case TASK_1:
	    res.sendRedirect(req.getContextPath() + "/task1");
	    break;
	case TASK_2:
	    res.sendRedirect(req.getContextPath() + "/task2");
	    break;
	default:
	    req.getRequestDispatcher("index.jsp").forward(req, res);
	}
    }
}

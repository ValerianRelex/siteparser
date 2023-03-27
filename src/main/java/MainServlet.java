import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private static final String TASK_1 = "1";
    private static final String TASK_2 = "2";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
	String[] path = req.getServletPath().split("/");
	String pathId = path[path.length - 1];

	if (TASK_1.equals(pathId)) {
	    res.sendRedirect(req.getContextPath() + "/task_1");
	} else if (TASK_2.equals(pathId)) {
	    res.sendRedirect(req.getContextPath() + "/task_2");
	}
    }
}

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
//        Date date = new Date();
//        req.setAttribute("date", date.toString());

        ConnectionService cs = new ConnectionService();

//        String price = cs.readPriceFromUrl(ConnectionService.URL_PRICE_LIST);

//        String price = new ParsePrice().startParse();
//
//        System.out.println("\n\n ==<><><<><><><><><><<>== \n\n" + price);

        Map<String, List<Number>> countryNumbers = cs.getCountryNumbers();

        req.setAttribute("strokaJson", countryNumbers.toString());
        req.getRequestDispatcher("time.jsp").forward(req, resp);
    }
}

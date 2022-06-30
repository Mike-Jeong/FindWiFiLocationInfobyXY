package controller;

import dto.WifiInfoDto;
import service.SearchWifiInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "searchWifiInfoController", value = "/search")
public class SearchWifiInfoController extends HttpServlet {
    public void init() {

        System.out.println("searchWifiInfoController init");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        double lat = Double.parseDouble(request.getParameter("lat"));
        double lnt = Double.parseDouble(request.getParameter("lnt"));

        SearchWifiInfoService searchWifiInfoService = new SearchWifiInfoService();

        ArrayList<WifiInfoDto> wifiInfoList = searchWifiInfoService.getWifiInfoList(lat, lnt);

        if (wifiInfoList == null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('오류가 발생했습니다'); location.href='/';</script>");
            writer.close();
        } else if (wifiInfoList.size() == 0) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('와이파이 정보를 가져온 뒤, 다시 시도해 주세요'); location.href='/';</script>");
            writer.close();
        } else {
            request.setAttribute("list", wifiInfoList);
            request.getRequestDispatcher("/WEB-INF/views/search.jsp").forward(request, response);
        }

    }
}
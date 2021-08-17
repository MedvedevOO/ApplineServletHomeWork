package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {


    Model model = Model.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Model model = Model.getInstance();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));

        if (id == 0) {
            pw.print(gson.toJson(model.getFromList()));

//            for (Map.Entry<Integer, User> entry : model.getFromList().entrySet()) {
//                pw.print("<li>" + entry.getKey() + "</li>" +
//                        "<ul>" +
//                        "<li>Имя: " + entry.getValue().getName() + "</li>" +
//                        "<li>Фамилия: " + entry.getValue().getSurname() + "</li>" +
//                        "<li>Зарплата: " + entry.getValue().getSalary() + "</li>" +
//                        "</ul>");
//            }
//            pw.print("</ul>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
        } else if (id > 0) {
            if(id > model.getFromList().size()) {
                pw.print(new JSONObject("{\"Error\": \"UserNotFound\",\"message\": \"No such user\"}"));
            } else {
                String name = model.getFromList().get(id).getName();
                String surname = model.getFromList().get(id).getSurname();
                double salary = model.getFromList().get(id).getSalary();

                User user = new User(name,surname,salary);

                pw.print(gson.toJson(user));
//                pw.print("<html>" +
//                        "<h3>Запрошенный пользователь:</h3><br/>" +
//                        "Имя: " + model.getFromList().get(id).getName() + "<br/>" +
//                        "Фамилия: " + model.getFromList().get(id).getSurname() + "<br/>" +
//                        "Зарплата: " + model.getFromList().get(id).getSalary() + "<br/>" +
//                        "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
            }
        } else {
            pw.print(new JSONObject("{\"Error\": \"IncorrectUserID\",\"message\": \"User ID must be > 0!\"}"));
//            pw.print("<html>" +
//                    "<h3>ID должен быть > 0!</h3><br/>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
        }
        pw.close();
    }
}

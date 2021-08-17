package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
        StringBuffer jb = new StringBuffer();
        String line;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        }catch (Exception e) {
            System.out.println("Error");
        }

        JsonObject jsonObject = gson.fromJson(String.valueOf(jb), JsonObject.class);
        int id = jsonObject.get("id").getAsInt();

        if (id == 0) {
            pw.print(gson.toJson(model.getFromList()));


        } else if (id > 0) {
            if(id > model.getFromList().size()) {
                pw.print(new JSONObject("{\"Error\": \"UserNotFound\",\"message\": \"No such user\"}"));
            } else {
                String name = model.getFromList().get(id).getName();
                String surname = model.getFromList().get(id).getSurname();
                double salary = model.getFromList().get(id).getSalary();

                User user = new User(name,surname,salary);

                pw.print(gson.toJson(user));

            }
        } else {
            pw.print(new JSONObject("{\"Error\": \"IncorrectUserID\",\"message\": \"User ID must be > 0!\"}"));

        }
        pw.close();
    }
}

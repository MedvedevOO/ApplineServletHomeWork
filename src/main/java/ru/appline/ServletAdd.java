package ru.appline;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

    private AtomicInteger counter = new AtomicInteger(5);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        String name = jsonObject.get("name").getAsString();
        String surname = jsonObject.get("surname").getAsString();
        double salary = jsonObject.get("salary").getAsDouble();

        User user = new User(name,surname,salary);
        model.add(user,counter.getAndIncrement());
        pw.print(gson.toJson(model.getFromList()));

    }

}

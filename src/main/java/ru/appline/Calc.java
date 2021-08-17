package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns =  "/calc")
public class Calc extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
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
            System.out.println("Error occurred while reading request");
        }

        JsonObject jsonObject = gson.fromJson(String.valueOf(jb), JsonObject.class);

        double a = jsonObject.get("a").getAsDouble();
        double b = jsonObject.get("b").getAsDouble();
        String action = jsonObject.get("action").getAsString();
        double result = 0;
        switch (action){
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) {
                    pw.print(new JSONObject("{\"Exception\": \"ArithmeticException\",\"message\": \"DO NOT DIVIDE BY ZERO\"}"));
                    pw.close();
                    break;

                } else {
                    result = a / b;
                    break;
                }

        }

        pw.print(gson.toJson(result));
        pw.close();
    }

}

package com.example.demoweb;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "fileManagerServlet", value = "/files")
public class FileManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Время генерации страницы
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now());
        ZoneId timeZone = ZoneId.systemDefault();
        req.setAttribute("date", date);
        req.setAttribute("timeZone", timeZone);

        // Текущая отображаемая директория
        String path = req.getParameter("path");
        if (path == null) {
            path = System.getProperty("user.dir");
        }
        req.setAttribute("path", path);

        // Переход к родительской директории
        String directorateAtTheTop = new File(path).getParent();
        if (directorateAtTheTop != null) {
            req.setAttribute("directoryVisibilityOnTop", "block");
            req.setAttribute("directorateAtTheTop", directorateAtTheTop);
        } else {
            req.setAttribute("directoryVisibilityOnTop", "none");
        }

        // Таблица с содержимым текущей директории
        outputContentsDir(req, path);

        req.getRequestDispatcher("fileManagerServlet.jsp").forward(req, resp);
    }

    private void outputContentsDir(HttpServletRequest req, String path) throws IOException {
        File f = new File(path);
        File[] allFiles = f.listFiles();

        if (allFiles != null) {
            List<File> directories = new ArrayList<>();
            List<File> files = new ArrayList<>();

            for (File file : allFiles) {
                if (file.getPath() != null) {
                    if (file.isDirectory())
                        directories.add(file);
                    else
                        files.add(file);
                    file.toPath();
                }
            }

            req.setAttribute("directories", directories);
            req.setAttribute("files", files);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("POST method isn't available");
    }
}

<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.nio.file.Files,java.io.File" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>File Manager</title>
  </head>
  <body>
    <p>${date} ${timeZone}</p>
    <h1>${path}</h1>
    <hr />

    <form
      style="display: ${directoryVisibilityOnTop};"
      action="/demo-web-1.0-SNAPSHOT/files"
      method="get"
    >
      <button type="submit" name="path" value="${directorateAtTheTop}">
        Вверх
      </button>
    </form>

    <table>
      <tr>
        <th>Файлы</th>
        <th>Размер</th>
        <th>Даты</th>
      </tr>

      <form action="/demo-web-1.0-SNAPSHOT/files" method="get">
        <c:foreach var="directory" items="${directories}">
          <tr>
            <td>
              <button
                type="submit"
                name="path"
                value="${directory.getAbsolutePath()}"
              >
                ${directory.getName()}
              </button>
            </td>
            <td></td>
            <td>
              <!-- {Files.getAttribute(directory.getPath(), "creationTime").toString()} -->
            </td>
          </tr>
        </c:foreach>
      </form>

      <form action="/demo-web-1.0-SNAPSHOT/download" method="post">
        <c:foreach var="file" items="${files}">
          <tr>
            <td>
              <button type="submit" name="path" value="${file.getPath()}">
                ${file.getName()}
              </button>
            </td>
            <td><!--{Files.size(file.getPath())}B --></td>
            <td>
              <!-- {Files.getAttribute(file.toPath(), "creationTime").toString()} -->
            </td>
          </tr>
        </c:foreach>
      </form>
    </table>
  </body>
</html>

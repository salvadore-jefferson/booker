/*
 * Copyright © 2016 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.server;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple Servlet for testing HTTP Redirects with Apache server.
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 1-13-2016
 *
 */
@WebServlet("/hi")
public class HiServlet extends HttpServlet {

  private static final long serialVersionUID = 11L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("test/plain");
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.sendRedirect("http://localhost:8080/bookerapplication/hello");
  }
}

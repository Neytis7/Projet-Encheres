package fr.eni.encheres.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Encheres;

/**
 * Servlet implementation class Filter
 */
@WebServlet("/Filter")
public class Filter extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private EncheresManager encheresManager;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Filter() {
    super();
    encheresManager = EncheresManager.getInstance();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher rd = request.getRequestDispatcher("./Home");
    if (rd != null) {
      rd.forward(request, response);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String search = "";

    String category = "";

    List<Encheres> filterList = null;

    if (request.getParameter("search") != null) {
      search = String.valueOf(request.getParameter("search"));

      /* Search ON */
      if (!search.isEmpty()) {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));

          /* Case 1 : Search + All */
          if (category.equals("Toutes")) {
            try {
              filterList = encheresManager.visualiserEncheresNameAll(search);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            request.setAttribute("filterList", filterList);
            doGet(request, response);

            /* Case 2 : Search + Category */
          } else {
            try {
              filterList = encheresManager.visualiserEncheresNameCategorie(search, category);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            request.setAttribute("filterList", filterList);
            doGet(request, response);
          }
        }
        /* Search OFF */
        /* Case 3 : Just All */
      } else {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));
          if (category.equals("Toutes")) {
            doGet(request, response);

            /* Case 4 : Just Category */
          } else {
            try {
              filterList = encheresManager.visualiserEncheresCategorie(category);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            request.setAttribute("filterList", filterList);
            doGet(request, response);
          }
        }
      }
    }
  }

}
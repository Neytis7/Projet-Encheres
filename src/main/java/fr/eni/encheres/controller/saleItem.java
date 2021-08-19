package fr.eni.encheres.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.CategoryManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.User;

/**
 * Servlet implementation class saleItem
 */
@WebServlet(description = "Permet à l'utilisateur de vendre un article",
    urlPatterns = {"/sale-item"})
public class saleItem extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public saleItem() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String redirectServlet = "./Home";
    HttpSession session = request.getSession();
    ArrayList<String> errors = new ArrayList<>();
    User userConnected = null;
    UsersManager usersManager = new UsersManager();
    CategoryManager categoryManager = new CategoryManager();
    ArrayList<Category> allCategories = new ArrayList<>();

    if (session != null && session.getAttribute("idUserConnected") != null) {

      int idUserConnected = (int) session.getAttribute("idUserConnected");

      try {
        userConnected = usersManager.getUserById(idUserConnected);
        request.setAttribute("user", userConnected);
      } catch (BLLException bllException) {
        errors.add("An error has occurred while getting your address !");
      }

      try {
        allCategories = categoryManager.getAllCategories();
        request.setAttribute("categories", allCategories);
        redirectServlet = "WEB-INF/sale-item.jsp";
      } catch (BLLException bllException) {
        errors.add("An error has occurred while getting available categories !");
      }
    } else {
      errors.add("You must be connected to see this page");
    }

    redirection(redirectServlet, errors, null, request, response, false);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    UsersManager usersManager = new UsersManager();
    ArticleManager articleManager = new ArticleManager();
    CategoryManager categoryManager = new CategoryManager();
    RetraitManager retraitManager = new RetraitManager();

    User userConnected = null;
    Category category = null;
    Article article = null;
    Retrait retrait = null;
    int idArticle = 0;
    int categoyItem = 0;

    String btnSubmitItem = request.getParameter("btnSubmitItem");
    ArrayList<String> errors = new ArrayList<>();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    HttpSession session = request.getSession();

    if (session != null && session.getAttribute("idUserConnected") != null) {

      int idUserConnected = (int) session.getAttribute("idUserConnected");

      try {
        userConnected = usersManager.getUserById(idUserConnected);
      } catch (BLLException exception) {

        errors.add("An error has occurred while getting your personal informations !");
        redirection("./sign-in", errors, null, request, response, true);
        return;
      }

      if (btnSubmitItem != null) {

        int priceItem = Integer.parseInt(request.getParameter("priceItem"));
        LocalDate startDateItem = LocalDate.parse(request.getParameter("startDateItem"), dtf);
        LocalDate endDateItem = LocalDate.parse(request.getParameter("endDateItem"), dtf);
        String nameItem = request.getParameter("nameItem");
        String descriptionItem = request.getParameter("descriptionItem");
        String adressItemUser = request.getParameter("adressItemUser");
        String zipCodeItemUser = request.getParameter("zipCodeItemUser");
        String cityItemUser = request.getParameter("cityItemUser");

        article = new Article(nameItem, descriptionItem, startDateItem, endDateItem, priceItem, 0,
            userConnected, null, false);
        
        errors = article.checkValueArticle(false);

        try {
          categoyItem = Integer.parseInt(request.getParameter("categoyItem"));
        } catch (NumberFormatException exception) {
          errors.add("Categories loading failed !");
        }

        if (errors.size() > 0) {
          request.setAttribute("errors", errors);
          request.setAttribute("article", article);
          request.setAttribute("idCategory", categoyItem);
          doGet(request, response);
          return;
        }

        try {

          category = categoryManager.getCategoryById(categoyItem);

          if (category != null) {
            article.setCategory(category);            
            idArticle = articleManager.createArticle(article);

            try {
              retrait = new Retrait(idArticle, adressItemUser, cityItemUser, zipCodeItemUser);
              errors = retrait.checkValueRetrait();

              if (errors.size() > 0) {
                request.setAttribute("errors", errors);
                request.setAttribute("article", article);
                doGet(request, response);
                return;
              } else {
                retraitManager.creerRetrait(retrait);
                String success = "Article has been successfully created !";
                redirection("./Home", null, success, request, response, false);
              }
            } catch (BLLException exception) {
              errors.add("An error has occurred while adding withdrawal !");
              return;
            }
          } else {
            throw new BLLException(new Exception("An error has occurred while creating article"));
          }

        } catch (BLLException exception) {
          errors.add("An error has occurred while adding your article !");
        }
      }
    } else {
      errors.add("You must be connected to see this page");
      redirection("./sign-in", errors, null, request, response, true);
      return;
    }
  }

  public void redirection(String path, ArrayList<String> paramError, String paramSuccess,
      HttpServletRequest request, HttpServletResponse response, boolean exitSession)
      throws ServletException, IOException {

    if (request != null) {

      if (exitSession) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("idUserConnected") != null) {
          session.invalidate();
        }
      }

      if (request.getAttribute("errors") != null) {
        @SuppressWarnings("unchecked")
        ArrayList<String> otherErrors = (ArrayList<String>) request.getAttribute("errors");
        for (String error : otherErrors) {
          paramError.add(error);
        }
      }
      request.setAttribute("errors", paramError);
      request.setAttribute("success", paramSuccess);
      RequestDispatcher rd = request.getRequestDispatcher(path);

      rd.forward(request, response);
    }
  }
}

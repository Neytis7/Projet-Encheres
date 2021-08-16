package fr.eni.encheres.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.CategoryManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;

/**
 * Servlet implementation class VisualiserListesEncheres
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private ArticleManager articleManager;
  private CategoryManager categoryManager;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Home() {
    super();
    articleManager = ArticleManager.getInstance();
    categoryManager = CategoryManager.getInstance();

  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<Category> listCategories = null;
    try {
      listCategories = categoryManager.getAllCategories();
      request.setAttribute("listCategories", listCategories);
    } catch (BLLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    if (request.getAttribute("noArticle") != null
        && request.getAttribute("noArticle").equals(true)) {
      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Home.jsp");
      if (rd != null) {
        rd.forward(request, response);
        return;
      }
    }

    List<Article> listArticles = null;

    try {
      listArticles = articleManager.getArticlesInProgress();
      listArticles = articleManager.getPrice(listArticles);

    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    request.setAttribute("listArticles", listArticles);

    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Home.jsp");
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
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}

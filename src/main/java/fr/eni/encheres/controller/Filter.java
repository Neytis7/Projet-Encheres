package fr.eni.encheres.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.Article;

/**
 * Servlet implementation class Filter
 */
@WebServlet("/Filter")
public class Filter extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private ArticleManager articleManager;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Filter() {
    super();
    articleManager = ArticleManager.getInstance();
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

    HttpSession session = request.getSession();

    if (session != null && session.getAttribute("idUserConnected") != null) {
      int idUserConnected = (int) session.getAttribute("idUserConnected");

      if (request.getParameter("btnRadioGroup") != null) {
        String choice = request.getParameter("btnRadioGroup");

        if (choice.equals("vente")) {
          List<Article> filterList = new ArrayList<Article>();
          String[] tab = request.getParameterValues("checkbox_vente");
          if (tab != null) {
            if (tab[0].equals("venteEnCours")) {
              checkBoxSellInProgress(filterList, request, idUserConnected);
            } else if (tab[0].equals("ventesNonDebutees")) {
              checkBoxSellNotBegin(filterList, request, idUserConnected);
            } else if (tab[0].equals("ventesTerminees")) {
              checkBoxSellTerminate(filterList, request, idUserConnected);
            }
          } else {
            createFilterList(request);
          }
        } else {
          List<Article> filterList = new ArrayList<Article>();
          String[] tab = request.getParameterValues("checkbox_achat");
          if (tab != null) {
            if (tab[0].equals("enchereEnOuvertes")) {
              createFilterList(request);
            } else if (tab[0].equals("enchereEnCours")) {
              checkBoxAuctionInProgress(filterList, request, idUserConnected);
            } else if (tab[0].equals("enchereRemportees")) {
              checkBoxAuctionWin(filterList, request, idUserConnected);
            }
          } else {
            createFilterList(request);
          }
        }
      } else {
        createFilterList(request);
      }
    } else {
      createFilterList(request);
    }
    doGet(request, response);
  }

  private HttpServletRequest checkBoxAuctionWin(List<Article> filterList,
      HttpServletRequest request, int idUserConnected) {
    String search = "";
    String category = "";
    if (request.getParameter("search") != null) {
      search = String.valueOf(request.getParameter("search"));

      /* Search ON */
      if (!search.isEmpty()) {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));

          /* Case 1 : Search + All */
          if (category.equals("All")) {
            try {
              filterList = articleManager.FilterConnectedSearchAuctionWin(search, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 2 : Search + Category */
          } else {
            try {
              filterList = articleManager.FilterConnectedSearchCategoryAuctionWin(search, category,
                  idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
        /* Search OFF */
      } else {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));
          /* Case 3 : Just Category */
          if (!category.equals("All")) {
            try {
              filterList = articleManager.FilterCategoryAuctionWin(category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 4 : Just checkBox */
          } else {
            try {
              filterList = articleManager.FilterMyAuctionWin(idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
      }
    }
    return request;

  }

  private HttpServletRequest checkBoxAuctionInProgress(List<Article> filterList,
      HttpServletRequest request, int idUserConnected) {
    String search = "";
    String category = "";
    if (request.getParameter("search") != null) {
      search = String.valueOf(request.getParameter("search"));

      /* Search ON */
      if (!search.isEmpty()) {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));

          /* Case 1 : Search + All */
          if (category.equals("All")) {
            try {
              filterList =
                  articleManager.FilterConnectedSearchAuctionInProgress(search, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 2 : Search + Category */
          } else {
            try {
              filterList = articleManager.FilterConnectedSearchCategoryAuctionInProgress(search,
                  category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
        /* Search OFF */
      } else {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));
          /* Case 3 : Just Category */
          if (!category.equals("All")) {
            try {
              filterList =
                  articleManager.FilterCategoryAuctionInProgress(category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 4 : Just checkBox */
          } else {
            try {
              filterList = articleManager.FilterMyAuctionInProgress(idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
      }
    }
    return request;

  }

  private HttpServletRequest checkBoxSellTerminate(List<Article> filterList,
      HttpServletRequest request, int idUserConnected) {
    String search = "";
    String category = "";
    if (request.getParameter("search") != null) {
      search = String.valueOf(request.getParameter("search"));

      /* Search ON */
      if (!search.isEmpty()) {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));

          /* Case 1 : Search + All */
          if (category.equals("All")) {
            try {
              filterList =
                  articleManager.FilterConnectedSearchMySellTerminate(search, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 2 : Search + Category */
          } else {
            try {
              filterList = articleManager.FilterConnectedSearchCategoryMySellTerminate(search,
                  category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
        /* Search OFF */
      } else {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));
          /* Case 3 : Just Category */
          if (!category.equals("All")) {
            try {
              filterList = articleManager.FilterCategoryMySellTerminate(category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 4 : Just checkBox */
          } else {
            try {
              filterList = articleManager.FilterMySellTerminate(idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
      }
    }
    return request;

  }

  private HttpServletRequest checkBoxSellNotBegin(List<Article> filterList,
      HttpServletRequest request, int idUserConnected) {
    String search = "";
    String category = "";
    if (request.getParameter("search") != null) {
      search = String.valueOf(request.getParameter("search"));

      /* Search ON */
      if (!search.isEmpty()) {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));

          /* Case 1 : Search + All */
          if (category.equals("All")) {
            try {
              filterList =
                  articleManager.FilterConnectedSearchMySellNotBegin(search, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 2 : Search + Category */
          } else {
            try {
              filterList = articleManager.FilterConnectedSearchCategoryMySellNotBegin(search,
                  category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
        /* Search OFF */
      } else {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));
          /* Case 3 : Just Category */
          if (!category.equals("All")) {
            try {
              filterList = articleManager.FilterCategoryMySellNotBegin(category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 4 : Just checkBox */
          } else {
            try {
              filterList = articleManager.FilterMySellNotBegin(idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
      }
    }
    return request;

  }

  private HttpServletRequest checkBoxSellInProgress(List<Article> filterList,
      HttpServletRequest request, int idUserConnected) {

    String search = "";
    String category = "";
    if (request.getParameter("search") != null) {
      search = String.valueOf(request.getParameter("search"));

      /* Search ON */
      if (!search.isEmpty()) {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));

          /* Case 1 : Search + All */
          if (category.equals("All")) {
            try {
              filterList =
                  articleManager.FilterConnectedSearchMySellInProgress(search, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 2 : Search + Category */
          } else {
            try {
              filterList = articleManager.FilterConnectedSearchCategoryMySellInProgress(search,
                  category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
        /* Search OFF */
      } else {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));
          /* Case 3 : Just Category */
          if (!category.equals("All")) {
            try {
              filterList = articleManager.FilterCategoryMySellInProgress(category, idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
            /* Case 4 : Just checkBox */
          } else {
            try {
              filterList = articleManager.FilterMySellInProgress(idUserConnected);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
      }
    }
    return request;
  }

  public HttpServletRequest createFilterList(HttpServletRequest request) {
    String search = "";
    String category = "";
    List<Article> filterList = null;

    if (request.getParameter("search") != null) {
      search = String.valueOf(request.getParameter("search"));

      /* Search ON */
      if (!search.isEmpty()) {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));

          /* Case 1 : Search + All */
          if (category.equals("All")) {
            try {
              filterList = articleManager.FilterNameAll(search);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }

            /* Case 2 : Search + Category */
          } else {
            try {
              filterList = articleManager.FilterNameCategory(search, category);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
        /* Search OFF */
      } else {
        if (request.getParameter("category") != null) {
          category = String.valueOf(request.getParameter("category"));
          /* Case 3 : Just Category */
          if (!category.equals("All")) {
            try {
              filterList = articleManager.FilterCategory(category);
              filterList = articleManager.getPrice(filterList);
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }
            if (filterList != null && filterList.size() > 0) {
              request.setAttribute("filterList", filterList);
            } else {
              request.setAttribute("noArticle", true);
            }
          }
        }
      }
    }
    return request;
  }
}



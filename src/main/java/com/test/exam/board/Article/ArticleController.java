package com.test.exam.board.Article;

import com.test.exam.board.Rq;
import com.test.exam.board.Util;
import com.test.exam.board.Article.dto.Article;
import com.test.exam.board.Container.Container;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ArticleController {
  int articleLastId;
  List<Article> articles;

  public ArticleController() {
    articleLastId = 0;
    articles = new ArrayList<>();

    makeTestData();

    if (articles.size() > 0) {
      articleLastId = articles.get(articles.size() - 1).id;
    }
  }

  void makeTestData() {
    // 테스트 게시물을 100개로 늘림
    IntStream.rangeClosed(1, 100)
        .forEach(
            i -> articles.add(new Article(i, "제목" + i, "내용" + i)
            )
        );
  }

  public void actionWrite(Rq rq) {
    System.out.println("== 게시물 작성 ==");
    System.out.printf("제목 : ");
    String title = com.test.exam.board.Container.Container.sc.nextLine();

    System.out.printf("내용 : ");
    String body = com.test.exam.board.Container.Container.sc.nextLine();

    int id = ++articleLastId;

    Article article = new Article(id, title, body);

    articles.add(article);
    System.out.printf("%d번 게시물이 생성되었습니다.\n", article.id);
  }

  public void showList(Rq rq) {
    Map<String, String> params = rq.getParams();

    System.out.println("== 게시물 리스트 ==");
    System.out.println("===================");
    System.out.println("번호 / 제목");
    System.out.println("===================");

    String searchKeyword = rq.getParam("searchKeyword", "");

    // articles : 정렬되지 않은 리모콘의 복사본(객체 주소) 있다.
    List<Article> filteredArticles = articles;

    // 검색 기능 시작
    if (searchKeyword.length() > 0) {
      filteredArticles = new ArrayList<>();

      for (Article article : articles) {
        boolean matched = article.title.contains(searchKeyword) || article.body.contains(searchKeyword);

        if (matched) {
          filteredArticles.add(article);
        }
      }
    }
    // 검색 기능 끝

    // 정렬 기능 시작
    List<Article> sortedArticles = filteredArticles;

    String orderBy = rq.getParam("orderBy", "idDesc");
    boolean orderByIdDesc = orderBy.equals("idDesc");
    if (params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
      orderByIdDesc = false;
    }

    if (orderByIdDesc) {
      sortedArticles = Util.reverseList(sortedArticles);
    }
    // 정렬 기능 끝

    for (Article article : sortedArticles) {
      System.out.printf("%d / %s\n", article.id, article.title);
    }
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
    }

    Article article = findById(id, articles);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("== 게시물 상세내용 ==");
    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.body);
  }

  private Article findById(int id, List<Article> articles) {
    for (Article article : articles) {
      if (article.id == id) {
        return article;
      }
    }

    return null;
  }

  public void actionModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = findById(id, articles);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    article.title = com.test.exam.board.Container.Container.sc.nextLine();
    System.out.printf("새 내용 : ");
    article.body = com.test.exam.board.Container.Container.sc.nextLine();

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void actionDelete(Rq rq) {
    Map<String, String> params = rq.getParams();

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    if (params.containsKey("id") == false) {
      System.out.println("id를 입력해주세요.");
      return;
    }

    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    } catch (NumberFormatException e) {
      System.out.println("id를 정수형태로 입력해주세요.");
      return;
    }

    Article article = findById(id, articles);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articles.remove(article);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
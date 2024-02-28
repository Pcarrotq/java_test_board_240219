package com.test.exam.board.Container;

import com.test.exam.board.Article.ArticleController;
import com.test.exam.board.member.MemberController;
import com.test.exam.board.session.Session;

import java.util.Scanner;

public class Container {
  public static Scanner sc;
  public static Session session;

  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    sc = new Scanner(System.in);
    session = new Session();

    memberController = new MemberController();
    articleController = new ArticleController();
  }

  public static Session getSession() {
    return session;
  }
}
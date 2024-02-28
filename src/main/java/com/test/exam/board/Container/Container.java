package com.test.exam.board.Container;

import com.test.exam.board.Article.ArticleController;
import com.test.exam.board.member.MemberController;

import java.util.Scanner;

public class Container {
  public static Scanner sc;

  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    sc = new Scanner(System.in);

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
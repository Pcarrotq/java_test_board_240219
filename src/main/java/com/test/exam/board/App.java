package com.test.exam.board;

import com.test.exam.board.Container.Container;
import com.test.exam.board.member.dto.Member;
import com.test.exam.board.session.Session;

import java.util.Scanner;

public class App {
  public void run() {
    System.out.println("== 자바 텍스트 게시판 0.1v ==");
    System.out.println("== 자바 텍스트 게시판 시작 ==");

    Scanner sc = Container.sc;
    Session session = Container.getSession();

    while (true) {
      Member loginedMember = (Member)session.getAttribute("loginedMember");

      String promptName = "명령";

      if (loginedMember != null) {
        promptName = loginedMember.loginId;
      }

      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      if (rq.getUrlPath().equals("/usr/article/write")) {
        Container.articleController.actionWrite(rq);
      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        Container.articleController.showList(rq);
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        Container.articleController.showDetail(rq);
      } else if (rq.getUrlPath().equals("/usr/article/modify")) {
        Container.articleController.actionModify(rq);
      } else if (rq.getUrlPath().equals("/usr/article/delete")) {
        Container.articleController.actionDelete(rq);
      } else if (rq.getUrlPath().equals("/usr/member/join")) {
        Container.memberController.actionJoin(rq);
      } else if (rq.getUrlPath().equals("/usr/member/login")) {
        Container.memberController.actionJoin(rq);
      } else if (rq.getUrlPath().equals("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;
      }
    }

    System.out.println("== 자바 텍스트 게시판 종료 ==");
    sc.close();
  }
}
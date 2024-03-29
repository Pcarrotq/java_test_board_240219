package com.test.exam.board;

import com.test.exam.board.Container.Container;
import com.test.exam.board.member.dto.Member;
import com.test.exam.board.session.Session;

import java.util.Map;

public class Rq {
  String url;
  Map<String, String> params;
  String urlPath;

  Rq(String url) {
    this.url = url;
    params = Util.getParamsFromUrl(url);
    urlPath = Util.getUrlPathFromUrl(url);
  }

  public Map getParams() {
    return params;
  }

  public String getUrlPath() {
    return urlPath;
  }

  public String getParam(String paramName, String defaultValue) {
    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    return params.get(paramName);
  }

  public int getIntParam(String paramName, int defaultValue) {
    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public void setSessionAttr(String key, Member value) {
    Session session = Container.getSession();

    session.setAttribute(key, value);
  }

  public void removeSessionAttr(String loginMember) {
    Session session = Container.getSession();

    session.removeAttribute(loginMember);
  }

  public boolean isLogined(String loginedMember) {
    Session session = Container.getSession();

    return session.hasAttribute(loginedMember);
  }

  public boolean isLogout(String loginedMember) {
    return !isLogined(loginedMember);
  }
}
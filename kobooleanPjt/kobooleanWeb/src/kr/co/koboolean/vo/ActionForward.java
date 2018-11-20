package kr.co.koboolean.vo;
//포워딩에 필요한 정보를 다루는 클래스
public class ActionForward {
 private String url;
 private boolean redirect;
 public String getUrl() {
    return url;
 }
 public void setUrl(String url) {
    this.url = url;
 }
 public boolean isRedirect() {
    return redirect;
 }
 public void setRedirect(boolean redirect) {
    this.redirect = redirect;
 }
}
<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%@ page import="site.springbike.view.CompanyRentedBikeView" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 20/05/2020
  Time: 06:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../templates/header.jsp" %>
<body>
<%@ include file="../templates/navbar.jsp" %>

<%
    String error = (String) request.getParameter("error");
    List<CompanyRentedBikeView> bikeViews = (List<CompanyRentedBikeView>) request.getAttribute("bikeViews");
    if (bikeViews == null) bikeViews = new ArrayList<>();
    Integer totalLeases = (Integer) request.getAttribute("totalLeases");
    if (totalLeases == null) totalLeases = bikeViews.size();
%>
<main id="main">
    <section>
        <div class="row" style="margin-left: 30px;margin-right: 30px;">
            <div class="col-lg-12">
                <div style="margin-top: 50px">
                    <h1 style="text-align: center;">Total profit: ${user.getBalance()} RON</h1>
                    <br/>
                    <br/>
                    <h1 style="text-align: center;"><% out.print(bikeViews.size() + " bike" + (bikeViews.size() != 1 ? "s" : "") + " rented right now"); %></h1>
                    <div style="text-align: center">
                        <div class="form-validate"><% if (error != null)
                            out.print(HtmlUtils.htmlEscape(error)); %></div>
                    </div>
                    <%
                        for (CompanyRentedBikeView bikeView : bikeViews) {
                    %>
                    <br/>
                    <div class="row row-bike-rent" style="border-bottom: 1px solid #63a91f;">
                        <%= bikeView.toString() %>
                    </div>
                    <%
                        }
                    %>
                    <br/>
                    <br/>
                    <h1 style="text-align: center;"><% out.print((totalLeases - bikeViews.size()) + " bike" + (totalLeases - bikeViews.size() != 1 ? "s" : "") + " rented in the past"); %></h1>
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-3"></div>
                            <div class="col-lg-6">
                                <h3>Search rent history</h3><br/>
                                <form method="POST" action="/company/rent_history">
                                    <label for="date_start">Date start</label><br/>
                                    <input class="form-control" type="datetime-local" id="date_start" name="date_start"><br/>
                                    <label for="date_finish">Date finish</label><br/>
                                    <input class="form-control" type="datetime-local" id="date_finish"
                                           name="date_finish"><br/>
                                    <button type="submit">Submit</button>
                                </form>
                            </div>
                            <div class="col-lg-3"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<%@ include file="../templates/footer.jsp" %>
</body>
</html>
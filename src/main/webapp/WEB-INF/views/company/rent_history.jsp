<%@ page import="java.util.ArrayList" %>
<%@ page import="site.springbike.view.CompanyRentedBikeView" %>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 20/05/2020
  Time: 06:41
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
    Timestamp dateStart = (Timestamp) request.getAttribute("dateStart");
    Timestamp dateFinish = (Timestamp) request.getAttribute("dateFinish");
%>
<main id="main">
    <section>
        <div class="row" style="margin-left: 30px;margin-right: 30px;">
            <div class="col-lg-12">
                <div style="margin-top: 50px">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-3"></div>
                            <div class="col-lg-6">
                                <a href="/company/reports">Go back</a>
                                <h3>Search rent history</h3><br/>
                                <form method="POST" action="/company/rent_history">
                                    <label for="date_start">Date start</label><br/>
                                    <input class="form-control" type="datetime-local" id="date_start" name="date_start"
                                           value="<% if (dateStart!=null) out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateStart).replace(" ","T")); %>"><br/>
                                    <label for="date_finish">Date finish</label><br/>
                                    <input class="form-control" type="datetime-local" id="date_finish"
                                           name="date_finish"
                                           value="<% if (dateFinish!=null) out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateFinish).replace(" ","T")); %>"><br/>
                                    <button type="submit">Submit</button>
                                </form>
                            </div>
                            <div class="col-lg-3"></div>
                        </div>
                    </div>
                    <br/>
                    <br/>
                    <h1 style="text-align: center;"><% out.print(bikeViews.size() + " result" + (bikeViews.size() != 1 ? "s" : "")); %></h1>
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
                </div>
            </div>
        </div>
    </section>
</main>
<%@ include file="../templates/footer.jsp" %>
</body>
</html>

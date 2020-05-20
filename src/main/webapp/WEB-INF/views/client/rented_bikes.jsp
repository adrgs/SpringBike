<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%@ page import="site.springbike.view.ClientRentedBikeView" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 19/05/2020
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../templates/header.jsp" %>
<body>
<%@ include file="../templates/navbar.jsp" %>

<%
    String error = (String) request.getParameter("error");
%>
<main id="main">
    <section>
        <div class="row" style="margin-left: 30px;margin-right: 30px;">
            <div class="col-lg-12">
                <div style="margin-top: 50px">
                    <h1 style="text-align: center;">My rented bikes</h1>
                    <div style="text-align: center">
                        <div class="form-validate"><% if (error != null)
                            out.print(HtmlUtils.htmlEscape(error)); %></div>
                    </div>
                    <%
                        List<ClientRentedBikeView> bikeViews = (List<ClientRentedBikeView>) request.getAttribute("bikeViews");
                        if (bikeViews == null) bikeViews = new ArrayList<>();
                        for (ClientRentedBikeView bikeView : bikeViews) {
                    %>
                    <br/>
                    <div class="row row-bike-rent" style="border-bottom: 1px solid #63a91f;">
                        <%= bikeView.toString() %>
                    </div>
                    <%
                        }
                        if (bikeViews.size() == 0) {
                    %>
                    <div style="text-align: center"><h3>No bikes rented right now</h3></div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </section>
</main>
<%@ include file="../templates/footer.jsp" %>
</body>
</html>
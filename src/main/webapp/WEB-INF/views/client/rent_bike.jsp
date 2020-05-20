<%@ page import="site.springbike.view.RentBikeView" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.springframework.web.util.HtmlUtils" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 19/05/2020
  Time: 18:07
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
                    <h1 style="text-align: center;">Rent a bike</h1>
                    <div style="text-align: center">
                        <div class="form-validate"><% if (error != null)
                            out.print(HtmlUtils.htmlEscape(error)); %></div>
                    </div>
                    <%
                        List<RentBikeView> bikeViews = (List<RentBikeView>) request.getAttribute("bikeViews");
                        if (bikeViews == null) bikeViews = new ArrayList<>();
                        for (RentBikeView bikeView : bikeViews) {
                    %>
                    <br/>
                    <div class="row row-bike-rent" style="border-bottom: 1px solid #63a91f;">
                        <%= bikeView.toString() %>
                    </div>
                    <%
                        }
                        if (bikeViews.size() == 0) {
                    %>
                    <div style="text-align: center"><h3>No bikes available to rent</h3></div>
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
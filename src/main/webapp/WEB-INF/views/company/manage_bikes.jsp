<%@ page import="site.springbike.view.InventoryBikeTypeView" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 15/05/2020
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../templates/header.jsp" %>
<body>
<%@ include file="../templates/navbar.jsp" %>

<main id="main">
    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div style="margin-top: 50px">
                        <h1 style="text-align: center;">Manage bikes</h1>
                        <a href="/company/add_bike">Add bike</a>
                        <div class="container">
                            <div class="row">
                        <%
                            for (InventoryBikeTypeView bikeView : (List<InventoryBikeTypeView>) request.getAttribute("bikeViews")) {
                        %>
                                <div class="col-lg-3">
                                    <%= bikeView.toString() %>
                                </div>
                        <%
                            }
                        %>
                            </div>
                        </div>
                        <div class="form-validate">${error}</div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<%@ include file="../templates/footer.jsp" %>
</body>
</html>
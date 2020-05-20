<%@ page import="site.springbike.view.ModelViewBuilder" %>
<%@ page import="site.springbike.model.*" %><%--
  Created by IntelliJ IDEA.
  User: Andrei
  Date: 15/05/2020
  Time: 19:13
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
                <div class="col-lg-3"></div>
                <div class="col-lg-6">
                    <div style="margin-top: 50px">
                        <h1 style="text-align: center;">Add bike</h1>
                        <div class="form-validate">${error}</div>
                        <a href="/company/manage_bikes">Go back</a>
                        <%
                            out.print(ModelViewBuilder.useModel(new Bike()).addInputs(new BikeType()).addInputs(new Inventory()).addInput("quantity", "number", true, "1").generateForm(null));
                        %>
                    </div>
                </div>
                <div class="col-lg-3"></div>
            </div>
        </div>
    </section>
</main>
<%@ include file="../templates/footer.jsp" %>
</body>
</html>
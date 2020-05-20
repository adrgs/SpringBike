<%@ page import="site.springbike.view.ModelViewBuilder" %>
<%@ page import="site.springbike.model.Bike" %>
<%@ page import="site.springbike.model.BikeType" %>
<%@ page import="site.springbike.model.Inventory" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 19/05/2020
  Time: 15:16
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
                        <h1 style="text-align: center;">Edit bike</h1>
                        <div class="form-validate">${error}</div>
                        <a href="/company/manage_bikes">Go back</a>
                        <%
                            Bike bike = (Bike) request.getAttribute("bike");
                            BikeType type = (BikeType) request.getAttribute("type");
                            Inventory inventory = (Inventory) request.getAttribute("inventory");
                            out.print(ModelViewBuilder.useModelToEdit(bike).addEditInputs(type).addEditInputs(inventory).generateForm(null));
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
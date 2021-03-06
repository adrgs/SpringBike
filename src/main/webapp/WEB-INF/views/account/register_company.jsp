<%@ page import="site.springbike.view.ModelViewBuilder" %>
<%@ page import="site.springbike.model.Company" %>
<%@ page import="site.springbike.model.Address" %>
<%@ page import="site.springbike.model.Location" %><%--
  Created by IntelliJ IDEA.
  User: Andrei
  Date: 14/05/2020
  Time: 19:35
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
                        <h1 style="text-align: center;">Register company</h1>
                        <%
                            out.print(ModelViewBuilder.useModel(new Company()).addInputs(new Address()).addInputs(new Location()).generateForm(null));
                        %>
                        <div class="form-validate">${error}</div>
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
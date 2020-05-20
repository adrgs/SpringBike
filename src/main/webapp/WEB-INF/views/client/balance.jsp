<%@ page import="site.springbike.view.ModelViewBuilder" %>
<%@ page import="site.springbike.model.Coupon" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 19/05/2020
  Time: 15:53
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
                        <h1 style="text-align: center;">Balance: ${user.balance}</h1>
                        <div class="container">
                            <div class="row">
                                <h3>Redeem coupon</h3><br/>
                                <%
                                    out.print(ModelViewBuilder.useModel(new Coupon()).generateForm(null));
                                %>
                                <div class="form-validate">${error}</div>
                            </div>
                        </div>
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
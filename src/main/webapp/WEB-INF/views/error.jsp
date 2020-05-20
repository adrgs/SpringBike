<%@ page import="site.springbike.controller.ControllerUtils" %><%--
  Created by IntelliJ IDEA.
  User: Andrei
  Date: 14/05/2020
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="templates/header.jsp" %>
<body>
<%
    request.setAttribute("user", ControllerUtils.checkAuthentication(request));
%>
<%@ include file="templates/navbar.jsp" %>

<main id="main">
    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div style="text-align: center; margin-top: 50px">
                        <h1>404 - Page not found</h1>
                        <p>You will soon be redirected to <a href="/index">/index</a></p>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<script>
    setTimeout(function () {
        document.location = "/index";
    }, 5000);
</script>
<%@ include file="templates/footer.jsp" %>
</body>
</html>
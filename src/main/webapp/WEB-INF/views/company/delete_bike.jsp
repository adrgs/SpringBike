<%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 19/05/2020
  Time: 14:38
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
                        <h1 style="text-align: center;">Bike deleted</h1>
                        <div class="form-validate">${error}</div>
                        <p>You will soon be redirected to <a href="/company/manage_bikes">Manage bikes</a></p>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<script>
    setTimeout(function () {
        document.location = "/company/manage_bikes";
    }, 5000);
</script>
<%@ include file="../templates/footer.jsp" %>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 15/05/2020
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../templates/header.jsp" %>
<body>
<%@ include file="../templates/navbar.jsp" %>

<%
    Cookie cookie = null;
    Cookie[] cookies = null;

    cookies = request.getCookies();

    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
            if (!cookies[i].getName().equals("JSESSIONID")) {
                cookie = cookies[i];
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }
%>

<main id="main">
    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-3"></div>
                <div class="col-lg-6">
                    <div style="margin-top: 50px">
                        <h1 style="text-align: center;">Logout completed</h1>
                        <p>You will soon be redirected to <a href="/index">/index</a></p>
                    </div>
                </div>
                <div class="col-lg-3"></div>
            </div>
        </div>
    </section>
</main>
<script>
    setTimeout(function () {
        document.location = "/index";
    }, 5000);
</script>
<%@ include file="../templates/footer.jsp" %>
</body>
</html>
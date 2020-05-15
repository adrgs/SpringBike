<%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 14/05/2020
  Time: 18:57
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
                        <h1 style="text-align: center;">Login</h1>
                        <form method="POST">
                            <label for="name">Username/Email*</label><br/>
                            <input class="form-control" type="text" id="name" name="name" required=""><br/>
                            <label for="password">Password*</label><br/>
                            <input class="form-control" type="password" id="password" name="password" required=""><br/>
                            <button type="submit">Submit</button>
                        </form>
                        <div class="validate">${error}</div>
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
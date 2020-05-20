<%@ page import="site.springbike.model.User" %>
<%@ page import="site.springbike.cache.UserCacheManager" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 14/05/2020
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- ======= Header ======= -->
<header id="header" class="fixed-top">
    <div class="container d-flex align-items-center">

        <a href="/index" class="logo mr-auto"><img src="/img/logo.png" alt="" class="img-fluid"></a>
        <!-- Uncomment below if you prefer to use text as a logo -->
        <!-- <h1 class="logo mr-auto"><a href="index.html">Butterfly</a></h1> -->

        <nav class="nav-menu d-none d-lg-block">
            <ul>
                <li class="${title.equals("Index") ? "active" : ""}"><a href="/index">Home</a></li>
                <%
                    if (request.getAttribute("user") == null) {
                %>
                <li class="${title.equals("Login") ? "active" : ""}"><a href="/account/login">Login</a></li>
                <li class="drop-down"><a href="">Register</a>
                    <ul>
                        <li class="${title.equals("Register Client") ? "active" : ""}"><a
                                href="/account/register/client">Client</a></li>
                        <li class="${title.equals("Register Company") ? "active" : ""}"><a
                                href="/account/register/company">Company</a></li>
                    </ul>
                </li>
                <%
                } else {
                %>

                <%
                    User user = (User) request.getAttribute("user");
                    if (user.getType().equals("Client")) {
                %>
                <li class="${title.equals("Rent a bike") ? "active" : ""}"><a href="/client/rent_bike">Rent a bike</a>
                </li>
                <li class="${title.equals("My transaction history") ? "active" : ""}"><a
                        href="/client/transaction_history">Transaction history</a></li>
                <li class="${title.equals("My rented bikes") ? "active" : ""}"><a href="/client/rented_bikes">My rented
                    bikes</a></li>
                <li><a href="/client/balance">Balance: ${user.getBalance()}</a></li>
                <%
                } else if (user.getType().equals("Company")) {
                %>
                <li class="${title.equals("Manage bikes") ? "active" : ""}"><a href="/company/manage_bikes">Manage
                    bikes</a></li>
                <li class="${title.equals("Reports") ? "active" : ""}"><a href="/company/reports">Reports</a></li>
                <li class="${title.equals("Manage clients") ? "active" : ""}"><a href="/company/manage_clients">Manage
                    clients</a></li>
                <%
                    }
                %>

                <li><a href="#">Hello, ${user.getUsername()}</a></li>
                <li class="${title.equals("Logout") ? "active" : ""}"><a href="/account/logout">Logout</a></li>
                <%
                    }
                %>
            </ul>
        </nav><!-- .nav-menu -->

    </div>
</header>
<!-- End Header -->
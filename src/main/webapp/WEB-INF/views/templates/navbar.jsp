<%--
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

        <a href="index" class="logo mr-auto"><img src="/img/logo.png" alt="" class="img-fluid"></a>
        <!-- Uncomment below if you prefer to use text as a logo -->
        <!-- <h1 class="logo mr-auto"><a href="index.html">Butterfly</a></h1> -->

        <nav class="nav-menu d-none d-lg-block">
            <ul>
                <li class="${title.equals("Index") ? "active" : ""}"><a href="/index">Home</a></li>
                <li class="${title.equals("Login") ? "active" : ""}"><a href="/account/login">Login</a></li>
                <li class="drop-down"><a href="">Register</a>
                    <ul>
                        <li class="${title.equals("Register Client") ? "active" : ""}"><a
                                href="/account/register/client">Client</a></li>
                        <li class="${title.equals("Register Company") ? "active" : ""}"><a
                                href="/account/register/company">Company</a></li>
                    </ul>
                </li>
            </ul>
        </nav><!-- .nav-menu -->

    </div>
</header>
<!-- End Header -->
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
                <li class="${title=="Index" ? "active" : ""}"><a href="/index">Home</a></li>
                <li class="${title=="About" ? "active" : ""}"><a href="/about">About</a></li>
                <li class="${title=="Services" ? "active" : ""}"><a href="/services">Services</a></li>
                <li class="${title=="Portofolio" ? "active" : ""}"><a href="/portfolio">Portfolio</a></li>
                <li class="${title=="Team" ? "active" : ""}"><a href="/team">Team</a></li>
                <li class="drop-down"><a href="">Drop Down</a>
                    <ul>
                        <li><a href="#">Drop Down 1</a></li>
                        <li class="drop-down"><a href="#">Deep Drop Down</a>
                            <ul>
                                <li><a href="#">Deep Drop Down 1</a></li>
                                <li><a href="#">Deep Drop Down 2</a></li>
                                <li><a href="#">Deep Drop Down 3</a></li>
                                <li><a href="#">Deep Drop Down 4</a></li>
                                <li><a href="#">Deep Drop Down 5</a></li>
                            </ul>
                        </li>
                        <li><a href="#">Drop Down 2</a></li>
                        <li><a href="#">Drop Down 3</a></li>
                        <li><a href="#">Drop Down 4</a></li>
                    </ul>
                </li>
                <li class="${title=="Team" ? "active" : ""}"><a href="/contact">Contact</a></li>

            </ul>
        </nav><!-- .nav-menu -->

    </div>
</header>
<!-- End Header -->
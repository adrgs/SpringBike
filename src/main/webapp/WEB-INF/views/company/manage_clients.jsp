<%@ page import="site.springbike.view.ModelViewBuilder" %>
<%@ page import="site.springbike.model.SpringBikeModel" %>
<%@ page import="site.springbike.model.Message" %>
<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: adragos
  Date: 20/05/2020
  Time: 04:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../templates/header.jsp" %>
<body>
<%@ include file="../templates/navbar.jsp" %>

<%
    String error = (String) request.getParameter("error");
    HashMap<String, String> blacklistView = (HashMap<String, String>) request.getAttribute("blacklistView");
    if (blacklistView == null) blacklistView = new HashMap<>();
    List<String> lateClientsView = (List<String>) request.getAttribute("lateClientsView");
    if (lateClientsView == null) lateClientsView = new ArrayList<>();
%>
<main id="main">
    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div style="margin-top: 50px">
                        <h1 style="text-align: center;">Manage clients</h1>
                        <div style="text-align: center">
                            <div class="form-validate"><% if (error != null)
                                out.print(HtmlUtils.htmlEscape(error)); %></div>
                        </div>
                        <div class="container">
                            <br/>
                            <div class="row">
                                <div class="col-lg-6">
                                    <h4>Blacklist</h4>
                                    <%
                                        for (HashMap.Entry<String, String> blacklistEntry : blacklistView.entrySet()) {
                                    %>
                                    <p>
                                        <%
                                            out.print(HtmlUtils.htmlEscape(blacklistEntry.getKey()));
                                            if (blacklistEntry.getValue() != null && !blacklistEntry.getValue().isBlank()) {
                                                out.print(" - ");
                                                out.print(HtmlUtils.htmlEscape(blacklistEntry.getValue()));
                                            }
                                        %>
                                    </p>
                                    <%
                                        }
                                    %>
                                </div>
                                <div class="col-lg-6">
                                    <h4>Blacklist users</h4>
                                    <i>If the user to be blacklisted is already in the blacklist, he will be removed
                                        from the list</i><br/>
                                    <form method="POST">
                                        <label for="username">Username client*</label><br/>
                                        <input class="form-control" type="text" id="username" name="username"
                                               required><br/>
                                        <label for="reason">Reason</label><br/>
                                        <textarea class="form-control" name="reason" id="reason"></textarea>
                                        <input type="hidden" name="action" value="blacklist"/><br/>
                                        <button type="submit">Submit</button>
                                    </form>
                                </div>
                            </div>
                            <br/><br/>
                            <div class="row">
                                <div class="col-lg-6"><h3>Clients that are late on their bike return</h3>
                                    <%
                                        for (String lateClient : lateClientsView) {
                                    %>
                                    <p><span style="color: red">
                                        <%
                                            out.print(HtmlUtils.htmlEscape(lateClient));
                                        %>
                                    </span></p>
                                    <%
                                        }
                                    %>
                                </div>
                                <div class="col-lg-6"><h3>Send email to late clients</h3>
                                    <%
                                        out.print(ModelViewBuilder.useModel(null).addInput("username", "text", true, "").addInputs(new Message()).addInput("body", "textarea", true, "").addInput("action", "hidden", true, "email").generateForm(null));
                                    %>
                                </div>
                            </div>
                        </div>
                        <div class="form-validate">${error}</div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<%@ include file="../templates/footer.jsp" %>
</body>
</html>
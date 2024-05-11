<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">DV Apartment</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/" />">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/users" />">Users</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/lockers" />">Lockers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/orders" />">Orders</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/invoices" />">Invoices</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/surveys" />">Surveys</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/stats" />">Stats</a>
                </li>
            </ul>   
        </div>
    </div>
</nav>
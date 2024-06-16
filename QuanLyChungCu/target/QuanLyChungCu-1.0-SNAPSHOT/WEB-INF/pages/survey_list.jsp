<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
    <c:when test="${not empty sessionScope.basename}">
        <fmt:setBundle basename="surveys_${sessionScope.basename}" scope="session" />
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="surveys" scope="session" />
    </c:otherwise>
</c:choose>
<h1 class="text-center text-info mt-4"><fmt:message key="title"/></h1>
<div class="d-flex justify-content-between mb-3 align-items-end">
    <div class="d-flex align-items-center">
        <a style="height: 40px" href="/QuanLyChungCu/surveys/" class="btn btn-success"><fmt:message key="addSurvey"/></a>
    </div>

    <form action="<c:url value="/surveys" />" class="d-flex align-items-end">
        <div class="me-4"> 
            <input style="width: 140px" class="form-control me-2" name="username" type="search" placeholder="Username...">
        </div>
        <div class="me-4"> 
            <input style="width: 140px" class="form-control me-2" name="email" type="search" placeholder="Email...">
        </div>
        <div class="me-4"> 
            <input style="width: 140px" class="form-control me-2" name="phone" type="search" placeholder="Phone...">
        </div>
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="status"><fmt:message key="status"/></label>
            <select style="width: 160px" class="form-select" id="status" name="status" path="status">
                <option value="Active" selected>Active</option>
                <option value="Block">Block</option>
            </select>
        </div> 

        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="room"><fmt:message key="room"/></label>
            <select style="width: 140px" class="form-select" id="room" name="room">
                <c:forEach items="${roomsUsing}" var="r">
                    <option value="${r.id}">${r.name}</option>
                </c:forEach>
            </select>
        </div>

        <button class="btn btn-primary" type="submit"><fmt:message key="search"/></button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th><fmt:message key="id"/></th> 
        <th><fmt:message key="Title"/></th>
        <th><fmt:message key="status"/></th>
        <th><fmt:message key="description"/></th>
        <th><fmt:message key="Action"/></th>
    </tr>
    <c:forEach items="${surveys}" var="s">
        <tr>
            <td>${s.id}</td>
            <td>${s.title}</td>
            <td>${s.status}</td>
            <td>
                <c:choose>
                    <c:when test="${s.status == 'Open'}">
                        <p class="text-success">${s.status}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-danger">${s.status}</p>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${s.description}</td>
            <td>
                <c:url value="/surveys/${s.id}" var="url" />
                <a href="${url}" class="btn btn-info"><fmt:message key="view"/></a>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="d-flex justify-content-end">
    <div class="btn-toolbar mb-3" role="toolbar" aria-label="Toolbar with button groups">
        <div class="btn-group me-2" role="group" aria-label="First group">
            <c:choose>
                <c:when test="${totalPages > 3}">
                    <c:choose>
                        <c:when test="${currentPage > 2}">
                            <button type="button" class="btn btn-outline-secondary"
                                    onclick="window.location.href = '/QuanLyChungCu/surveys?page=1'">1</button>
                            <button type="button" class="btn btn-outline-secondary">...</button>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="3" varStatus="loop">
                                <button type="button" class="btn btn-outline-secondary"
                                        onclick="window.location.href = '/QuanLyChungCu/surveys?page=${loop.index}'">${loop.index}</button>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <button type="button" class="btn btn-outline-secondary"
                                onclick="window.location.href = '/QuanLyChungCu/surveys?page=${loop.index}'">${loop.index}</button>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="<c:url value="/js/script.js" />"></script>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
    <c:when test="${not empty sessionScope.basename}">
        <fmt:setBundle basename="locker_${sessionScope.basename}" scope="session" />
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="locker" scope="session" />
    </c:otherwise>
</c:choose>

<h1 class="text-center text-info mt-4"><fmt:message key="title"/></h1>
<div class="d-flex justify-content-between mb-3 align-items-end">
    <c:url value="/api/lockers/" var="url" />
    <button onClick="addLocker('${url}')" type="button" class="btn btn-success"><fmt:message key="addLocker"/></button>

    <form action="<c:url value="/lockers" />" class="d-flex align-items-end">
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="status"><fmt:message key="status"/></label>
            <select style="width: 140px" class="form-select" id="status" name="status" path="status">
                <option value="Blank" selected>Blank</option>
                <option value="Using">Using</option>
            </select>
        </div> 

        <button class="btn btn-primary ms-5" type="submit"><fmt:message key="search"/></button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th><fmt:message key="id"/></th> 
        <th><fmt:message key="status"/></th>
        <th><fmt:message key="action"/></th>
    </tr>
    <c:forEach items="${lockers}" var="locker">
        <tr>
            <td>${locker.id}</td>
            <td>
                <c:choose>
                    <c:when test="${locker.status == 'Using'}">
                        <p class="text-warning">${locker.status}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-success">${locker.status}</p>
                    </c:otherwise>
                </c:choose>

            </td>
            <td>
                <c:url value="/api/lockers/${locker.id}" var="url" />
                <button onClick="deleteLocker('${url}')" class="btn btn-danger">Delete</button>
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
                                    onclick="window.location.href = '/QuanLyChungCu/lockers?page=1'">1</button>
                            <button type="button" class="btn btn-outline-secondary">...</button>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="3" varStatus="loop">
                                <button type="button" class="btn btn-outline-secondary"
                                        onclick="window.location.href = '/QuanLyChungCu/lockers?page=${loop.index}'">${loop.index}</button>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <button type="button" class="btn btn-outline-secondary"
                                onclick="window.location.href = '/QuanLyChungCu/lockers?page=${loop.index}'">${loop.index}</button>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="<c:url value="/js/script.js" />"></script>
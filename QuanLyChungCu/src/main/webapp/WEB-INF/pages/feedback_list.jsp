<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
    <c:when test="${not empty sessionScope.basename}">
        <fmt:setBundle basename="feedback_${sessionScope.basename}" scope="session" />
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="feedback" scope="session" />
    </c:otherwise>
</c:choose>

<h1 class="text-center text-info mt-4"><fmt:message key="title"/></h1>
<c:if test="${!empty errMsg}">
    <div class="alert alert-danger">${errMsg}</div>
</c:if>
<div class="d-flex justify-content-between mb-3 align-items-end">
    <form action="<c:url value="/feedbacks" />" class="d-flex mt-4 w-100 justify-content-end align-items-end">
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="roomId"><fmt:message key="room"/></label>
            <select style="width: 140px;" class="form-select ms-3 me-4" id="roomId" name="roomId">
                <c:forEach items="${roomsUsing}" var="r">
                    <option value="${r.id}" >${r.name}</option>
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
        <th><fmt:message key="content"/></th>
        <th><fmt:message key="room"/></th>
        <th><fmt:message key="Createdat"/></th>
        <th><fmt:message key="Updatedat"/></th>
        <th><fmt:message key="Action"/></th>
    </tr>
    <c:forEach items="${feedbacks}" var="fb">
        <tr>
            <td>${fb.id}</td>
            <td>${fb.title}</td>
            <td>${fb.content}</td>
            <td>${fb.userId.room.name}</td>
            <td><fmt:formatDate value="${fb.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
            <td>
                <c:choose>
                    <c:when test="${fb.updatedAt != null}">
                        <p><fmt:formatDate value="${p.updatedAt}" pattern="dd/MM/yyyy HH:mm:ss" /></p>
                    </c:when>
                    <c:otherwise>
                        <p>-----</p>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:url value="/feedbacks/${fb.id}" var="url" />
                <a href="${url}" class="btn btn-info">View</a>
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
                                    onclick="window.location.href = '/QuanLyChungCu/feedbacks?page=1'">1</button>
                            <button type="button" class="btn btn-outline-secondary">...</button>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="3" varStatus="loop">
                                <button type="button" class="btn btn-outline-secondary"
                                        onclick="window.location.href = '/QuanLyChungCu/feedbacks?page=${loop.index}'">${loop.index}</button>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <button type="button" class="btn btn-outline-secondary"
                                onclick="window.location.href = '/QuanLyChungCu/feedbacks?page=${loop.index}'">${loop.index}</button>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="<c:url value="/js/script.js" />"></script>

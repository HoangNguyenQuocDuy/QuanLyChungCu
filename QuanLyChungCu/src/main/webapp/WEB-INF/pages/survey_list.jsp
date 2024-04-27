<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center text-info mt-4">Survey management</h1>
<div class="d-flex justify-content-between mb-3 align-items-end">
    <div class="d-flex align-items-center">
        <a style="height: 40px" href="/QuanLyChungCu/surveys/" class="btn btn-success">Add Survey</a>
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
            <label class="d-flex justify-content-center mb-2" for="status">Status</label>
            <select style="width: 160px" class="form-select" id="status" name="status" path="status">
                <option value="Active" selected>Active</option>
                <option value="Block">Block</option>
            </select>
        </div> 

        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="room">Room</label>
            <select style="width: 140px" class="form-select" id="room" name="room">
                <c:forEach items="${roomsUsing}" var="r">
                    <option value="${r.id}">${r.name}</option>
                </c:forEach>
            </select>
        </div>

        <button class="btn btn-primary" type="submit">Search</button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th>Id</th> 
        <th>Title</th>
        <th>Status</th>
        <th>Description</th>
        <th>Action</th>
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
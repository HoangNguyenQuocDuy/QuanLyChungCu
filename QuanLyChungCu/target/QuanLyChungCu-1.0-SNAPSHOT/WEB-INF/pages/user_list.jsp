<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center text-info mt-4">User management</h1>
<div class="d-flex justify-content-between mb-3">
    <a href="/QuanLyChungCu/users/" type="button" class="btn btn-success">Add user</a>

    <form action="<c:url value="/" />" class="d-flex">
        <input class="form-control me-2" name="kw" type="search" placeholder="Search room...">
        <button class="btn btn-primary" type="submit">Search</button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th>Id</th> 
        <th>Username</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Status</th>
        <th>Avatar</th>
        <th>Room</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${users}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.email}</td>
            <td>${u.phone}</td>

            <td>
                <c:choose>
                    <c:when test="${u.status == 'Active'}">
                        <p class="text-success">${u.status}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-danger">${u.status}</p>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${u.avatar != null}">
                        <img class="rounded img-fluid" src="${u.avatar}" width="200" alt="${u.avatar}">
                    </c:when>
                </c:choose>
                -----
            </td>
            <td>${u.room.name}</td>
            <td>
                <button class="btn btn-danger">Delete</button>
                <c:url value="/users/${u.id}" var="url" />
                <a href="${url}" class="btn btn-info">Update</a>
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
                                    onclick="window.location.href = '/QuanLyChungCu/users?page=1'">1</button>
                            <button type="button" class="btn btn-outline-secondary">...</button>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="3" varStatus="loop">
                                <button type="button" class="btn btn-outline-secondary"
                                        onclick="window.location.href = '/QuanLyChungCu/users?page=${loop.index}'">${loop.index}</button>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <button type="button" class="btn btn-outline-secondary"
                                onclick="window.location.href = '/QuanLyChungCu/users?page=${loop.index}'">${loop.index}</button>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
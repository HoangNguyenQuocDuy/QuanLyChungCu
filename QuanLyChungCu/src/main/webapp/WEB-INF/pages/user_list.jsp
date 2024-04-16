<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center text-info mt-4">User management</h1>
<div class="d-flex justify-content-between mb-3">
    <a href="/QuanLyChungCu/users/" type="button" class="btn btn-success">Add user</a>

    <form action="<c:url value="/users" />" class="d-flex">
        <input class="form-control me-2" name="username" type="search" placeholder="Search username...">
        <input class="form-control me-2" name="email" type="search" placeholder="Search email...">
        <input class="form-control me-2" name="phone" type="search" placeholder="Search phone...">
        <select  class="form-select" id="status" name="status" path="status">
            <option value="Active" selected>Active</option>
            <option value="Block">Block</option>
        </select>
        <input class="form-control me-2" name="roomName" type="search" placeholder="Room name...">

        <button class="btn btn-primary" type="submit">Search</button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th>Id</th> 
        <th>Username</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Locker</th>
        <th>Status</th>
        <th>Avatar</th>
        <th>Room</th>
        <th>Locker</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${users}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.email}</td>
            <td>${u.phone}</td>
            <td>${u.locker.id}</td>
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
            <td>${u.locker.id}</td>
            <td>
                <c:choose>
                    <c:when test="${u.status == 'Active'}">
                        <c:url value="/users/${u.id}" var="url" />
                        <button onclick="blockUser('${url}')" class="btn btn-danger">Block</button>
                    </c:when>
                </c:choose>
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

<script src="<c:url value="/js/script.js" />"></script>
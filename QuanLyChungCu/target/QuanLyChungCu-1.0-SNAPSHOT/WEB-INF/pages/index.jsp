<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center text-info mt-4">Room management</h1>
<div class="d-flex justify-content-between mb-3 align-items-end">
    <div class="d-flex align-items-center">
        <a style="height: 40px" href="/QuanLyChungCu/rooms/" class="btn btn-success">Add Room</a>
    </div>

    <form action="<c:url value="/" />" class="d-flex align-items-end">
        <input class="form-control me-3" style="width: 160px;" name="name" type="search" placeholder="Search room name...">
        <div>
            <label class="d-flex justify-content-center mb-2" for="status">Status</label>
            <select style="width: 160px" class="form-select" id="status" name="status">
                <option value="Blank" selected>Blank</option>
                <option value="Rent">Rent</option>
            </select>
        </div>   
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="type">Type</label>
            <select class="form-select ms-3 me-4" id="type" name="type">
                <c:forEach items="${roomtypes}" var="rt">
                    <option value="${rt.type}" selected>${rt.type}</option>
                </c:forEach>
            </select>
        </div> 

        <button class="btn btn-primary" type="submit">Search</button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th>Id</th> 
        <th>Room name</th>
        <th>Status</th>
        <th>Room type</th>
        <th>Price</th>
        <th></th>
        <th>Action</th>
    </tr>
    <c:forEach items="${rooms}" var="r">
        <tr>
            <td>${r.id}</td>
            <td>${r.name}</td>
            <td>${r.status}</td>
            <td>${r.roomtype.type}</td>
            <td>${r.roomtype.price} $</td>
            <td><img class="rounded img-fluid" src="${r.image}" width="200" alt="${r.name}"></td>
            <td>
                <c:url value="/api/rooms/${r.id}" var="url" />
                <button onclick="deleteRoom('${url}')" class="btn btn-danger">Delete</button>
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
                                    onclick="window.location.href = '/QuanLyChungCu?page=1'">1</button>
                            <button type="button" class="btn btn-outline-secondary">...</button>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="3" varStatus="loop">
                                <button type="button" class="btn btn-outline-secondary"
                                        onclick="window.location.href = '/QuanLyChungCu?page=${loop.index}'">${loop.index}</button>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <button type="button" class="btn btn-outline-secondary"
                                onclick="window.location.href = '/QuanLyChungCu?page=${loop.index}'">${loop.index}</button>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="<c:url value="/js/script.js" />"></script>

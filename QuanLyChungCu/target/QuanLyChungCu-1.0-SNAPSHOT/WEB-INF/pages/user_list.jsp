<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center text-info mt-4">Rooms</h1>
<div class="d-flex justify-content-between mb-3">
    <a href="/QuanLyChungCu/rooms/" type="button" class="btn btn-success">Add room</a>

    <form action="<c:url value="/" />" class="d-flex">
        <input class="form-control me-2" name="kw" type="search" placeholder="Search room...">
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
        <th>Action</th>
    </tr>
    <c:forEach items="${rooms}" var="r">
        <tr>
            <td>${r.id}</td>
            <td>${r.name}</td>
            <td>${r.status}</td>
            <td>${r.roomtype.type}</td>
            <td>${r.roomtype.price} $</td>
            <td>
                <button class="btn btn-danger">Delete</button>
                <c:url value="/rooms/${r.id}" var="url" />
                <a href="${url}" class="btn btn-info">Update</a>
            </td>
        </tr>
    </c:forEach>
</table>
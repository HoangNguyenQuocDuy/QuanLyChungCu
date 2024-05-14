<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 class="text-center text-info mt-4">Order management</h1>
<c:if test="${!empty errMsg}">
    <div class="alert alert-danger">${errMsg}</div>
</c:if>
<div class="d-flex justify-content-between mb-3 align-items-end">
    <div class="d-flex align-items-center">
        <a href="/QuanLyChungCu/orders/" type="button" class="btn btn-success">Add order</a>
    </div>

    <form action="<c:url value="/orders" />" class="d-flex mt-4 align-items-end">
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="status">Status</label>
            <select style="width: 140px;" class="form-select ms-3 me-4" id="status" name="status">
                <option value="Pending" >Pending</option>
                <option value="Received" >Received</option>
            </select>
        </div> 
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="lockerId">Locker</label>
            <select style="width: 140px" class="form-select me-4" id="lockerId" name="lockerId" path="lockerId">
                <c:forEach items="${lockersUsing}" var="locker">
                    <option value="${locker.id}">${locker.id}</option>
                </c:forEach>
            </select>
        </div> 
        <button class="btn btn-primary" type="submit">Search</button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th>Id</th> 
        <th>Image</th>
        <th>Status</th>
        <th>Locker ID</th>
        <th>Created at</th>
        <th>Updated at</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${userOrders}" var="o">
        <tr>
            <td>${o.id}</td>
            <td>
                <img class="rounded img-fluid" src="${o.image}" width="200" alt="${o.id}">
            </td>
            <td>
                <c:choose>
                    <c:when test="${o.status == 'Received'}">
                        <p class="text-success">${o.status}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-warning">${o.status}</p>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${o.lockerId.id}</td>
            <td><fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
            <td>
                <c:choose>
                    <c:when test="${o.updatedAt != null}">
                        <p><fmt:formatDate value="${o.updatedAt}" pattern="dd/MM/yyyy HH:mm:ss" /></p>
                    </c:when>
                    <c:otherwise>
                        <p>-----</p>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:url value="/api/orders/${o.id}" var="url" />
                <button onClick="deleteOrder('${url}')" class="btn btn-danger">Delete</button>
                <button onClick="confirmOrder('${url}')" class="btn btn-info">Update</button>
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

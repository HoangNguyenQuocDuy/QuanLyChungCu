<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 class="text-center text-info mt-4">Parking management</h1>
<c:if test="${!empty errMsg}">
    <div class="alert alert-danger">${errMsg}</div>
</c:if>
<div class="d-flex justify-content-between mb-3 align-items-end">
    <form action="<c:url value="/parkings" />" class="d-flex mt-4 w-100 justify-content-end align-items-end">
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="status">Status</label>
            <select style="width: 140px;" class="form-select ms-3 me-4" id="status" name="status">
                <option value="Pending" >Pending</option>
                <option value="Confirmed" >Confirmed</option>
                <option value="Canceled" >Canceled</option>
            </select>
        </div> 
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="roomId">Room</label>
            <select style="width: 140px;" class="form-select ms-3 me-4" id="roomId" name="roomId">
                <c:forEach items="${roomsUsing}" var="r">
                    <option value="${r.id}" >${r.name}</option>
                </c:forEach>
            </select>
        </div> 
        <button class="btn btn-primary" type="submit">Search</button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th>Id</th> 
        <th>Status</th>
        <th>Related Name</th>
        <th>Related</th>
        <th>Room</th>
        <th>Created at</th>
        <th>Updated at</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${parkings}" var="p">
        <tr>
            <td>${p.id}</td>
            <td>
                <c:choose>
                    <c:when test="${p.status == 'Confirmed'}">
                        <p class="text-success">${p.status}</p>
                    </c:when>
                    <c:when test="${p.status == 'Pending'}">
                        <p class="text-warning">${p.status}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-danger">${p.status}</p>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${p.relativeId.name}</td>
            <td>${p.relativeId.type}</td>
            <td>${p.relativeId.userId.room.name}</td>
            <td><fmt:formatDate value="${p.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
            <td>
                <c:choose>
                    <c:when test="${p.updatedAt != null}">
                        <p><fmt:formatDate value="${p.updatedAt}" pattern="dd/MM/yyyy HH:mm:ss" /></p>
                    </c:when>
                    <c:otherwise>
                        <p>-----</p>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:url value="/api/parkings/${p.id}" var="url" />
                <button onClick="updateParking('${url}', 'Confirmed')" class="btn btn-info">Confirm</button>
                <button onClick="updateParking('${url}', 'Canceled')" class="btn btn-danger">Cancel</button>
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
                                    onclick="window.location.href = '/QuanLyChungCu/parkings?page=1'">1</button>
                            <button type="button" class="btn btn-outline-secondary">...</button>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="3" varStatus="loop">
                                <button type="button" class="btn btn-outline-secondary"
                                        onclick="window.location.href = '/QuanLyChungCu/parkings?page=${loop.index}'">${loop.index}</button>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <button type="button" class="btn btn-outline-secondary"
                                onclick="window.location.href = '/QuanLyChungCu/parkings?page=${loop.index}'">${loop.index}</button>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="<c:url value="/js/script.js" />"></script>

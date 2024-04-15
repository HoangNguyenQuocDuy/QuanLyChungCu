<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-info mt-4">Rooms</h1>
<c:url value="/rooms/" var="action" />
<form:form method="post" action="${action}" modelAttribute="room">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="name"  placeholder="Room name" path="name" />
        <label for="name">Room name</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="status"  placeholder="Status" path="status" />
        <label for="email">Status</label>
    </div>
    <div class="form-floating">
        <form:select class="form-select" id="roomtype" name="roomtype" path="roomtype">
            <c:forEach items="${roomtypes}" var="rt">
                <c:choose>
                    <c:when test="${rt.id == room.roomtype.id}">
                        <option value="${rt.id}" selected>${rt.type}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${rt.id}">${rt.type}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </form:select>
        <label for="roomtype" class="form-label">Select list (select one):</label>
    </div>
    <div class="form-floating">
        <button class="btn btn-info mt-1" type="submit">
            <c:choose>
                <c:when test="${room.id > 0}">Update</c:when>
                <c:otherwise>Create room</c:otherwise>
            </c:choose>
        </button>
        <form:hidden path="id" />
    </div>
</form:form>
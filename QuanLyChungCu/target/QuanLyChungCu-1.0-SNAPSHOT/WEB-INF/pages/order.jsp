<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-info mt-4">Rooms</h1>
<c:url value="/orders/" var="action" />
<form:form method="post" action="${action}" modelAttribute="userOrder" enctype="multipart/form-data">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    <c:if test="${!empty errMsg}">
        <div class="alert alert-danger">${errMsg}</div>
    </c:if>
    <div class="form-floating">
        <form:select class="form-select" id="lockerId" name="lockerId" path="lockerId">
            <c:forEach items="${lockersUsing}" var="locker">
                <option value="${locker.id}">${locker.id}</option>
            </c:forEach>
        </form:select>
        <label for="lockerId" class="form-label">Select locker (select one):</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <div>
            <label class="mb-1" for="file">Image</label>
        </div>
        <div class="mb-2">
            <form:input type="file" path="file" id="file" name="file" />
        </div>
    </div>
    <div class="form-floating">
        <div class="d-flex justify-content-center">
            <button class="btn btn-info mt-2 w-200" type="submit">Create order</button>
        </div>
    </div>
</form:form>
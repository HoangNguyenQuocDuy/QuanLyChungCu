<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-info mt-4">User management</h1>
<c:url value="/users" var="action" />
<form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data">
    <%--<form:errors path="*" element="div" cssClass="alert alert-danger" />--%>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="name"  placeholder="Username" path="username" />
        <label for="name">Username</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="email"  placeholder="Email" path="email" />
        <label for="email">Email</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="phone"  placeholder="Phone" path="phone" />
        <label for="email">Phone</label>
    </div>
    <div class="form-floating">
        <button class="btn btn-info mt-1" type="submit">
            <c:choose>
                <c:when test="${user.id > 0}">Update</c:when>
                <c:otherwise>Create user</c:otherwise>
            </c:choose>
        </button>
        <form:hidden path="id" />
    </div>
</form:form>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-info mt-4">User management</h1>
<c:url value="/users/" var="action" />
<form:form method="post" action="${action}" modelAttribute="user">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />

    <c:if test="${!empty errMsg}">
        <div class="alert alert-danger">${errMsg}</div>
    </c:if>
    <c:choose>
        <c:when test="${empty user.id}">
            <div class="form-floating mb-3 mt-3">
                <form:input class="form-control"  id="name"  placeholder="Username" path="username" />
                <label for="name">Username</label>
            </div>
            <div class="form-floating mb-3 mt-3 d-flex align-items-center">
                <div style="width: 60%; height: 58px" class="form-floating d-flex flex-start">
                    <form:input class="form-control"  id="password"  placeholder="Password" path="password" />
                    <label for="password">Password</label>
                </div>

                <button onclick="generatePassword()" type="button" class="btn btn-primary ms-4" style="width: 160px; height: 42px">Random password</button>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input class="form-control"  id="email"  placeholder="Email" path="email" />
                <label for="email">Email</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="number" class="form-control"  id="phone"  placeholder="Phone" path="phone" />
                <label for="email">Phone</label>
            </div>
        </c:when>
        <c:otherwise>
            <form:hidden path="username" />
            <form:hidden path="password" />
            <form:hidden path="email" />
            <form:hidden path="phone" />
        </c:otherwise>
    </c:choose>


    <div class="form-floating">
        <form:select class="form-select" id="room" name="room" path="room">
            <c:forEach items="${rooms}" var="r">
                <c:choose>
                    <c:when test="${r.id == user.room.id}">
                        <option value="${r.id}" selected>${r.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${r.id}">${r.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </form:select>
        <label for="roomtype" class="form-label">Select room (select one):</label>
    </div>

    <div class="form-floating">
        <button class="btn btn-info mt-4" type="submit">
            <c:choose>
                <c:when test="${user.id > 0}">Update</c:when>
                <c:otherwise>Create user</c:otherwise>
            </c:choose>
        </button>
        <form:hidden path="id" />
        <form:hidden path="status" />
    </div>
</form:form>

<script>
    function generatePassword() {
        var length = 6;
        var charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        var password = "";
        for (var i = 0; i < length; ++i) {
            password += charset.charAt(Math.floor(Math.random() * charset.length));
        }
        document.getElementById("password").value = password;
    }
</script>
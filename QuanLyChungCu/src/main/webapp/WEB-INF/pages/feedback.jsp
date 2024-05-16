<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 class="text-center text-info mt-4">Feedback</h1>
<c:if test="${!empty errMsg}">
    <div class="alert alert-danger">${errMsg}</div>
</c:if>
<form:form method="post" action="${action}" modelAttribute="feedback" enctype="multipart/form-data">
    <div class="form-floating mb-3 mt-3">
        <form:input readonly="true" class="form-control"  id="description" path="userId.room.name" />
        <label for="desc">Room</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input readonly="true" class="form-control"  id="createdAt" path="createdAt"/>
        <label for="createdAt">Created At</label>
    </div>
        <div class="form-floating mb-3 mt-3">
        <form:input readonly="true" class="form-control"  id="updatedAt" path="updatedAt"/>
        <label for="updatedAt">Updated At</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input readonly="true" class="form-control"  id="title" path="title" />
        <label for="title">Title</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:textarea readonly="true" class="form-control"  id="content" path="content" />
        <label for="content">Content</label>
    </div>
</form:form>
<div class="form-floating">
    <c:url value="/feedbacks" var="url" />
    <a href="${url}" class="btn btn-info">Back</a>
</div>
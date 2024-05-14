<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-info mt-4">Invoice</h1>
<c:url value="/invoices/" var="action" />
<form:form method="post" action="${action}" modelAttribute="invoice">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />

    <c:if test="${!empty errMsg}">
        <div class="alert alert-danger">${errMsg}</div>
    </c:if>
    <div class="form-floating">
        <form:select class="form-select" id="room" name="room" path="room">
            <c:forEach items="${roomsUsing}" var="r">
                <option value="${r.id}">${r.name}</option>
            </c:forEach>
        </form:select>
        <label for="room" class="form-label">Select room (select one):</label>
    </div>

    <div class="form-floating mt-3">
        <form:select class="form-select" id="invoiceType" name="invoiceType" path="invoiceType">
            <c:forEach items="${invoicetypes}" var="it">
                <c:choose>
                    <c:when test="${it.id == invoice.invoiceType.id}">
                        <option selected="selected" value="${it.id}">${it.type}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${it.id}">${it.type}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </form:select>
        <label for="invoiceType" class="form-label">Select type (select one):</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="amount" type="number" placeholder="Amount" path="amount" />
        <label for="amount">Amount</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control"  id="description" placeholder="Description" path="description" />
        <label for="desc">Description</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" id="dueDate" name="dueDate" type="datetime" placeholder="Due Date" path="dueDate" readonly="true" />
        <label for="dueDate">Due Date</label>
    </div>

    <div class="form-floating">
        <div class="d-flex justify-content-center">
            <c:choose>
                <c:when test="${empty invoice.id}">
                    <button class="btn btn-info mt-2 w-200" type="submit">Create invoice</button>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-info mt-2 w-200" type="submit">Update invoice</button>

                    <form:hidden path="id" />
                    <form:hidden path="status" />
                    <form:hidden path="createdAt" />
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</form:form>

<script>
    $(function () {
        $('#dueDate').datepicker({
            dateFormat: 'yy-mm-dd 23:59:00',
            changeMonth: true,
            changeYear: true,
            minDate: 1
        });
    });
</script>
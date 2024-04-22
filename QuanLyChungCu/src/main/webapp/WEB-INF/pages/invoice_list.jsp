<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center text-info mt-4 mb-4">Invoice management</h1>
<div class="d-flex justify-content-between mb-4 align-items-end">
    <c:url value="/invoices/" var="url" />

    <div class="d-flex align-items-center">
        <a style="height: 40px" href="/QuanLyChungCu/invoices/" class="btn btn-success">Add Invoice</a>
    </div>
    <form action="<c:url value="/invoices" />" class="d-flex align-items-end">
        <!--        <div class="me-4">
                    <label class="d-flex justify-content-center mb-2" for="dueDate">Due date</label>
                    <input class="form-control" id="dueDate" name="dueDate" type="text" 
                           placeholder="Due date" readonly="true" style="width: 160px" />
                </div>
                <div class="me-4">
                    <label class="d-flex justify-content-center mb-2" for="fromDate">From date</label>
                    <input class="form-control" id="fromDate" name="fromDate" type="text" 
                           placeholder="From date" readonly="true" style="width: 160px" />
                </div>
                <div class="me-4">
                    <label class="d-flex justify-content-center mb-2" for="toDate">To date</label>
                    <input class="form-control" id="toDate" name="toDate" type="text" 
                           placeholder="To date" readonly="true" style="width: 160px"/>
                </div>-->
        <div class="me-4">
            <label class="d-flex justify-content-center mb-2" for="status">Status</label>
            <select  class="form-select" id="status" name="status" path="status">
                <option value="Unpaid" selected>Unpaid</option>
                <option value="Paid">Paid</option>
            </select>
        </div>
        <div>
            <label class="d-flex justify-content-center mb-2" for="invoiceType">Invoice type</label>
            <select style="width: 140px" class="form-select" id="invoiceType" name="invoiceType">
                <c:forEach items="${invoicetypes}" var="it">
                    <option value="${it.type}">${it.type}</option>
                </c:forEach>
            </select>
        </div>
        <div class="ms-4">
            <label class="d-flex justify-content-center mb-2" for="room">Room</label>
            <select style="width: 140px" class="form-select" id="room" name="room">
                <c:forEach items="${roomsUsing}" var="r">
                    <option value="${r.id}">${r.name}</option>
                </c:forEach>
            </select>
        </div>
        <button style="height: 40px" class="btn btn-primary ms-5" type="submit">Search</button>
    </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th>Id</th> 
        <th>Invoice type</th>
        <th>Amount</th>
        <th>Due date</th>
        <th>Status</th>
        <th>Created at</th>
        <th>Updated at</th>
        <th>Room</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>
    <c:forEach items="${invoices}" var="i">
        <tr>
            <td>${i.id}</td>
            <td>${i.invoiceType.type}</td>
            <td>${i.amount} $</td>
            <td>${i.dueDate}</td>
            <td>
                <c:choose>
                    <c:when test="${i.status == 'Unpaid'}">
                        <p class="text-warning">${i.status}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-success">${i.status}</p>
                    </c:otherwise>
                </c:choose>

            </td>
            <td>${i.createdAt}</td>
            <td>${i.updatedAt}</td>
            <td>${i.room.name}</td>
            <td>${i.description}</td>
            <td>
                <c:url value="/invoices/${i.id}" var="url" />
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
                                    onclick="window.location.href = '/QuanLyChungCu/invoices?page=1'">1</button>
                            <button type="button" class="btn btn-outline-secondary">...</button>
                        </c:when>
                        <c:otherwise>
                            <c:forEach begin="1" end="3" varStatus="loop">
                                <button type="button" class="btn btn-outline-secondary"
                                        onclick="window.location.href = '/QuanLyChungCu/invoices?page=${loop.index}'">${loop.index}</button>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                        <button type="button" class="btn btn-outline-secondary"
                                onclick="window.location.href = '/QuanLyChungCu/invoices?page=${loop.index}'">${loop.index}</button>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script>
//    $(function () {
//        $('#fromDate').datepicker({
//            dateFormat: 'yy-mm-dd',
//            changeMonth: true,
//            changeYear: true
//        });
//    });
//    $(function () {
//        $('#toDate').datepicker({
//            dateFormat: 'yy-mm-dd',
//            changeMonth: true,
//            changeYear: true
//        });
//    });
//    $(function () {
//        $('#dueDate').datepicker({
//            dateFormat: 'yy-mm-dd',
//            changeMonth: true,
//            changeYear: true
//        });
//    });
</script>
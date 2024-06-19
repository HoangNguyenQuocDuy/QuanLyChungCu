<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
    String lang = request.getParameter("lang");
    if (lang != null) {
        session.setAttribute("basename", lang);
    } else if (session.getAttribute("basename") == null) {
        session.setAttribute("basename", "vi_VN");
    }
%>

<fmt:setBundle basename="index_${sessionScope.basename}" scope="session" />
<h1 class="text-center text-info mt-4"><fmt:message key="title"/></h1>
<div class="d-flex justify-content-between mb-3 align-items-end">
    <div class="d-flex align-items-center flex-grow-1">
        <a style="height: 40px" href="/QuanLyChungCu/rooms/" class="btn btn-success"><fmt:message key="addRoom"/></a>    
        <label style="margin: 0 16px"> Chọn ngôn ngữ: </label>
        <select onchange="changeLanguage()" id="languageSelect" name="lang"  style="width: 160px" class="form-select">
           <option ${sessionScope.basename == 'vi_VN' ? 'selected' : ''} value="vi_VN">Tiếng Việt</option>
           <option ${sessionScope.basename == 'en_US' ? 'selected' : ''} value="en_US">English</option>
        </select>
    </div>
 
        <form action="<c:url value="/" />" class="d-flex align-items-end">
            <input class="form-control me-3" style="width: 160px;" name="name" type="search" placeholder="Search room name...">
            <div>
                <label class="d-flex justify-content-center mb-2" for="status"><fmt:message key="status"/></label>
                <select style="width: 160px" class="form-select" id="status" name="status">
                    <option value="Blank" selected>Blank</option>
                    <option value="Rent">Rent</option>
                </select>
            </div>   
            <div class="me-4">
                <label class="d-flex justify-content-center mb-2" for="type"><fmt:message key="type"/></label>
                <select class="form-select ms-3 me-4" id="type" name="type">
                    <c:forEach items="${roomtypes}" var="rt">
                        <option value="${rt.type}" selected>${rt.type}</option>
                    </c:forEach>
                </select>
            </div> 

            <button class="btn btn-primary" type="submit"><fmt:message key="search"/></button>
        </form>
</div>
<table class="table table-hover mt-4">
    <tr>
        <th><fmt:message key="id"/></th> 
        <th><fmt:message key="roomName"/></th>
        <th><fmt:message key="status"/></th>
        <th><fmt:message key="roomType"/></th>
        <th><fmt:message key="price"/></th>
        <th><fmt:message key="image"/></th>
        <th><fmt:message key="action"/></th>
    </tr>
    <c:forEach items="${rooms}" var="r">
        <tr>
            <td>${r.id}</td>
            <td>${r.name}</td>
            <td>${r.status}</td>
            <td>${r.roomtype.type}</td>
            <td>${r.roomtype.price} $</td>
            <td><img class="rounded img-fluid" src="${r.image}" width="200" alt="${r.name}"></td>
            <td>
                <c:url value="/api/rooms/${r.id}" var="url" />
                <button onclick="deleteRoom('${url}')" class="btn btn-danger"><fmt:message key="delete"/></button>
                <a href="${url}" class="btn btn-info"><fmt:message key="update"/></a>
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

<script>
                                    function changeLanguage() {
                                        var selectedLanguage = document.getElementById("languageSelect").value;
                                        var url = window.location.pathname + "?lang=" + selectedLanguage;
                                        window.location.href = url;
                                    }

</script>

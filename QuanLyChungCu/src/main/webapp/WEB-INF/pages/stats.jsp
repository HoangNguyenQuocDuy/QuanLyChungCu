<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
    <c:when test="${not empty sessionScope.basename}">
        <fmt:setBundle basename="static_${sessionScope.basename}" scope="session" />
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="static" scope="session" />
    </c:otherwise>
</c:choose>
<h1 class="text-center text-info mt-1"><fmt:message key="title"/></h1>


<h3 style="margin-top: 40px"><fmt:message key="Survey"/></h3>

<div class="row">
    <div class="col-md-5 col-12" style="max-height: 280px; overflow-y: scroll">
        <table class="table ">
            <thead>
                <tr>
                    <th><fmt:message key="Id"/></th>
                    <th><fmt:message key="Title"/></th>
                    <th><fmt:message key="ResponseCount"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${statsResponsesForPerSurvey}" var="stats">
                    <tr>
                        <td>${stats[0]}</td>
                        <td>${stats[1]}</td>
                        <td>${stats[2]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="col-md-7 col-12">
        <canvas id="myChart1"></canvas>
    </div>
</div>

<div class="row mt-4">
    <div class="col-md-5 col-12">
        <div>
            <div class="alert alert-info">
                <h5 id="surveyTitle"><fmt:message key="Survey"/>:</h5>
                <h5 id="questionTitle"><fmt:message key="Question"/>:</h5>
            </div>
            <form>
                <div class="form-floating  mb-3 mt-3">
                    <select class="form-select" id="surveyId" name="surveyId">
                        <c:forEach items="${surveys}" var="survey">
                            <option value="${survey.id}">${survey.title}</option>
                        </c:forEach>
                    </select>
                    <label for="surveyId" class="form-label"><fmt:message key="Survey"/>:</label>
                </div>
                <div class="form-floating  mb-3 mt-3">
                    <select class="form-select" id="questionId" name="questionId">
                        <c:forEach items="${questions}" var="question">
                            <option value="${question.id}">${question.questionText}</option>
                        </c:forEach>
                    </select>
                    <label for="questionId" class="form-label"><fmt:message key="Question"/>:</label>
                </div>
                <div class="form-floating  mb-3 mt-3">
                    <button class="btn btn-info"><fmt:message key="Filter"/>:</button>
                </div>
            </form>
        </div>
        <table class="table">
            <tr>
                <th><fmt:message key="Option"/></th>
                <th><fmt:message key="ResponseCount"/></th>
            </tr>
            <c:forEach items="${statsCountQuestion}" var="sqc">
                <tr>

                    <td>${sqc[3]}</td>
                    <td>${sqc[4]}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-7 col-12">
        <canvas id="myChart2"></canvas>
    </div>
</div>

<h3 style="margin-top: 60px"><fmt:message key="MonthlyRevenue"/></h3>

<div class="row mt-4">
    <div class="d-flex">
        <div class="col-md-5 col-12 me-4">
            <div>
                <div class="alert alert-info">
                    <h5 id="yearTitle"><fmt:message key="Year"/>:</h5>
                    <h5 id="monthTitle"><fmt:message key="Month"/>:</h5>
                </div>
                <form>
                    <div class="form-floating mb-3">
                        <input name="year" type="number" class="form-control" id="year" placeholder="2024">
                        <label for="year"><fmt:message key="Year"/></label>
                    </div>
                    <div class="form-floating mb-3">
                        <input name="month" type="number" class="form-control" id="floatingInput" placeholder="5">
                        <label for="floatingInput"><fmt:message key="Month"/></label>
                    </div>
                    <div class="form-floating  mb-3 mt-3">
                        <button class="btn btn-info"><fmt:message key="Filter"/>:</button>
                    </div>
                </form>
            </div>
            <table class="table ">
                <thead>
                    <tr>
                        <th><fmt:message key="InvoiceType"/></th>
                        <th><fmt:message key="Amount"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${statsRevenueByMonth}" var="stats">
                        <tr>
                            <td>${stats[0]}</td>
                            <td>${stats[1]}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-7 col-12">
            <canvas id="statsRevenueByMonth"></canvas>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    document.getElementById("surveyId").addEventListener("change", function () {
        var surveyId = this.value;
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    let response = xhr.responseText;
                    var newHTML = response.substring(
                            response.indexOf('<select class=\"form-select\" id=\"questionId\" name=\"questionId\">',
                                    '</select>'));
                    document.getElementById("questionId").innerHTML = newHTML;
                } else {
                    console.log("Có lỗi xảy ra: " + xhr.status);
                }
            }
        };
        xhr.open("GET", "/QuanLyChungCu/stats?surveyId=" + surveyId, true);
        xhr.send();
        console.log(xhr);
    });
    let labels = [];
    let data = [];
    <c:forEach items="${statsResponsesForPerSurvey}" var="s">
    labels.push('${s[1]}');
    data.push(${s[2]});
    </c:forEach>

    let labels2 = [];
    let data2 = [];
    <c:forEach items="${statsCountQuestion}" var="sc">
    labels2.push('${sc[3]}');
    data2.push(${sc[4]});
    </c:forEach>

    let labels3 = [];
    let data3 = [];
    <c:forEach items="${statsRevenueByMonth}" var="sc">
    labels3.push('${sc[0]}');
    data3.push(${sc[1]});
    </c:forEach>

    function drawChart(ctx, labels, data, title) {
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                        label: title,
                        data: data,
                        borderWidth: 1,
                        backgroundColor: ['red', 'green', 'blue', 'brown']
                    }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
    }

    window.onload = function () {
        let ctx1 = document.getElementById("myChart1");
        let ctx2 = document.getElementById("myChart2");
        let ctx3 = document.getElementById("statsRevenueByMonth");

        drawChart(ctx1, labels, data, 'Responses Count');
        drawChart(ctx2, labels2, data2, '${statsCountQuestion[0][1]}');
        drawChart(ctx3, labels3, data3, 'Monthly Revenue');

        let surveyTitle = document.getElementById("surveyTitle");
        let questionTitle = document.getElementById("questionTitle");
        let monthTitle = document.getElementById("monthTitle");
        let yearTitle = document.getElementById("yearTitle");

        surveyTitle.innerText += ' ${statsCountQuestion[0][0]}';
        questionTitle.innerText += ' ${statsCountQuestion[0][2]}';

        monthTitle.innerText += ' ${month}';
        yearTitle.innerText += ' ${year}'
    };
</script>
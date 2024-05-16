<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-center text-info mt-1">Statistic</h1>

<div class="row">
    <div class="col-md-5 col-12" style="max-height: 280px; overflow-y: scroll">
        <table class="table ">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Response Count</th>
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
                <h5 id="surveyTitle">Survey:</h5>
                <h5 id="questionTitle">Question:</h5>
            </div>
            <form>
                <div class="form-floating  mb-3 mt-3">
                    <select class="form-select" id="surveyId" name="surveyId">
                        <c:forEach items="${surveys}" var="survey">
                            <option value="${survey.id}">${survey.title}</option>
                        </c:forEach>
                    </select>
                    <label for="surveyId" class="form-label">Survey name:</label>
                </div>
                <div class="form-floating  mb-3 mt-3">
                    <select class="form-select" id="questionId" name="questionId">
                        <c:forEach items="${questions}" var="question">
                            <option value="${question.id}">${question.questionText}</option>
                        </c:forEach>
                    </select>
                    <label for="questionId" class="form-label">Question name:</label>
                </div>
                <div class="form-floating  mb-3 mt-3">
                    <button class="btn btn-info">Filter</button>
                </div>
            </form>
        </div>
        <table class="table">
            <tr>
                <th>Option</th>
                <th>Response count</th>
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
        drawChart(ctx1, labels, data, 'Responses count');
        drawChart(ctx2, labels2, data2, '${statsCountQuestion[0][1]}');

        let surveyTitle = document.getElementById("surveyTitle");
        let questionTitle = document.getElementById("questionTitle");

        surveyTitle.innerText += ' ${statsCountQuestion[0][0]}';
        questionTitle.innerText += ' ${statsCountQuestion[0][2]}';
    };
</script>
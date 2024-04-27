<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-info mt-4">Surveys</h1>
<c:url value="/surveys/" var="action" />
<form:form method="post" action="${action}" modelAttribute="surveyForm">
    <div class="form-floating mb-3 mt-3">
        <c:choose>
            <c:when test="${empty surveyForm.survey.id}">
                <form:input class="form-control mb-4" id="title" placeholder="Title" path="survey.title" />
                <label for="title">Title</label>
                <div>
                    <h5 class="mb-4">Questions:</h5>
                    <div id="questionContainer">

                    </div>
                    <button class="btn btn-info mt-4 mb-4" type="button" onclick="addQuestion()">Add question</button>
                </div>

                <div class="d-flex justify-content-center">
                    <button class="btn btn-info" type="submit">Create Survey</button>
                </div>
            </c:when>
            <c:otherwise>
                <div class="d-flex ">
                    <h5>Title: </h5>
                    <p class="ms-2">${surveyForm.survey.title}</p>
                </div>
                <c:forEach var="q" items="${questions}" varStatus="status">
                    <div class="mb-3">
                        <p style="font-weight: bold" class="mb-1 ">Question ${status.index + 1}: ${q.question.questionText}</p>
                        <c:forEach var="o" items="${q.options}" varStatus="oStatus">
                            <c:if test="${q.question.questionType == 'Single choice'}">
                                <input id="q${status.index}o${oStatus.index}" name="q${status.index}" type="radio"/>
                                <label for="q${status.index}o${oStatus.index}">${o.optionText}</label>
                            </c:if>
                            <c:if test="${q.question.questionType == 'Multiple choice'}">
                                <input id="q${status.index}o${oStatus.index}" name=q${status.index}" type="checkbox"/>
                                <label for="q${status.index}o${oStatus.index}">${o.optionText}</label>
                            </c:if>
                            <c:if test="${q.question.questionType == 'Rating'}">
                                <input id="q${status.index}o${oStatus.index}" name=q${status.index}" type="radio"/>
                                <label for="q${status.index}o${oStatus.index}">${o.optionText}</label>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</form:form>



<script>
    let questionCount = 0;
    let optionCount = 0;
    function addQuestion() {
        optionCount = 0;
        let questionIndex = questionCount;
        const questionContainer = document.getElementById('questionContainer');
        const newQuestionDiv = document.createElement('div');
        newQuestionDiv.innerHTML = `
                <div>
        <label for="question\${questionIndex}">Question \${questionIndex + 1}:</label>
        <input id="question\${questionIndex}" name="questions[\${questionIndex}].questionText" required />
        </div>
                <div>
        <label for="questionType\${questionIndex}">Question type:</label>
        <select onchange="handleQuestionTypeChange(\${questionIndex})" id="questionType\${questionIndex}" name="questions[\${questionCount}].questionType" required>
                        <option value="Single choice">Single choice</option>
                        <option value="Multiple choice">Multiple Choice</option>
                        <option value="Rating">Rating</option>
                    </select>
                </div>
                <div>
                    <p>Options</p>
                    <div id="optionsContainer\${questionIndex}">
                        <input type="radio" name="questions\${questionIndex}_option" /> 
                        <input type="text" value="Option 1" name="questions[\${questionIndex}].surveyoptionSet[\${optionCount}].optionText" /><br/>
                    </div>
                    <button type="button" onclick="addOption(\${questionIndex})">Add option</button>
                </div>
            `;
        questionCount++;
        optionCount++;
        questionContainer.appendChild(newQuestionDiv);
    }

    function addOption(questionIndex) {
        const optionsContainer = document.getElementById(`optionsContainer\${questionIndex}`);
        const newOptionDiv = document.createElement('div');
        const questionType = document.getElementById(`questionType\${questionIndex}`).value;
        let optionIndex = optionCount;

        if (questionType === 'Single choice') {
            const inputDiv = document.createElement('div');
            inputDiv.innerHTML = `
            <input type="radio" /> 
            <input type="text" value="Option \${optionIndex + 1}" name="questions[\${questionIndex}].surveyoptionSet[\${optionIndex}].optionText" /><br/>
        `;
            optionsContainer.appendChild(inputDiv);
        } else if (questionType === 'Multiple choice') {
            const inputDiv = document.createElement('div');
            inputDiv.innerHTML = `
            <input type="checkbox" name="questions[\${questionIndex}]" /> 
            <input type="text" value="Option \${optionIndex + 1}" name="questions[\${questionIndex}].surveyoptionSet[\${optionIndex}].optionText" /><br/>
        `;
            optionsContainer.appendChild(inputDiv);
        }
        optionCount++;
    }

    function handleQuestionTypeChange(questionIndex) {
        optionCount = 0;
        const questionTypeSelect = document.getElementById(`questionType\${questionIndex}`);
        const questionType = questionTypeSelect.value;
        const optionsContainer = document.getElementById(`optionsContainer\${questionIndex}`);

        optionsContainer.innerHTML = '';

        if (questionType === 'Single choice') {
            const inputDiv = document.createElement('div');
            inputDiv.innerHTML = `
            <input type="radio" name="questions[\${questionIndex}]" /> 
            <input type="text" value="Option \${optionCount + 1}" name="questions[\${questionIndex}].surveyoptionSet[\${optionCount}].optionText" /><br/>
        `;
            optionsContainer.appendChild(inputDiv);
        } else if (questionType === 'Multiple choice') {
            const inputDiv = document.createElement('div');
            inputDiv.innerHTML = `
            <input type="checkbox" name="questions[\${questionIndex}]" /> 
            <input type="text" value="Option \${optionCount + 1}" name="questions[\${questionIndex}].surveyoptionSet[\${optionCount}].optionText" /><br/>
        `;
            optionsContainer.appendChild(inputDiv);
        } else if (questionType === 'Rating') {
            const inputDiv = document.createElement('div');
            inputDiv.innerHTML = `
            <div class="d-flex" >
                <input type="radio" name="questions[\${questionIndex}].surveyoptionSet[0].optionText" />
                <input style="width:60px;" type="text" value="1" name="questions[\${questionIndex}].surveyoptionSet[0].optionText" /><br/>
                <input type="radio" name="questions[\${questionIndex}].surveyoptionSet[1].optionText" /> 2<br/>
                <input style="width:60px;" type="text" value="2" name="questions[\${questionIndex}].surveyoptionSet[1].optionText" /><br/>
                <input type="radio" name="questions[\${questionIndex}].surveyoptionSet[2].optionText" /> 3<br/>
                <input style="width:60px;" type="text" value="3" name="questions[\${questionIndex}].surveyoptionSet[2].optionText" /><br/>            
                <input type="radio" name="questions[\${questionIndex}].surveyoptionSet[3].optionText" /> 4<br/>
                <input style="width:60px;" type="text" value="4" name="questions[\${questionIndex}].surveyoptionSet[3].optionText" /><br/>
                <input type="radio" name="questions[\${questionIndex}].surveyoptionSet[4].optionText" /> 5<br/>
                <input style="width:60px;" type="text" value="5" name="questions[\${questionIndex}].surveyoptionSet[4].optionText" /><br/>
            </div>
        `;
            optionsContainer.appendChild(inputDiv);
        }
        optionCount++;
    }
</script>


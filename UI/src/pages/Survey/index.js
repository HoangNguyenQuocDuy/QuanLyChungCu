import classNames from 'classnames/bind'
import styles from './survey.module.scss'
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setIsActiveNavbar, setIsSubmitServey } from '../../store/slice/appSlice';
import { useParams } from 'react-router-dom';
import Questions from '../../components/Questions';
import { clearQuestions } from '../../store/slice/questionSubmitSlice';
import newRequest from '../../untils/request';

const cx = classNames.bind(styles)

function Survey() {

    const dispatch = useDispatch()
    const { surveyId } = useParams()
    const surveys = useSelector(state => state.surveys)
    const survey = surveys.find(s => s.survey.id === parseInt(surveyId))
    const questionsSubmit = useSelector(state => state.questionsSubmit)
    const { user } = useSelector(state => state.user)
    const accessToken = localStorage.getItem("accessToken")

    const handleSubmitSurvey = () => {
        // console.log('questionsSubmit: ', quest   ionsSubmit)
        if (questionsSubmit.singleChoise.length > 0) {
            questionsSubmit.singleChoise.forEach(async q => {
                const data = {
                    surveyId: parseInt(surveyId),
                    userId: user.id,
                    optionId: q.optionId,
                    questionId: q.questionId
                }
                console.log(data)

                await newRequest.post('/surveyAnswers/', data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(data => {
                        console.log(data)
                    })
                    .catch(err => {
                        alert("ERR when post answer to survey: ", err)
                    })
            })
        }
        if (questionsSubmit.multipleChoise.length > 0) {
            questionsSubmit.multipleChoise.forEach(async q => {
                const data = {
                    surveyId: parseInt(surveyId),
                    userId: user.id,
                    optionId: q.optionId,
                    questionId: q.questionId
                }
                console.log(data)

                await newRequest.post('/surveyAnswers/', data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(data => {
                        console.log(data)
                    })
                    .catch(err => {
                        alert("ERR when post answer to survey: ", err)
                    })
            })
        }
    }


    useEffect(() => {
        dispatch(setIsActiveNavbar(true))
        dispatch(clearQuestions())
        // dispatch(setIsSubmitServey(false))
        // dispatch(clearQuestions())
    }, [])


    return (
        <div className={cx('container')}>
            {
                surveys &&
                <>
                    <h2 className={cx('title')}>
                        {survey.survey.title}
                    </h2>
                    <p className={cx('desc')}>
                        {survey.survey.description & survey.survey.description !== ''
                            ? '(' + survey.survey.description + ')' : ''}
                    </p>
                    <div className={cx('questions')}>
                        <Questions questions={survey.questions} />
                    </div>

                    <div className={cx('btnBox')}>
                        <button onClick={() => { handleSubmitSurvey() }}>Submit</button>
                    </div>
                </>
            }
            {/* <div>DUYYY</div> */}
        </div>
    );
}

export default Survey;
import classnames from 'classnames/bind'

import styles from './feedback.module.scss'
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useRef, useState } from 'react';
import { setIsActiveNavbar } from '../../store/slice/appSlice';
import { AiOutlinePlusCircle } from 'react-icons/ai';
import newRequest from '../../untils/request'
import moment from 'moment';
import ReactLoading from 'react-loading';
import { MdOutlineDeleteOutline } from "react-icons/md";
import { notify } from '../../untils/notification';
const cx = classnames.bind(styles)

function Feedbacks() {
    const dispatch = useDispatch()
    const titleRef = useRef()
    const [isShowAddBox, setIsShowAddBox] = useState(false)
    const [title, setTitle] = useState('')
    const [content, setContent] = useState('')
    const [isLoading, setIsLoading] = useState(false)
    const accessToken = localStorage.getItem("accessToken")
    const [feedbacks, setFeedbacks] = useState([])
    const { user } = useSelector(state => state.user)
    const [page, setPage] = useState(1);

    useEffect(() => {
        setIsLoading(false)
        dispatch(setIsActiveNavbar(true))
        handleGetFeedbacks()
    }, [])

    useEffect(() => {
        if (!isShowAddBox) {
            setTitle('')
            setContent('')
        } else {
            const timeout = () => {
                setTimeout(() => {
                    titleRef.current.focus()
                }, 210)
            }
            timeout()

            return () => {
                clearTimeout(timeout)
            }
        }
    }, [isShowAddBox])

    const handleScroll = () => {
        if (window.innerHeight + document.documentElement.scrollTop !== document.documentElement.offsetHeight || isLoading) {
            return;
        }
        handleGetFeedbacks();
    };

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [isLoading]);

    const handleAddFB = async (e) => {
        e.preventDefault()
        if (title.trim() === '') {
            alert('Error when create feedback: Title is required!')
        } else if (content.trim() === '') {
            alert('Error when create feedback: Content is required!')
        } else {
            setIsLoading(true)
            await newRequest.post(`/feedbacks/`, {
                userId: user.id,
                title, content
            }, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(data => {
                    setIsShowAddBox(false)
                    handleGetFeedbacks()
                    notify('Feedback has been created!', 'success')
                })
                .catch(err => {
                    notify('Error when create feedback: ' + err, 'error')
                })
                .finally(() => {
                    setIsLoading(false)
                })
        }
    }

    const handleGetFeedbacks = async () => {
        setIsLoading(true)
        await newRequest.get(`/feedbacks/?userId=${user.id}&page=${page}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then((data) => {
                console.log(data.data)
                setFeedbacks(prevItems => [...prevItems, ...data.data]);
                setPage(prevPage => prevPage + 1);
                setIsLoading(false)
                if (isShowAddBox) {
                    setIsShowAddBox(false)
                }
            })
            .catch(err => {
                alert('Error when get feedbacks: ', err)
            })
    }

    const handleDeleteFeedback = async (id) => {
        if (window.confirm('Do you want to delete this feedback?')) {
            setIsLoading(true)
            await newRequest.delete(`/feedbacks/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then((data) => {
                    notify('Feedback has been deleted!', 'success')
                    handleGetFeedbacks()
                    if (isShowAddBox) {
                        setIsShowAddBox(false)
                    }
                })
                .catch(err => {
                    notify(`Error when deleting feedback with id=${id}: ` + err, 'error')
                })
                .finally(() => {
                    setIsLoading(false)
                })
        }
    }

    return (
        <div className={cx('container')}>
            <div className={cx('btnBox', 'addNewBox')}>
                <button onClick={() => { setIsShowAddBox(!isShowAddBox) }}>Add new
                    <AiOutlinePlusCircle size={20} />
                </button>
            </div>

            <form onSubmit={(e) => { handleAddFB(e) }} className={cx('addNewFeedbackBox', { active: isShowAddBox })}>
                <div className={cx('wrapper')}>
                    <label htmlFor='title'>Title</label>
                    <div className={cx('title')}>
                        <input ref={titleRef} onChange={(e) => { setTitle(e.target.value) }} value={title} id='title' placeholder='Title' />
                    </div>
                    <label htmlFor='content'>Content</label>
                    <div className={cx('content')}>
                        <textarea onChange={(e) => { setContent(e.target.value) }} value={content} id='content' placeholder='Content' />
                    </div>
                    <div className={cx('btnBox', 'addBtn')}>
                        <button type='submit' >Add feedback</button>
                    </div>
                </div>
            </form>

            <div className={cx('wrapper')}>
                <table className={cx('table')} cellSpacing="0" cellPadding="0">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Content</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            !isLoading ?
                                feedbacks && feedbacks.map(f => (
                                    <tr>
                                        <td>{f.title}</td>
                                        <td>{f.content}</td>
                                        <td>{moment(f.createdAt).format("HH:mm:ss-DD/MM/YYYY")}</td>
                                        <td>
                                            <div className={cx('actionIconBox')}>
                                                <div onClick={() => { handleDeleteFeedback(f.id) }} className={cx('trashIcon')}><MdOutlineDeleteOutline size={24} /></div>
                                            </div>
                                        </td>
                                    </tr>
                                )) : <div className={cx('loadingBox')}>
                                    <ReactLoading type='spin' color={'#999'} height={'20%'} width={'20%'} />
                                </div>
                        }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Feedbacks;
import classnames from 'classnames/bind'
import '../../components/GlobalStyles/globalStyle.scss'

import styles from './personal.module.scss'
import { useDispatch, useSelector } from 'react-redux';
import { FaArrowLeftLong, FaCheck, FaPlus, FaRegPenToSquare } from 'react-icons/fa6';
import images from '../../assets/images';
import { NavLink } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';
import ReactLoading from 'react-loading';
import { PiTrashSimpleBold } from 'react-icons/pi';
import AddRelative from '../../components/AddRelative';
import newRequest from '../../untils/request';
import { setIsActiveNavbar, setIsLoadRelative } from '../../store/slice/appSlice';
import { deleteRelative } from '../../store/slice/relativesSlice';
import { loadUser } from '../../store/slice/userSlice';
import { ToastContainer, toast } from 'react-toastify';
import { notify } from '../../untils/notification'

const cx = classnames.bind(styles)

function Personal() {
    const dispatch = useDispatch()
    const { user } = useSelector(state => state.user)
    console.log('user: ', user)

    const relatives = useSelector(state => state.relatives)
    const { isLoadRelative } = useSelector(state => state.app)
    const accessToken = localStorage.getItem('accessToken')

    const [isChangeFirstName, setIsChangeFirstName] = useState(false)
    const [isChangeLastName, setIsChangeLastName] = useState(false)
    const [isChangeEmail, setIsChangeEmail] = useState(false)
    const [isChangePhone, setIsChangePhone] = useState(false)

    const [firstname, setFirstName] = useState(user.firstname)
    const [lastname, setLastname] = useState(user.lastname)
    const [email, setEmail] = useState(user.email)
    const [phone, setPhone] = useState(user.phone)
    const [selectedImage, setSelectedImage] = useState()

    const [selectedRelative, setSelectedRelative] = useState()

    const firstnameRef = useRef()
    const lastnameRef = useRef()
    const emailRef = useRef()
    const phoneRef = useRef()
    const imgRef = useRef()

    const [isChangeAvatar, setIsChangeAvatar] = useState(false)
    const [updatingUser, setUpdatingUser] = useState(false)

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setSelectedImage(reader.result);
            };
            setIsChangeAvatar(true)
            reader.readAsDataURL(file);
        }
    };

    useEffect(() => {
        if (isLoadRelative) {
            dispatch(setIsLoadRelative(false))
        }
        dispatch(setIsActiveNavbar(true))
        getUserById()
    }, [])

    const handleDeleteRelative = async (id) => {
        if (window.confirm('Do you want to delete this relative?')) {
            dispatch(setIsLoadRelative(true))
            await newRequest.delete(`/relatives/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(data => {
                    console.log(data.data)
                    dispatch(deleteRelative(id))
                    notify('Delete relative successfully!', 'success')
                })
                .catch(error => {
                    notify('Error deleting relative:' + error, 'error')
                })
                .finally(() => {
                    dispatch(setIsLoadRelative(false))
                })
        }
    }

    const getUserById = async () => {
        await newRequest.get(`/_users/?userId=${user.id}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then(data => {
                console.log('data: ', data.data)
                console.log('user: ', user)
                dispatch(loadUser(data.data))
            })
            .catch(err => {
                console.log('Error getting user: ', err)
            })
    }

    const handleChangeUserInfo = async () => {
        if (firstname.trim() !== user.firstname || lastname.trim() !== user.lastname
            || email.trim() !== user.email || phone.trim() !== user.phone || isChangeAvatar) {
            const formData = new FormData();
            formData.append('firstname', firstname.trim());
            formData.append('lastname', lastname.trim());
            formData.append('email', email.trim());
            formData.append('phone', phone.trim());

            if (isChangeAvatar) {
                const avatarFile = document.getElementById('file').files[0];
                formData.append('files', avatarFile);
            }
            setUpdatingUser(true)
            await newRequest.post(`/users/${user.id}`, formData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(async data => {
                    await getUserById()
                    setIsChangeFirstName(false)
                    setIsChangeLastName(false)
                    setIsChangeEmail(false)
                    setIsChangePhone(false)
                    notify('Update infomations successfully!', 'success')
                })
                .catch(err => {
                    notify('Error updating user: ' + err, 'error')
                })
                .finally(() => {
                    setUpdatingUser(false)
                })
        }
    }

    return (
        <div className={cx('container')}>
            <ToastContainer />
            <div className={cx('wrapper')}>
                <div className={cx('backBox')}>
                    <NavLink to={'/'} className={cx('logoBox')}>
                        <FaArrowLeftLong size={24} />
                        <div className={cx('imgBox')}>
                            <img src={images.logo_active} alt={'logo'} />
                        </div>
                        <div className={cx('title')}>DVApartment</div>
                    </NavLink>
                </div>
                <div className={cx('aside')}>
                    <div className={cx('personal')}>
                        <div className={cx('infomations')}>
                            <h3>Personal Infomations</h3>
                            <div className={cx('infoBox')}>
                                <div className={cx('info-casual')}>
                                    <div className={cx('fieldBox')}>
                                        <label htmlFor='firstname'>First name</label>
                                        <div className={cx('field')}>
                                            {
                                                !isChangeFirstName ?
                                                    <>
                                                        <span>{firstname}</span>
                                                        <FaRegPenToSquare onClick={() => {
                                                            setIsChangeFirstName(true)
                                                            setTimeout(() => { firstnameRef.current.focus() }, 10)
                                                        }} size={24} className={cx('changeIcon')} />
                                                    </> :
                                                    <>
                                                        <input ref={firstnameRef} value={firstname}
                                                            onChange={(e) => { setFirstName(e.target.value) }}
                                                            id='firstname' placeholder='First name...' />
                                                        <FaCheck onClick={() => {
                                                            setIsChangeFirstName(false)
                                                        }} size={24} className={cx('checkIcon')} />
                                                    </>
                                            }
                                        </div>
                                    </div>
                                    <div className={cx('fieldBox')}>
                                        <label htmlFor='lastname'>Last name</label>
                                        <div className={cx('field')}>
                                            {
                                                !isChangeLastName ?
                                                    <>
                                                        <span>{lastname}</span>
                                                        <FaRegPenToSquare onClick={() => {
                                                            setIsChangeLastName(true)
                                                            setTimeout(() => { lastnameRef.current.focus() }, 10)
                                                        }} size={24} className={cx('changeIcon')} />
                                                    </> :
                                                    <>
                                                        <input ref={lastnameRef} value={lastname}
                                                            onChange={(e) => { setLastname(e.target.value) }}
                                                            id='lastname' placeholder='Last name...' />
                                                        <FaCheck onClick={() => { setIsChangeLastName(false) }} size={24} className={cx('checkIcon')} />
                                                    </>
                                            }
                                        </div>
                                    </div>
                                    <div className={cx('fieldBox')}>
                                        <label htmlFor='email'>Email</label>
                                        <div className={cx('field')}>
                                            {
                                                !isChangeEmail ?
                                                    <>
                                                        <span>{email}</span>
                                                        <FaRegPenToSquare onClick={() => {
                                                            setIsChangeEmail(true)
                                                            setTimeout(() => { emailRef.current.focus() }, 10)
                                                        }} size={24} className={cx('changeIcon')} />
                                                    </> :
                                                    <>
                                                        <input type='email' ref={emailRef} value={email}
                                                            onChange={(e) => { setEmail(e.target.value) }}
                                                            id='email' placeholder='Email...' />
                                                        <FaCheck size={24} onClick={() => { setIsChangeEmail(false) }} className={cx('checkIcon')} />
                                                    </>
                                            }
                                        </div>
                                    </div>
                                    <div className={cx('fieldBox')}>
                                        <label htmlFor='phone'>Phone</label>
                                        <div className={cx('field')}>
                                            {
                                                !isChangePhone ?
                                                    <>
                                                        <span>{phone}</span>
                                                        <FaRegPenToSquare onClick={() => {
                                                            setIsChangePhone(true)
                                                            setTimeout(() => { phoneRef.current.focus() }, 10)
                                                        }} size={24} className={cx('changeIcon')} />
                                                    </> :
                                                    <>
                                                        <input ref={phoneRef} type='number' value={phone}
                                                            onChange={(e) => { setPhone(e.target.value) }}
                                                            id='phone' placeholder='Phone...' />
                                                        <FaCheck onClick={() => { setIsChangePhone(false) }} size={24} className={cx('checkIcon')} />
                                                    </>
                                            }
                                        </div>
                                    </div>
                                </div>
                                <div onClick={() => { imgRef.current.click() }} className={cx('avatar')}>
                                    <input id='file' onChange={handleFileChange} hidden={true} ref={imgRef} type="file" accept="image/*" />
                                    <img src={selectedImage ? selectedImage : user.avatar ? user.avatar : images.logo_active} alt='avatar' />
                                    <FaRegPenToSquare onClick={() => {

                                    }} size={24} className={cx('changeIcon')} />
                                </div>
                            </div>
                            <div className={cx('btnBox')}>
                                <button onClick={handleChangeUserInfo} className={cx('btn', { prevent: updatingUser })}>
                                    Update
                                </button>
                                {updatingUser && <ReactLoading className={cx('loading')} type='spin' color={'#999'} height={'5%'} width={'5%'} />}
                            </div>
                        </div>
                        <div className={cx('relatives')}>
                            <div className={cx('titleBox')}>
                                <h3>Relatives</h3>
                                <FaPlus size={24} className={cx('plusIcon')} />
                            </div>
                            <AddRelative relative={selectedRelative} />

                            <table className={cx('table')} cellSpacing="0" cellPadding="0">
                                <thead>
                                    <tr>
                                        <th>First name</th>
                                        <th>Last name</th>
                                        <th>Relative</th>
                                        <th>Avatar</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        relatives &&
                                        relatives.map(r => (
                                            <tr key={r.id}>
                                                <td>{r.firstname}</td>
                                                <td>{r.lastname}</td>
                                                <td>{r.type}</td>
                                                <td>
                                                    <img src={r.avatar} alt='avatar' />
                                                </td>
                                                <td>
                                                    <PiTrashSimpleBold onClick={() => { handleDeleteRelative(r.id) }} size={24} className={cx('trashIcon')} />

                                                    <FaRegPenToSquare onClick={() => {
                                                        setSelectedRelative(r)
                                                    }} size={24} className={cx('changeIcon')} />

                                                </td>
                                            </tr>
                                        ))
                                    }
                                    {isLoadRelative && <ReactLoading className={cx('loading')} type='spin' color={'#999'} height={'5%'} width={'5%'} />}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>);
}

export default Personal;
import classnames from 'classnames/bind'
import { HiArrowLeft, HiOutlineArrowRight } from "react-icons/hi2";
import styles from './login.module.scss'
import { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux';
import { fetchLogin } from '../../store/slice/accountSlice';
import { loadUser } from '../../store/slice/userSlice';
import { useNavigate } from 'react-router-dom';
import routes from '../../config/routes';
import { fetchRelatives } from '../../store/slice/relativesSlice';
import { ToastContainer } from 'react-toastify';
import { notify } from '../../untils/notification';
import newRequest from '../../untils/request';
import ReactLoading from 'react-loading';
import { setIsActiveNavbar } from '../../store/slice/appSlice';

const cx = classnames.bind(styles)

function Login() {
    const [isActive, setIsActive] = useState(false)
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [confirmedNewPassword, setConfirmedNewPassword] = useState('')
    const [email, setEmail] = useState('')
    const [err, setErr] = useState('')
    const [selectedAvatar, setSelectedAvatar] = useState()
    const [isChangeAvatar, setIsChangeAvatar] = useState(false)
    const [isNewUser, setIsNewUser] = useState(false)
    const [isLogin, setIsLogin] = useState(false)
    const [isResetPassword, setIsResetPassword] = useState(false)
    const [isUpdateUser, setIsUpdateUser] = useState(false)

    const [userId, setUserId] = useState()

    const accessToken = localStorage.getItem('accessToken')

    const dispatch = useDispatch()
    const navigate = useNavigate()

    useEffect(() => {
        dispatch(setIsActiveNavbar(true))
    }, [])

    const handleRAvatarSelected = (event) => {
        const file = event.target.files[0];
        if (file) {
            setIsChangeAvatar(true)
            const reader = new FileReader();
            reader.onloadend = () => {
                setSelectedAvatar(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleLogin = (e) => {
        e.preventDefault()

        if (username.trim() === '') {
            alert('Username is required!')
        }
        else if (password.trim() === '') {
            alert('Password is required!')
        } else {
            setIsLogin(true)
            dispatch(fetchLogin({ username, password }))
                .then(action => {
                    if (action.error) {
                        setErr(action.payload.message)
                    } else {
                        setIsLogin(false)
                        dispatch(loadUser(action.payload))
                        dispatch(fetchRelatives({ userId: action.payload.user.id, accessToken: action.payload.accessToken }))
                        localStorage.setItem('accessToken', action.payload.accessToken)
                        if (action.payload.user.status !== 'New') {
                            navigate(routes.home)
                        } else {
                            setUserId(action.payload.user.id)
                            setIsNewUser(true)
                        }
                    }
                })
        }
    }

    const handleUpdateUser = async (e) => {
        e.preventDefault()
        if (newPassword.trim() === '') {
            notify('New password is required!', 'error')
        }
        else if (confirmedNewPassword.trim() === '') {
            notify('Confirmed password is required!', 'error')
        } else if (newPassword !== confirmedNewPassword) {
            notify('New password and confirmed password does not match!', 'error')
        } else if (!selectedAvatar) {
            notify('Avatar is required!', 'error')
        } else {
            setIsUpdateUser(true)
            const formData = new FormData();
            formData.append('password', newPassword.trim());

            if (isChangeAvatar) {
                const avatarFile = document.getElementById('file').files[0];
                formData.append('files', avatarFile);
            }

            console.log('formData: ', userId)
            await newRequest.post(`/users/${userId}`, formData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(async data => {
                    notify('Update infomations successfully!', 'success')
                    setPassword(newPassword)
                    setIsNewUser(false)
                })
                .catch(err => {
                    notify('Error updating user: ' + err, 'error')
                })
                .finally(() => {
                    setIsUpdateUser(false)
                })
        }
    }

    return (
        <div className={cx('container')}>
            <ToastContainer />
            <div className={cx('wrapper')}>
                <div className={cx('formBox')}>
                    <form className={cx('form-forgot', { 'active': !isActive })}>
                        <h3>Forgot password</h3>
                        <div className={cx('form-item')}>
                            <input
                                type="email"
                                className="form-control"
                                placeholder="Enter your email"
                                value={email}
                                onChange={(e) => {
                                    if (err !== '') {
                                        setErr('')
                                    }
                                    setEmail(e.target.value)
                                }}
                            />
                        </div>

                        <div className={cx('buttonBox')}>
                            <button type="submit" className="btn btn-primary">
                                Submit
                            </button>
                            {!isResetPassword && <ReactLoading className={cx('loading')} type='spin' color={'#999'} height={'5%'} width={'5%'} />}
                        </div>

                        {/* <div className={cx('btnBox')}>
                            <button type='submit' className={cx('btn', { prevent: isResetPassword })}>
                                Submit
                            </button>
                        </div> */}
                        <div className={cx('over')}></div>
                    </form>

                    <form onSubmit={handleLogin} className={cx('form-login', { 'active': !isActive && !isNewUser })}>
                        <h3>Sign In</h3>
                        <div className={cx('form-item')}>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter your username"
                                value={username}
                                onChange={(e) => {
                                    if (err !== '') {
                                        setErr('')
                                    }
                                    setUsername(e.target.value)
                                }}
                            />
                        </div>

                        <div className={cx('form-item')}>
                            <input
                                type="password"
                                className="form-control"
                                placeholder="Enter your password"
                                value={password}
                                onChange={(e) => {
                                    if (err !== '') {
                                        setErr('')
                                    }
                                    setPassword(e.target.value)
                                }}
                            />
                        </div>
                        {err && <p className={cx('errMessage')}>{err}</p>}
                        <div className={cx('btnBox')}>
                            <button type='submit' className={cx('btn', { prevent: isLogin })}>
                                Login
                            </button>
                            {isLogin && <ReactLoading className={cx('loading')} type='spin' color={'#555'} />}
                        </div>
                    </form>

                    <form onSubmit={handleUpdateUser} className={cx('form-login', { 'active': isNewUser })}>
                        <h3>New User</h3>
                        <div className={cx('form-item')}>
                            <input
                                type="password"
                                className="form-control"
                                placeholder="New password"
                                value={newPassword}
                                onChange={(e) => {
                                    if (err !== '') {
                                        setErr('')
                                    }
                                    setNewPassword(e.target.value)
                                }}
                            />
                        </div>

                        <div className={cx('form-item')}>
                            <input
                                type="password"
                                className="form-control"
                                placeholder="Confirmed new password"
                                value={confirmedNewPassword}
                                onChange={(e) => {
                                    if (err !== '') {
                                        setErr('')
                                    }
                                    setConfirmedNewPassword(e.target.value)
                                }}
                            />
                        </div>
                        <div className={cx('form-item')}>
                            <div className={cx('avatarBox')}>
                                <div className={cx('_avatar')}>
                                    <input placeholder='Avatar' onChange={handleRAvatarSelected} id='file' type='file' accept="image/*" />
                                </div>
                                {
                                    selectedAvatar &&
                                    <div>
                                        <img src={selectedAvatar} alt='avatar' />
                                    </div>
                                }
                            </div>
                        </div>
                        {err && <p className={cx('errMessage')}>{err}</p>}
                        <div className={cx('btnBox')}>
                            <button type='submit' className={cx('btn', { prevent: isUpdateUser })}>
                                Submit
                            </button>
                            {isUpdateUser && <ReactLoading className={cx('loading')} type='spin' color={'#555'} />}
                        </div>
                    </form>
                </div>
                <div className={cx('introBox')}>
                    <div className={cx('introLogin', { 'active': !isActive })}>
                        <h3>Welcome to DVApartment</h3>
                        <p>Let's join with us to explore more!</p>
                        <div className={cx('buttonBox')}>
                            <button onClick={() => setIsActive(!isActive)}>
                                <HiArrowLeft size={24} />
                                <span>Forgot password</span>
                            </button>
                        </div>
                    </div>
                    <div className={cx('introForgot', { 'active': isActive })}>
                        <h3>Don't worry to much about that</h3>
                        <p>Let us know your email to get token to reset your password!</p>
                        <div className={cx('buttonBox')}>
                            <button onClick={() => setIsActive(!isActive)}>
                                <span>Login</span>
                                <HiOutlineArrowRight size={24} />
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login
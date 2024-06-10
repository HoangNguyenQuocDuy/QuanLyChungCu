import classnames from 'classnames/bind'
import '../../components/GlobalStyles/globalStyle.scss'

import styles from './personal.module.scss'
import { useSelector } from 'react-redux';
import { FaArrowLeftLong, FaCheck, FaRegPenToSquare } from 'react-icons/fa6';
import images from '../../assets/images';
import { NavLink } from 'react-router-dom';
import { useRef, useState } from 'react';

const cx = classnames.bind(styles)

function Personal() {
    const { user } = useSelector(state => state.user)
    const [isChangeFirstName, setIsChangeFirstName] = useState(false)
    const [isChangeLastName, setIsChangeLastName] = useState(false)
    const [isChangeEmail, setIsChangeEmail] = useState(false)
    const [isChangePhone, setIsChangePhone] = useState(false)

    const [firstname, setFirstName] = useState(user.firstname)
    const [lastname, setLastname] = useState(user.lastname)
    const [email, setEmail] = useState(user.email)
    const [phone, setPhone] = useState(user.phone)
    const [selectedImage, setSelectedImage] = useState()

    const firstnameRef = useRef()
    const lastnameRef = useRef()
    const emailRef = useRef()
    const phoneRef = useRef()
    const imgRef = useRef()

    console.log(firstname)


    const accessToken = localStorage.getItem('accessToken')

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setSelectedImage(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    return (
        <div className={cx('container')}>
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
                            <div className={cx('btnBox')}>
                                <button className={cx('btn')}>Update</button>
                            </div>
                        </div>
                        <div onClick={() => { imgRef.current.click() }} className={cx('avatar')}>
                            <input onChange={handleFileChange} hidden={true} ref={imgRef} type="file" accept="image/*" />
                            <img src={selectedImage ? selectedImage : user.avatar ? user.avatar : images.logo_active} alt='avatar' />
                            <FaRegPenToSquare onClick={() => {

                            }} size={24} className={cx('changeIcon')} />
                        </div>
                    </div>
                    <div className={cx('relative')}>

                    </div>
                </div>
            </div>
        </div>);
}

export default Personal;
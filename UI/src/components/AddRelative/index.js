import classnames from 'classnames/bind'
import ReactLoading from 'react-loading';

import styles from './addRelative.module.scss'
import { useEffect, useState } from 'react'
import { IoIosArrowDown } from 'react-icons/io'
import newRequest from '../../untils/request'
import { useDispatch, useSelector } from 'react-redux'
import { fetchRelatives } from '../../store/slice/relativesSlice'
import { setIsLoadRelative } from '../../store/slice/appSlice';
import { notify } from '../../untils/notification'

const cx = classnames.bind(styles)

function AddRelative({ relative }) {
    const types = ['Father/Mother', 'Wife/Husband', 'Children', 'Others']
    const { user } = useSelector(state => state.user)
    const dispatch = useDispatch()

    const [rFitstname, setRFirstname] = useState(relative ? relative.firstname : '')
    const [rLastname, setRLastname] = useState(relative ? relative.lastname : '')
    const [rType, setRType] = useState(relative ? relative.type : types[0])
    const [typeActive, setTypeActive] = useState(false)
    const [rSelectedAvatar, setRSelectedAvatar] = useState()
    const [isChangeAvatar, setIsChangeAvatar] = useState(false)

    const accessToken = localStorage.getItem('accessToken')

    useEffect(() => {
        if (relative) {
            setRFirstname(relative.firstname)
            setRLastname(relative.lastname)
            setRType(relative.type)
            setRSelectedAvatar(relative.avatar)
            setTypeActive(false)
        }
    }, [relative])

    const handleRAvatarSelected = (event) => {
        const file = event.target.files[0];
        if (file) {
            setIsChangeAvatar(true)
            const reader = new FileReader();
            reader.onloadend = () => {
                setRSelectedAvatar(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleRelative = async () => {
        if (rFitstname.trim() === '') {
            alert('First name is required!')
        } else if (rLastname.trim() === '') {
            alert('Last name is required!')
        } else if (!rSelectedAvatar) {
            alert('Avatar is required!')
        }
        else if (relative) {
            if (rFitstname.trim() !== relative.firstname || rLastname.trim() !== relative.lastname
                || rType !== relative.type || isChangeAvatar) {
                const formData = new FormData();
                formData.append('firstname', rFitstname.trim());
                formData.append('lastname', rLastname.trim());
                formData.append('type', rType);

                if (isChangeAvatar) {
                    const avatarFile = document.getElementById('rfile').files[0];
                    formData.append('files', avatarFile);
                }

                try {
                    dispatch(setIsLoadRelative(true))
                    await newRequest.post(`/relatives/${relative.id}`, formData, {
                        headers: {
                            "Content-Type": 'multipart/form-data',
                            Authorization: `Bearer ${accessToken}`
                        }
                    })
                        .then(data => {
                            console.log(data.data);
                            dispatch(fetchRelatives({ userId: user.id, accessToken }))
                            setRFirstname('')
                            setRLastname('')
                            setRSelectedAvatar()
                            setRType(types[0])
                            dispatch(setIsLoadRelative(false))
                            notify('Relative has been updated!', 'success')
                        })
                } catch (error) {
                    notify('Error updating relative: ' + error, 'error')
                }
            }
        } else {
            const formData = new FormData();
            formData.append('firstname', rFitstname.trim());
            formData.append('lastname', rLastname.trim());
            formData.append('type', rType);
            formData.append('userId', user.id);

            if (isChangeAvatar) {
                const avatarFile = document.getElementById('rfile').files[0];
                formData.append('files', avatarFile);
            }

            try {
                dispatch(setIsLoadRelative(true))
                await newRequest.post(`/relatives/`, formData, {
                    headers: {
                        "Content-Type": 'multipart/form-data',
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                    .then(data => {
                        console.log(data.data);
                        dispatch(fetchRelatives({ userId: user.id, accessToken }))
                        setRFirstname('')
                        setRLastname('')
                        setRSelectedAvatar()
                        setRType(types[0])
                        dispatch(setIsLoadRelative(false))
                        notify('Relative has been created!', 'success')
                    })
            } catch (error) {
                notify('Error updating relative: ' + error, 'error')
            }
        }
    }

    return (
        <div className={cx('wrapper')}>
            <div className={cx('addRelativeBox')}>
                <div className={cx('sw')}>
                    <div className={cx('fieldBox')}>
                        <label htmlFor='rFirstname'>First name</label>
                        <input id='rFirstname' value={rFitstname} onChange={(e) => { setRFirstname(e.target.value) }}
                            placeholder='First name...' />
                    </div>
                    <div className={cx('fieldBox')}>
                        <label htmlFor='rLastname'>Last name</label>
                        <input id='rLastname' value={rLastname} onChange={(e) => { setRLastname(e.target.value) }}
                            placeholder='Last name...' />
                    </div>
                    <div className={cx('fieldBox')}>
                        <label htmlFor='rType'>Type</label>
                        <div onClick={() => { setTypeActive(!typeActive) }} className={cx('type', 'selectedType')}>
                            <span>{rType}</span>
                            <IoIosArrowDown size={20} className={cx('arrowIcon')} />
                        </div>
                        <ul className={cx('list', { 'active': typeActive })}>
                            {types.map((t, i) => <li className={cx('type', { active: t === rType })}
                                onClick={() => {
                                    setRType(t)
                                    setTypeActive(false)
                                    console.log('ddd')
                                }} key={i} >{t}</li>)}
                        </ul>
                    </div>
                </div>
                <div>
                    <div className={cx('avatarBox')}>
                        <div className={cx('_avatar')}>
                            <label htmlFor='rfile'>Avatar</label>
                            <input onChange={handleRAvatarSelected} id='rfile' type='file' accept="image/*" />
                        </div>
                        <div>
                            <img src={rSelectedAvatar && rSelectedAvatar} alt='avatar' />
                        </div>
                    </div>
                </div>
            </div>
            <div className={cx('btnBox')}>
                <button onClick={handleRelative} className={cx('btn')}>{relative ? 'Update' : 'Add'}</button>
            </div>
        </div>
    );
}

export default AddRelative;
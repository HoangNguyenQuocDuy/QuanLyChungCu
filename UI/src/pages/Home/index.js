import classnames from 'classnames/bind'

import styles from './home.module.scss'

import Header from "../../components/Header";
import { useDispatch } from 'react-redux';
import { useEffect } from 'react';
import { setIsActiveNavbar } from '../../store/slice/appSlice';

const cx = classnames.bind(styles)

function Home() {
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(setIsActiveNavbar(false))
    }, [])

    return (<div className={cx('container')}>
        <div className={cx('header')}> <Header /></div>
    </div>);
}

export default Home;
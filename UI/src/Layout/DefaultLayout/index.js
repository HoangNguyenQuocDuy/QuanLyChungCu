import classnames from 'classnames/bind'

import styles from './defaultLayout.module.scss'
import Navbar from '../../components/Navbar';
import { useSelector } from 'react-redux';
import { ToastContainer } from 'react-toastify';

const cx = classnames.bind(styles)

function DefaultLayout({ children }) {

    const { isActiveNavBar } = useSelector(state => state.app)

    return (
        <>
            <Navbar />
            <ToastContainer />
            <div className={cx('wrapper', { 'nav': isActiveNavBar })}>
                {children}
            </div>
        </>
    );
}

export default DefaultLayout;
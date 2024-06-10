import classnames from 'classnames/bind'

import styles from './header.module.scss'
import Navbar from "../Navbar";

const cx = classnames.bind(styles)

function Header() {
    return (
        <div className={cx('wrapper')}>
            <Navbar />
        </div>
    );
}

export default Header;
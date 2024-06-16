import classnames from 'classnames/bind'
import styles from './card.module.scss'
import images from '../../assets/images'
import moment from 'moment';

const cx = classnames.bind(styles)

function Card({ card }) {
    console.log(card)

    return (
        <div className={cx('container')}>
            <h3>DV APARTMENT</h3>
            <div className={cx('infomations')}>
                <div className={cx('avatar')}>
                    <img src={card.relativeId.avatar || images.noAvatar} alt='avatar' />
                </div>
                <div className={cx('fields')}>
                    <div className={cx('field')}>
                        <span className={cx('name', 'title')}>Name: </span>
                        <span>{`${card.relativeId.firstname} ${card.relativeId.lastname}`}</span>
                    </div>
                    <div className={cx('field')}>
                        <span className={cx('room', 'title')}>Room: </span>
                        <span>{card.relativeId.userId.room.name}</span>
                    </div>
                    <div className={cx('field')}>
                        <span className={cx('cardType', 'title')}>Card type: </span>
                        <span>{card.licensePlates ? 'Entry card' : 'Parking card'}</span>
                    </div>
                    <div className={cx('field')}>
                        <span className={cx('title')}>Status: </span>
                        <span className={cx('status', {
                            cancel: card.status === 'Canceled', pending: card.status === 'Pending',
                            confirmed: card.status === 'Confirmed'
                        })}>{card.status}</span>
                    </div>
                    {
                        card.typeOfVehicle &&
                        <div className={cx('field')}>
                            <span className={cx('typeOfVehicle', 'title')}>Type of vehicle: </span>
                            <span>{card.typeOfVehicle}</span>
                        </div>
                    }
                    {
                        card.licensePlates &&
                        <div className={cx('field')}>
                            <span className={cx('licensePlates', 'title')}>License plates: </span>
                            <span>{card.licensePlates}</span>
                        </div>
                    }
                    <div className={cx('field')}>
                        <span className={cx('createdAt', 'title')}>License date: </span>
                        <span>{card.updatedAt ? moment(card.updatedAt).format("DD/MM/YYYY") : '-----'}</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Card;
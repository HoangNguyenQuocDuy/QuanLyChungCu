import classnames from 'classnames/bind'

import styles from './invoice.module.scss'
import { useDispatch, useSelector } from 'react-redux'
import { useEffect, useState } from 'react'
import { setIsActiveNavbar } from '../../store/slice/appSlice'
import newRequest from '../../untils/request'
import Status from '../../components/Status'
import moment from 'moment'
import { IoIosArrowDown } from 'react-icons/io'

const cx = classnames.bind(styles)

function Invoice() {
    const dispatch = useDispatch()
    const { user } = useSelector(state => state.user)
    const [invoices, setInvoices] = useState()
    const accessToken = localStorage.getItem('accessToken')
    const [statusActive, setStatusActive] = useState(false)
    const [typeActive, setTypeActive] = useState(false)
    const [selectedStatus, setSelectedStatus] = useState('')
    const [selectedType, setSelectedType] = useState('')

    console.log(user)

    const status = ['No select', 'Paid', 'Unpaid']
    const types = ['No select', 'Room', 'Electric', 'Water', 'Packing', 'Other']



    const handleGetInvoices = async () => {
        await newRequest.get(`/invoices/?room=${user.room.id}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then(data => {
                setInvoices(data.data)
            })
            .catch(err => {
                alert('Error when get invoices: ', err)
            })
    }

    const handlePayInvoice = async (invoicerentId, amount) => {
        await newRequest.get(`/payments/?amount=${amount}&userId=${user.id}&invoicerentId=${invoicerentId}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then(data => {
                window.open(data.data.url, '_blank')
            })
            .catch(err => {
                alert("ERR when pay this invoice: ", err)
            })
    }

    const handleSearchInvoices = async () => {
        let q = ''
        if (selectedStatus !== '' && selectedStatus !== 'No select') {
            q += `&status=${selectedStatus}`
        }
        if (selectedType !== '' && selectedType !== 'No select') {
            q += `&invoiceType=${selectedType}`
        }
        await newRequest.get(`/invoices/?room=${user.room.id}${q}&list`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then(data => {
                setInvoices(data.data)
            })
            .catch(err => {
                alert('Error when get invoices: ', err)
            })
    }

    useEffect(() => {
        dispatch(setIsActiveNavbar(true))
        handleGetInvoices()
    }, [])

    useEffect(() => {
        handleSearchInvoices()
    }, [selectedStatus, selectedType])

    return (
        <div className={cx('wrapper')}>
            <div className={cx('searchBox')}>
                <div className={cx('statusList')}>
                    <div onClick={() => { setStatusActive(!statusActive) }} className={cx('title')}>
                        <div>{selectedStatus === '' ? 'Status' : selectedStatus}</div>
                        <IoIosArrowDown size={20} />
                    </div>
                    <ul className={cx('list', { 'active': statusActive })}>
                        {status.map((t, i) => <li className={cx({ active: t === selectedStatus })}
                            onClick={() => {
                                setSelectedStatus(t)
                                setStatusActive(false)
                            }} key={i} >{t}</li>)}
                    </ul>
                </div>
                <div className={cx('typeList')}>
                    <div onClick={() => { setTypeActive(!typeActive) }} className={cx('title')}>
                        <div>{selectedType === '' ? 'Type' : selectedType}</div>
                        <IoIosArrowDown size={20} />
                    </div>
                    <ul className={cx('list', { 'active': typeActive })}>
                        {types.map((t, i) => <li className={cx({ active: t === selectedType })}
                            onClick={() => {
                                setSelectedType(t)
                                setTypeActive(false)
                            }} key={i} >{t}</li>)}
                    </ul>
                </div>
                {/* <div className={cx('buttonBox')}>
                    <button>Search</button>
                </div> */}
            </div>

            <div className={cx('invoices')}>
                {
                    invoices && invoices.map(item => (
                        <div key={item.id} className={cx('item')}>
                            <div className={cx('title', item.invoiceType.type.toLowerCase())}>
                                <p>{item.invoiceType.type}</p>
                            </div>
                            <div className={cx('info')}>
                                <div>
                                    <div className={cx('amount')}><span className={cx('title')}>Room:</span>{item.room.name}</div>
                                    <div className={cx('amount')}><span className={cx('title')}>Amount:</span>{item.amount} VND</div>
                                    <div className={cx('status')}>
                                        <span className={cx('title')}>Status:</span>
                                        <Status status={item.status} />
                                    </div>
                                    <div className={cx('dueDate')}>
                                        <span className={cx('title')}>Due date:</span>
                                        {moment(item.dueDate).format("HH:mm:ss DD/MM/YYYY")}
                                    </div>
                                </div>
                            </div>
                            <div className={cx('btnBox')}>
                                <button onClick={() => { handlePayInvoice(item.id, item.amount) }} className={cx('payBtn', { 'prevent': item.status === 'Paid' })}>Pay</button>
                            </div>
                        </div>
                    ))
                }
            </div>
        </div>
    );
}

export default Invoice;
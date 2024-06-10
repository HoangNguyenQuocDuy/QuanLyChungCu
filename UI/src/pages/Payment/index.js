import classnames from 'classnames/bind'

import styles from './payment.module.scss'
import { useDispatch, useSelector } from 'react-redux';
import { fetchPayment } from '../../store/slice/paymentSlice';
import { useEffect, useState } from 'react';
import moment from 'moment';
import Status from '../../components/Status';
import { IoIosArrowDown } from 'react-icons/io';
import { setIsActiveNavbar } from '../../store/slice/appSlice';
import newRequest from '../../untils/request';

const cx = classnames.bind(styles)

function Payment() {

    const dispatch = useDispatch()
    const accessToken = localStorage.getItem("accessToken")
    const payments = useSelector(state => state.payments)
    const { user } = useSelector(state => state.user)
    const [selectedMonth, setSelectedMonth] = useState(0)
    const [selectedYear, setSelectedYear] = useState(0)
    const [years, setYears] = useState([])
    const [activeMonth, setActiveMonth] = useState(false)
    const [activeYear, setActiveYear] = useState(false)
    const [paymentsSearch, setPaymentSearch] = useState([])
    const [isSearch, setIsSearch] = useState(false)

    const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]

    const handleInitYears = () => {
        const currentYear = new Date().getFullYear()
        for (let i = currentYear; i >= 2020; i--) {
            setYears(state => [...state, i])
        }
    }

    const handleSearchInvoices = async () => {
        if (selectedMonth !== 0 && selectedYear !== 0) {
            await newRequest.get(`/payments/list?userId=${user.id}&month=${selectedMonth}&year=${selectedYear}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })
                .then(data => {
                    if (data.data) {
                        setPaymentSearch(data.data)
                    } else {
                        setPaymentSearch([])
                    }
                    setIsSearch(true)
                })
                .catch(err => {
                    alert('ERR when search invoices: ', err)
                })
        }
    }
    console.log('paymentsSearch: ', paymentsSearch)

    useEffect(() => {
        dispatch(fetchPayment({ userId: user.id, accessToken }))
        dispatch(setIsActiveNavbar(true))
        if (years.length === 0) {
            handleInitYears()
        }
    }, [])

    return (
        <div className={cx('container')}>
            <div className={cx('searchBox')}>
                <div className={cx('monthList')}>
                    <div onClick={() => { setActiveMonth(!activeMonth) }} className={cx('title')}>
                        <div>{selectedMonth === 0 ? 'Month' : selectedMonth}</div>
                        <IoIosArrowDown size={20} />
                    </div>
                    <ul className={cx('list', { 'active': activeMonth })}>
                        {months.map((t, i) => <li className={cx({ active: t === selectedMonth })}
                            onClick={() => {
                                setSelectedMonth(t)
                                setActiveMonth(false)
                            }} key={i} >{t}</li>)}
                    </ul>
                </div>
                <div className={cx('yearList')}>
                    <div onClick={() => { setActiveYear(!activeYear) }} className={cx('title')}>
                        <div>{selectedYear === 0 ? 'Year' : selectedYear}</div>
                        <IoIosArrowDown size={20} />
                    </div>
                    <ul className={cx('list', { 'active': activeYear })}>
                        {years.sort().map((t, i) => <li className={cx({ active: t === selectedYear })}
                            onClick={() => {
                                setSelectedYear(t)
                                setActiveYear(false)
                            }} key={i} >{t}</li>)}
                    </ul>
                </div>

                <div onClick={handleSearchInvoices} className={cx('btnBox')}>
                    <button>Search</button>
                </div>
            </div>

            <div className={cx('wrapper')}>
                <table className={cx('table')} cellSpacing="0" cellPadding="0">
                    <thead>
                        <tr>
                            <th>Transaction No</th>
                            <th>Bank Tran No</th>
                            <th>Bank Code</th>
                            <th>Invoice type</th>
                            <th>Amount</th>
                            <th>Due Date</th>
                            <th>Payment Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            isSearch ?
                                paymentsSearch.map(p => (
                                    <tr>
                                        <td>{p.transactionNo}</td>
                                        <td>{p.bankTranNo}</td>
                                        <td>{p.bankCode}</td>
                                        <td className={cx('ct')}><Status status={p.invoice.invoiceType.type} /></td>
                                        <td>{p.amount} VND</td>
                                        <td>{moment(p.invoice.dueDate).format("HH:mm:ss-DD/MM/YYYY")}</td>
                                        <td>{moment(p.createdAt).format("HH:mm:ss-DD/MM/YYYY")}</td>
                                    </tr>
                                )) : payments && payments.map(p => (
                                    <tr>
                                        <td>{p.transactionNo}</td>
                                        <td>{p.bankTranNo}</td>
                                        <td>{p.bankCode}</td>
                                        <td className={cx('ct')}><Status status={p.invoice.invoiceType.type} /></td>
                                        <td>{p.amount} VND</td>
                                        <td>{moment(p.invoice.dueDate).format("HH:mm:ss-DD/MM/YYYY")}</td>
                                        <td>{moment(p.createdAt).format("HH:mm:ss-DD/MM/YYYY")}</td>
                                    </tr>
                                ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Payment;
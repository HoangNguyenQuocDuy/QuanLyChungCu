import classnames from 'classnames/bind'

import styles from './home.module.scss'

import Header from "../../components/Header";
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { setIsActiveNavbar } from '../../store/slice/appSlice';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { notify } from '../../untils/notification';
import { ToastContainer } from 'react-toastify';

const cx = classnames.bind(styles)

function Home() {
    const dispatch = useDispatch()

    const [stompClient, setStompClient] = useState(null);

    const { user } = useSelector(state => state.user)

    useEffect(() => {
        dispatch(setIsActiveNavbar(false))

        if (user) {
            const accessToken = localStorage.getItem("accessToken")
            const socket = new SockJS('http://localhost:8080/QuanLyChungCu/ws');
            const client = new Client({
                webSocketFactory: () => socket,
                connectHeaders: {
                    Authorization: `Bearer ${accessToken}`
                },
                debug: (str) => console.log(str),
            });

            client.onConnect = () => {
                console.log('Stomp client connected');
                setStompClient(client);

                client.subscribe(`/notification/lockers/${user.locker.id}`, (message) => {
                    console.log('Received message:', message.body);
                    notify(message.body, 'success');
                });
            };

            client.onStompError = (frame) => {
                console.error('Stomp client error:', frame);
            };

            client.activate();

            return () => {
                client.deactivate();
            };
        }
    }, []);

    useEffect(() => {
        if (stompClient) {
            const subscriptionOrderNotify = stompClient.subscribe(`/notification/lockers/${user.locker.id}`, (message) => {
                notify(message.body, 'success')
            })
            const subscriptionCardNotify = stompClient.subscribe(`/notification/relatives/user/${user.id}`, (message) => {
                let status = ''
                if (message.body.toLowerCase().includes('confirmed')) {
                    status = 'success'
                } else {
                    status = 'warn'
                }
                notify(message.body, status)
            })
            const subscriptionSurveyNotify = stompClient.subscribe(`/notification/surveys`, (message) => {
                notify(message.body, 'success')
            })

            return () => {
                subscriptionOrderNotify.unsubscribe()
                subscriptionCardNotify.unsubscribe()
                subscriptionSurveyNotify.unsubscribe()
            };
        }
    }, [stompClient])

    return (<div className={cx('container')}>
        <ToastContainer />
        <div className={cx('header')}>
            <Header />
        </div>
    </div>);
}

export default Home;
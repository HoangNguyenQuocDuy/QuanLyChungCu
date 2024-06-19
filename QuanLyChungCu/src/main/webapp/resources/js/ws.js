var stompClient = null;

$(document).ready(function () {
    connect();
});

const onConnect = () => {
    console.log('WebSocket is connected');
    stompClient.subscribe('/topic/notify', function (message) {
        console.log('Received: ' + message.body);
        $("#message").text(message.body);
    });
};

const onError = (error) => {
    console.error('Error connecting to WebSocket', error);
};

function connect() {
    var socket = new SockJS('/QuanLyChungCu/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnect, onError);
}
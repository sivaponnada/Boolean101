var stompClient = null;

function setConnected(connected) {
    $("#connectAdmin").prop("disabled", connected);
    $("#disconnectAdmin").prop("disabled", !connected);
    if (connected) {
        $("#conversationAdmin").show();
    }
    else {
        $("#conversationAdmin").hide();
    }
    $("#queries").html("");
}

function connectAdmin() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/queries', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnectAdmin() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/queries", {}, JSON.stringify({'name': 'queries'}));
}

function showGreeting(message) {
    $("#queries").append("<tr><td>" + message+ "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connectAdmin" ).click(function() { connectAdmin(); });
    $( "#disconnectAdmin" ).click(function() { disconnectAdmin(); });
    $( "#send" ).click(function() { sendName(); });
});
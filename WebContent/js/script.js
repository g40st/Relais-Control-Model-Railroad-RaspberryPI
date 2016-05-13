var trainSocket;

$(document).ready(function() {
    $("svg").height($(window).height()-5);

    // trainSocket anlegen
    var url = window.location.pathname;
    var path = url.lastIndexOf("/");
    var result = url.substring(0,path);
    url = 'ws://' + window.location.hostname + (location.port ? ':' + location.port :'') + result +'/train';
    trainSocket=new WebSocket(url);
    trainSocket.onopen = trainOpened;
    trainSocket.onclose = trainClosed;
    trainSocket.onerror = trainErrorHandler;
    trainSocket.onmessage = trainReceiveMessage;

    $(".trClick").click(function(event) {  
        var tmpTrack = event.target.getAttribute("class").split(" ");
        // Nachricht an Server schicken
        var switchTrack = {
            "Type": "1",
            "Length" : "1",
            "Content" : tmpTrack[0]
        };
        trainSocket.send(JSON.stringify(switchTrack));
    });

    $(".swClick").click(function(event) {  
        var tmpSwitch = event.target.getAttribute("class").split(" ");
        // Nachricht an Server schicken
        var switchChange = {
            "Type": "2",
            "Length" : "1",
            "Content" : tmpSwitch[0]
        };
        trainSocket.send(JSON.stringify(switchChange));
    }); 

    $(".sgClick").click(function(event) {  
        // Nachricht an Server schicken
        var signalChange = {
            "Type": "3",
            "Length" : "1",
            "Content" : "signal"
        };
        trainSocket.send(JSON.stringify(signalChange));
    });

    $(window).resize(function() {
        $("svg").height($(window).height() - 20);
    });
});


function trainOpened() {}

function trainClosed() {trainSocket.close();}

function trainErrorHandler(Event) {console.log("Fehler: " + Event.data);}

function trainReceiveMessage(message) {
    var msgServer = JSON.parse(message.data);
    console.log(msgServer);

    if(msgServer.Type == 1) {
        for (var i = 0; i < msgServer.Length; i++) {
            var tmpTrack = "." + msgServer.Content[i].Track + "paint";
            if(msgServer.Content[i].State) {
                trackActive(tmpTrack);
            } else {
                trackInactive(tmpTrack);
            }
        }
    } else if(msgServer.Type == 2) {
        for (var i = 0; i < msgServer.Length; i++) {
            var tmpSwitchStraight = "." + msgServer.Content[i].Switch + "Straight";
            var tmpSwitchLeft = "." + msgServer.Content[i].Switch + "Left";
            if(msgServer.Content[i].State) {
                switchStraight(tmpSwitchLeft,tmpSwitchStraight);
            } else {
                switchLeft(tmpSwitchLeft,tmpSwitchStraight);
            }
        };
    } else if(msgServer.Type == 3) {
        for (var i = 0; i < msgServer.Length; i++) {
            if(msgServer.Content[i].State) {
                $(".circleGreen").css({ fill: "green" });
                $(".circleRed").css({ fill: "white" });
                trackActive(".signal");
            } else {
                $(".circleGreen").css({ fill: "white" });
                $(".circleRed").css({ fill: "red" });
                trackInactive(".signal");
            }
        };
    }
}

function trackActive(tmpTrack) {
    $(tmpTrack).css({ stroke: "#40FF00" });
}

function trackInactive(tmpTrack) {
    $(tmpTrack).css({ stroke: "red" });   
}

function switchLeft(tmpSwitchLeft, tmpSwitchStraight) {
    $(tmpSwitchLeft).css({stroke: "#40FF00"});
    $(tmpSwitchStraight).css({stroke: "#FF8000"});
    $(tmpSwitchStraight).css({opacity: "0.5"});
    $(tmpSwitchLeft).css({opacity: "1.0"});
}

function switchStraight(tmpSwitchLeft, tmpSwitchStraight) {
    $(tmpSwitchLeft).css({stroke: "#FF8000"});
    $(tmpSwitchStraight).css({stroke: "#40FF00"});
    $(tmpSwitchStraight).css({opacity: "1.0"});
    $(tmpSwitchLeft).css({opacity: "0.5"});
}    
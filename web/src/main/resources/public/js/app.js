var getJSON = function(url) {
    return new Promise(function(resolve, reject) {
        var xhr = new XMLHttpRequest();
        xhr.open('get', url, true);
        xhr.responseType = 'json';
        xhr.onload = function() {
            var status = xhr.status;
            if (status == 200) {
                resolve(xhr.response);
            } else {
                reject(status);
            }
        };
        xhr.send();
    });
};
var loadPlayerHandCards = function(playerId) {
    getJSON('http://localhost:4567/cards/' + playerId + '/1').then(function(data) {
        var i;
        document.getElementById('player' + playerId).innerHTML = '';
        for (i = 0; i < data.cards.length; i += 1) {
            document.getElementById('player' + playerId).innerHTML +=
                '<a onclick="playCard(' + i + ', ' + playerId + ');" data-player-id="' + playerId
                + '" data-card-index="' + i + '">'
                + '<img src="img/' + cardNameToImageName(data.cards[i].name) + '.png" width="180" ' +
                'draggable="true" ondragstart="drag(event)" />'
                + '</a>';
        }
    });
};
var playCard = function(cardIndex, playerId) {
    var getJSON = function(url) {
        return new Promise(function(resolve, reject) {
            var xhr = new XMLHttpRequest();
            xhr.open('put', 'http://localhost:4567/play/' + cardIndex + '/' + playerId + '/1', true);
            xhr.responseType = 'json';
            xhr.onload = function() {
                var status = xhr.status;
                if (status == 200) {
                    resolve(xhr.response);
                    refreshCards();
                } else {
                    reject(xhr.response, status);
                }
            };
            xhr.send();
        });
    };
    getJSON(cardIndex, playerId).then(function(data) {
        document.getElementById('error').innerHTML = '';
    }, function (data, status) {
        console.log(data);
        document.getElementById('error').innerHTML = status + ' ' + data.error;
    });
};
var refreshCards = function() {
    loadPlayerHandCards(1);
    loadPlayerHandCards(2);
    loadPlayerHandCards(3);
};
var cardNameToImageName = function(cardName) {
    return cardName.toLowerCase().replace(/ /g, '-');
};

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    console.log(data);
    console.log(data.innerHTML);
}

refreshCards();
self.document.getElementById('trash').attachEvent('ondrop', function (event) {
    drop(event);
});
self.document.getElementById('trash').attachEvent('ondragover', function (event) {
    allowDrop(event);
});
getElementById('refresh').onclick(refreshCards);
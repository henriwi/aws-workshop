function padLeft(nr, n){
    return Array(n-String(nr).length+1).join('0')+nr;
}

var el = document.getElementById("grupper");
var str = "";

for(var i = 1; i <= 16; i++) {
    var nr = padLeft(i, 2);

    str = "<div class='gruppe'>";
    str += "<p>Gruppe " + i + "</p>";
    str += "<img src='https://s3.eu-central-1.amazonaws.com/aws-kurs-gruppe" + nr + "/bilde.jpg' />";
    str += "</div>";
    el.innerHTML += str;
}

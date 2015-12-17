/**
 * Created by jfn on 12/8/15.
 */

function showMes() {
    alert("Hello World!");
}

function toggle_login(id) {
    var div = document.getElementById(id);
    var content = $(div).html();
    if (div.style.height == 'auto') {
        div.style.height = '40px';
        var c = content.substr(0, content.length - 5);
        var html = c + " ...";
        $(div).html(html);
    } else {
        div.style.height = 'auto';
    }
}
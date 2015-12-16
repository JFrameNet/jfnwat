/**
 * Created by jfn on 12/8/15.
 */

function showMes() {
    alert("Hello World!");
}

function toggle_login(id) {
    var div = document.getElementById(id);
    if (div.style.height == 'auto') {
        div.style.height = '40px';
    } else {
        div.style.height = 'auto';
    }
    //var content = $(div).html();
    //var c = content.substr(0, content.length - 5);
    //var html = c + " ...";
    //$(this).html(html);
}
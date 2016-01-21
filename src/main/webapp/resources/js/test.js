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

function toggle(rowIndex) {
    var div = document.getElementById('raw'+rowIndex);
    var other = document.getElementById('annotated'+rowIndex);
    if (div.style.display !== 'none') {
        div.style.display = 'none';
        other.style.display = 'block';
    }
    else {
        div.style.display = 'block';
        other.style.display = 'none';
    }
}

function displayDef(tag,row) {
    var div = document.getElementById('def'+row);
    div.innerHTML = tag;
    if (div.style.display == 'none'){
        div.style.display = 'block';
    } else {
        div.style.display = 'none';
    }
}

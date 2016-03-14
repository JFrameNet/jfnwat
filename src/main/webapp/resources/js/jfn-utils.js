/**
 * Created by jfn on 12/8/15.
 */

function displayAll (id)  {
    var div = document.getElementById('core'+id);
    var other = document.getElementById('non-core'+id);
    other.style.display = 'block';
    div.style.display = 'block';
}

function displayCore (id) {
    var div = document.getElementById('core'+id);
    var other = document.getElementById('non-core'+id);
    other.style.display = 'none';
    div.style.display = 'block';
}

function displayNonCore(id) {
    var div = document.getElementById('core'+id);
    var other = document.getElementById('non-core'+id);
    other.style.display = 'block';
    div.style.display = 'none';
}

function hideFilter(div){
    div.display = 'none';
}

window.onresize = setHeight;
window.onload = setHeight;

function setHeight(event) {
    var out = document.getElementById('form2');
    var a = (out == null)?0:out.clientHeight;
    var h = window.innerHeight;
    var list1 = document.getElementById('frameList');
    if (list1 != null) {
        var d = document.getElementById('form2:wind');
        var b = (d == null)?0: d.clientHeight;
        $("#frameList > div").css("height", Math.max(h,a) + b);
        return;
    }
    var list2 = document.getElementById('list');
    if (list2 != null) {
        var d = document.getElementById('form2:wind');
        var b = (d == null)?0: d.clientHeight;
        $("#list > div").css("height", Math.max(h,a) + b);
        return;
    }
    var list3 = document.getElementById('docList');
    if (list3 != null) {
        var d = document.getElementById('form2:wind');
        var b = (d == null)?0: d.clientHeight;
        $("#docList > div").css("height", Math.max(h,a) + b);
        return
    }
}

function heightFrameList() {
    var out = document.getElementById('form2');
    var a = (out == null)?0:out.clientHeight;
    var h = window.innerHeight;
    var list = document.getElementById('frameList');
    if (list != null) {
        var d = document.getElementById('form2:wind');
        var b = (d == null)?0: d.clientHeight;
        $("#frameList > div").css("height", Math.max(h,a) + b);
    }
}

function heightLUList() {
    var out = document.getElementById('form2');
    var a = (out == null)?0:out.clientHeight;
    var h = window.innerHeight;
    var list = document.getElementById('list');
    if (list != null) {
        var d = document.getElementById('form2:wind');
        var b = (d == null)?0: d.clientHeight;
        $("#list > div").css("height", Math.max(h,a) + b);
    }
}

function heightDocList() {
    var out = document.getElementById('form2');
    var a = (out == null)?0:out.clientHeight;
    var h = window.innerHeight;
    var list = document.getElementById('docList');
    if (list != null) {
        var d = document.getElementById('form2:wind');
        var b = (d == null)?0: d.clientHeight;
        $("#docList > div").css("height", Math.max(h,a) + b);
    }
}

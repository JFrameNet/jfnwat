/**
 * Created by jfn on 12/8/15.
 */

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

function toggleGroup(groupIndex, rowIndex) {
    var div = document.getElementById('raw'+groupIndex+rowIndex);
    var other = document.getElementById('annotated'+groupIndex+rowIndex);
    if (div.style.display !== 'none') {
        div.style.display = 'none';
        other.style.display = 'block';
    }
    else {
        div.style.display = 'block';
        other.style.display = 'none';
    }
}

function hideMe(row) {
    var div = document.getElementById('def'+row);
    div.style.display = 'none';
}

function displayAll (id)  {
    var div = document.getElementById('core'+id);
    var other = document.getElementById('non-core'+id);
    other.style.display = 'block';
    div.style.display = 'block';
}

function displayCore (id) {
    var div = document.getElementById('core'+id);
    div.style.display = 'block';
}

function displayNonCore(id) {
    var other = document.getElementById('non-core'+id);
    other.style.display = 'block';
}



window.addEventListener('load', mExternalJsLoadFunc, false);
function mExternalJsLoadFunc()
{
    var a = $(window).width();
    var div = document.getElementById('form2:form3:w1');
    var other = document.getElementById('form2:form3:w2');
    if (div != null) {
        div.value=a;
    }
    if (other != null) {
        other.value=a;
    }
}

//var addEvent = function(object, type, callback) {
//    if (object == null || typeof(object) == 'undefined') return;
//    if (object.addEventListener) {
//        object.addEventListener(type, callback, false);
//    } else if (object.attachEvent) {
//        object.attachEvent("on" + type, callback);
//    } else {
//        object["on"+type] = callback;
//    }
//};

//addEvent(window, "resize", function(event) {
//    var a = $(window).width();
//    var div = document.getElementById('form2:form3:w1');
//    var other = document.getElementById('form2:form3:w2');
//    if (div != null) {
//        div.value=a;
//    }
//    if (other != null) {
//        other.value=a;
//    }
//    return true;
//});




function hideFilter(div){
    div.display = 'none';
}

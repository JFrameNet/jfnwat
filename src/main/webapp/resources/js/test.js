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



window.addEventListener('load', mExternalJsLoadFunc, false);
function mExternalJsLoadFunc()
{
    var a = $(window).width();
    var div = document.getElementById('form2:form3:w1');
    if (div != null) {
        div.value=(a*0.65);
    }
}

function resizeDialog(index)
{
    var div = document.getElementById('form2:form3:w1');
    for (i=0; i<index; i++) {
        var a = document.getElementById('form2:form3:tab-lu:'+i+':wind').clientWidth;
        if ((a!= 0) && (div != null)) {
            div.value=a;
            return;
        }
    }
}


function hideFilter(div){
    div.display = 'none';
}

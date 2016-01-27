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

function displayAnnotation (sentence)  {
    var div = document.getElementById('at'+sentence);
    var other = document.getElementById('ab'+sentence);
    if (div.style.display !== 'none') {
        div.style.display = 'none';
        other.style.display = 'block';
    }
    else {
        div.style.display = 'block';
        other.style.display = 'none';
    }
}


$(document).click(function(e){

    if (e.target.id.contains('modal')) {
        PF('dlg').hide();
    }
});

function hideFilter(div){
    div.display = 'none';
}

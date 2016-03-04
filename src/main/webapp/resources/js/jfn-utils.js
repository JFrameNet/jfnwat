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

function reloadPage() {
    location.reload();
}

function toggleDivVisibility(id) {
    var p = document.getElementById(id);
    if (p.style.display === 'none') {
        p.style.display = 'block';
    }
    else {
        p.style.display = 'none';
    }
}

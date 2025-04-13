document.addEventListener('DOMContentLoaded', async e => {
    let turnos = await cargarTurnos();

    for(i = 0; i <= turnos.data.length - 1; i++) {
        var select = document.getElementById('tabla');
        var opt = document.createElement('tr');
        opt.innerHTML = "<tr><td>" + turnos.data[i].id + "</td>" 
        + "<td>" + turnos.data[i].fecha + "</td>"
        + "<td>" + turnos.data[i].inicio + " - " + turnos.data[i].fin + "</td>"
        + "<td>" + turnos.data[i].cliente.nombre + " " + turnos.data[i].cliente.apellido + "</td>"
        + "<td>" + turnos.data[i].mascota.nombre + "</td>"
        + "<td><select><option>" + turnos.data[i].veterinario.nombre + " " + turnos.data[i].veterinario.apellido + "</option></select><br></td></tr>";
        select.appendChild(opt);
    }
})

async function cargarTurnos() {
    const rawResponse = await fetch('/api/turno', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}
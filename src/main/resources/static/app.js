document.addEventListener('DOMContentLoaded', async e => {
    const content = await cargarTurnos();
    const content2 = await cargarLocal();

    //Crear fecha de mañana
    var fecha = new Date();
    fecha.setDate(fecha.getDate() + 1);

    //Colocar fechas disponibles para turnos
    for (let i = 0; i < content2.data[0].diasDisponibles; i++) {
        var select = document.getElementById('fechas');
        var opt = document.createElement('option');
        opt.innerHTML = fecha.toLocaleDateString();
        opt.setAttribute("value", i + 1);
        select.appendChild(opt);
        fecha.setDate(fecha.getDate() + 1);
    }

    let popup = document.getElementById("popup");
    let open = document.getElementById("button2");
    let close = document.getElementById("close");
    open.addEventListener("click", ()=>{
        popup.showModal();
    })
    close.addEventListener("click", ()=>{
        popup.close();
    })
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

async function cargarLocal() {
    const rawResponse = await fetch('/api/local', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}

//Escuchar cuando se seleccione fecha
document.getElementById("fechas").onchange = onChangeHandler;
async function onChangeHandler() {
    //Limpiar dropdown de horarios
    var node = document.getElementById('horas');
    node.innerHTML = '';
    var block = document.createElement('option');
    block.innerHTML = "hora";
    node.appendChild(block);

    //Obtener fecha seleccionada
    var dias = document.getElementById('fechas').value;
    var fecha = new Date();
    fecha.setDate(fecha.getDate() + (1 * dias));
    //console.log(fecha);

    //Fetcha turnos y configuración del local
    const content = await cargarTurnos();
    const content2 = await cargarLocal();

    //Crear Date con hora a la que cierra
    let cierre = new Date(fecha);
    cierre.setHours((content2.data[0].cierre).substring(0, 2));
    cierre.setMinutes((content2.data[0].cierre).substring(3, 5));
    cierre.setSeconds((content2.data[0].cierre).substring(6, 8));

    //Crear Date con hora del primer bloque horario
    let inicioBloque = new Date(fecha);
    inicioBloque.setHours((content2.data[0].apertura).substring(0, 2));
    inicioBloque.setMinutes((content2.data[0].apertura).substring(3, 5));
    inicioBloque.setSeconds((content2.data[0].apertura).substring(6, 8));
    if (cierre.getHours() < inicioBloque.getHours()) {
        cierre.setDate(cierre.getDate() + 1);
    }

    //Crear Date con duración de los bloques horarios
    let tamaBloque = new Date(fecha);
    tamaBloque.setHours((content2.data[0].tamBloque).substring(0, 2));
    tamaBloque.setMinutes((content2.data[0].tamBloque).substring(3, 5));
    tamaBloque.setSeconds((content2.data[0].tamBloque).substring(6, 8));

    //Crear Date con hora a la que termina el primer bloque horario
    let finBloque = new Date(fecha);
    finBloque.setHours(inicioBloque.getHours() + tamaBloque.getHours());
    finBloque.setMinutes(inicioBloque.getMinutes() + tamaBloque.getMinutes());
    finBloque.setSeconds(inicioBloque.getSeconds() + tamaBloque.getSeconds());

    //Colocar todos los horarios
    while (finBloque.getTime() <= cierre.getTime()) {
        let written = false;
        //Comparar turnos ya pedidos con horarios disponibles
        for (let i = 0; i <= content.data.length - 1; i++) {
            let turno = new Date(fecha);
            turno.setFullYear((content.data[i].fecha).substring(0, 4), ((content.data[i].fecha).substring(5, 7))-1, (content.data[i].fecha).substring(8, 10));
            turno.setHours((content.data[i].inicio).substring(0, 2));
            turno.setMinutes((content.data[i].inicio).substring(3, 5));
            turno.setSeconds((content.data[i].inicio).substring(6, 8));
            //Colocar en gris los turnos ya pedidos
            if (inicioBloque.getTime() == turno.getTime()) {
                var opt = document.createElement('option');
                opt.innerHTML = inicioBloque.getHours() + ":" + inicioBloque.getMinutes() + " - " + finBloque.getHours() + ":" + finBloque.getMinutes();
                opt.setAttribute("disabled", true);
                
                i = content.data.length;
            //Colocar el resto de horarios
            } else if(written == false){
                var opt = document.createElement('option');
                opt.innerHTML = inicioBloque.getHours() + ":" + inicioBloque.getMinutes() + " - " + finBloque.getHours() + ":" + finBloque.getMinutes();
                
                written = true;
            } else {}
        }

        written = false;
        node.appendChild(opt);
        //Crear el siguiente horario
        inicioBloque.setHours(finBloque.getHours());
        inicioBloque.setMinutes(finBloque.getMinutes());
        inicioBloque.setSeconds(finBloque.getSeconds());
        finBloque.setHours(inicioBloque.getHours() + tamaBloque.getHours());
        finBloque.setMinutes(inicioBloque.getMinutes() + tamaBloque.getMinutes());
        finBloque.setSeconds(inicioBloque.getSeconds() + tamaBloque.getSeconds());

        //Crear el último bloque que puede ser más pequeño de lo normal
        if (finBloque.getTime() > cierre.getTime()) {
            for (let i = 0; i <= content.data.length - 1; i++) {
                let turno = new Date(fecha);
                turno.setHours((content.data[i].inicio).substring(0, 2));
                turno.setMinutes((content.data[i].inicio).substring(3, 5));
                turno.setSeconds((content.data[i].inicio).substring(6, 8));
                if (inicioBloque.getTime() == turno.getTime()) {
                    var opt = document.createElement('option');
                    opt.innerHTML = inicioBloque.getHours() + ":" + inicioBloque.getMinutes() + " - " + cierre.getHours() + ":" + cierre.getMinutes();
                    opt.setAttribute("disabled", true);
                    node.appendChild(opt);
                    i = content.data.length;
                } else {
                    var opt = document.createElement('option');
                    opt.innerHTML = inicioBloque.getHours() + ":" + inicioBloque.getMinutes() + " - " + cierre.getHours() + ":" + cierre.getMinutes();
                    node.appendChild(opt);
                    i = content.data.length;
                }
            }
        }
    }
}

async function crearTurno() {}

async function consultaTurno() {
    let popupText = document.getElementById("popup-text");
    popupText.innerHTML = " ";

    if ((document.getElementById("dni2").value) === "") {
        var datos = document.createElement('h4');
        datos.innerHTML = "<h3 id='error'>¡Error! Debe ingresar su número de DNI</h3>";
        popupText.appendChild(datos);
        return
    }

    const rawResponse = await fetch('/api/cliente/dni/' + document.getElementById("dni2").value, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }).then(rawResponse => rawResponse.json());
    if (rawResponse.data === null) {
        var datos = document.createElement('h4');
        datos.innerHTML = "<h3 id='error'>¡Error! No se encontraron turnos para esta persona</h3>";
        popupText.appendChild(datos);
        return
    }

    const turno = await cargarTurnoPorCliente(rawResponse.data.id);
    
    let primeraLinea = 0;
    let fecha = new Date();
    for(let i = 0; i <= turno.data.length - 1; i++) {
        var datos = document.createElement('h4');
        datos.innerHTML = "<h3 id='error'>¡Error! No se encontraron turnos para esta persona</h3>";
        
        let fechaTurno = new Date();
        fechaTurno.setFullYear((turno.data[i].fecha).substring(0, 4));
        fechaTurno.setMonth((turno.data[i].fecha).substring(5, 7) + 1);
        fechaTurno.setDate((turno.data[i].fecha).substring(8, 10));
        fechaTurno.setHours((turno.data[i].inicio).substring(0, 2));
        fechaTurno.setMinutes((turno.data[i].inicio).substring(3, 5));

        if (fechaTurno.getDate() >= fecha.getDate()) {
            if (primeraLinea == 0) {
                datos.innerHTML = " ";
                primeraLinea = primeraLinea + 1;
            }

            datos.innerHTML = "<div id='popup-text2'> Código de turno: " + turno.data[i].id
            + "<br>" + "Cliente: " + rawResponse.data.nombre + " " + rawResponse.data.apellido + " con DNI " + rawResponse.data.dni
            + "<br>" + "Mascota: " + turno.data[i].mascota.nombre
            + "<br>" + "Será atendido por especialista " + turno.data[i].veterinario.nombre + " " + turno.data[i].veterinario.apellido
            + "<br>" + "Día de la cita: " + turno.data[i].fecha + ", de " + turno.data[i].inicio + " hasta las " + turno.data[i].fin + "</div>";
            popupText.appendChild(datos);
        }
        
    }
    
}

async function cargarTurnoPorCliente(id) {
    const rawResponse = await fetch('/api/turno/cliente/' + id, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}

async function cargarClientePorDni(dni) {
    const rawResponse = await fetch('/api/cliente/dni/' + dni, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}

async function actualizarCliente(cliente, id) {
    const rawResponse = await fetch('/api/cliente/' + id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cliente)
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}

async function crearCliente(cliente) {
    const rawResponse = await fetch('/api/cliente', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cliente)
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}

async function crearMascota(mascota) {
    const rawResponse = await fetch('/api/mascota', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(mascota)
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}

async function cargarVeterinario() {
    const rawResponse = await fetch('/api/veterinario', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}

async function crearTurno(turno) {
    const rawResponse = await fetch('/api/turno', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(turno)
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}

async function escribir() {
    let cliente = document.getElementsByName("cliente");
    let mascota = document.getElementsByName("mascota");
    let sexo;
    let esterilizado;
    let fecha = document.querySelector('select[id="fechas"]').value;
    let hora = document.querySelector('select[id="horas"]').value;
    var select = document.getElementById("errorField");
    select.innerHTML = " ";

    //Verificar fecha
    var diaTurno = new Date();
    if (fecha == "fecha") {
        var error = document.createElement('h4');
        error.innerHTML = "<h3 id='error2'>¡Error! Debe escoger una fecha para su turno</h3>";
        select.appendChild(error);
        return;
    } else {
        diaTurno.setDate(diaTurno.getDate() + (1 * fecha));
        diaTurno = diaTurno.toISOString();
        diaTurno = diaTurno.slice(0, 10);
    }

    //Verificar hora y convertir
    let inicioString = "";
    let finString = "";
    if (hora == "hora") {
        var error = document.createElement('h4');
        error.innerHTML = "<h3 id='error2'>¡Error! Debe escoger un bloque horario para su turno</h3>";
        select.appendChild(error);
        return;
    } else {
        for (i = 0; i <= hora.length; i++) {
            if (hora.slice(i, i+1) == "" || hora.slice(i, i+1) == "-") {
            } else {
                inicioString = inicioString + hora.slice(i, i+1);
            }
        }
        if (inicioString.substring(1, 2) == ":") {
            inicioString = 0 + inicioString;
        }
        if (inicioString.substring(4, 5) == " ") {
            inicioString = inicioString.substring(0, 4) + 0 + inicioString.substring(4, 11);
        }
        if (inicioString.substring(8, 9) == ":") {
            inicioString = inicioString.substring(0, 7) + 0 + inicioString.substring(7, 12);
        }
        if (inicioString.length == 11) {
            inicioString = inicioString + 0;
        }
        finString = inicioString.slice(7, 12) + ":00";
        inicioString = inicioString.slice(0, 5) + ":00";
    }

    //Verificar que estén todos los datos de usuario
    for(i = 0; i <= cliente.length - 1; i++) {
        if (cliente[i].value == "" && i != 3) {
            var error = document.createElement('h4');
            switch(i) {
                case 0:
                    error.innerHTML = "<h3 id='error2'>¡Error! Debe ingresar su nombre</h3>";
                    select.appendChild(error);
                    return;
                case 1:
                    error.innerHTML = "<h3 id='error2'>¡Error! Debe ingresar su apellido</h3>";
                    select.appendChild(error);
                    return;
                case 2:
                    error.innerHTML = "<h3 id='error2'>¡Error! Debe ingresar su DNI</h3>";
                    select.appendChild(error);
                    return;
                case 4:
                    error.innerHTML = "<h3 id='error2'>¡Error! Debe ingresar su teléfono</h3>";
                    select.appendChild(error);
                    return;
                case 5:
                    error.innerHTML = "<h3 id='error2'>¡Error! Debe ingresar su dirección</h3>";
                    select.appendChild(error);
                    return;
            }
        }
    }

    //Verificar que estén todos los datos de mascota
    for(i = 0; i <= 1; i++) {
        if (mascota[i].value == "") {
            var error = document.createElement('h4');
            switch(i) {
                case 0:
                    error.innerHTML = "<h3 id='error2'>¡Error! Debe ingresar la especie de la mascota</h3>";
                    select.appendChild(error);
                    return;
                case 1:
                    error.innerHTML = "<h3 id='error2'>¡Error! Debe ingresar el nombre de la mascota</h3>";
                    select.appendChild(error);
                    return;
            }
        }
    }
    //Verificar sexo
    try {sexo = document.querySelector('input[name="mascota2"]:checked').value;} 
    catch(err) {
        var error = document.createElement('h4');
        error.innerHTML = "<h3 id='error2'>¡Error! Debe indicar el sexo de la mascota</h3>";
        select.appendChild(error);
        return;
    }
    //Verificar castrado
    try {esterilizado = document.querySelector('input[name="mascota3"]:checked').value;} 
    catch(err) {
        var error = document.createElement('h4');
        error.innerHTML = "<h3 id='error2'>¡Error! Debe indicar si la mascota está castrada</h3>";
        select.appendChild(error);
        return;
    }

    //Cargar veterinarios, preparar datos
    let veterinario = await cargarVeterinario();
    let inicioTurno = new Date(diaTurno);
    let inicioDate = new Date(diaTurno);
    inicioDate.setHours(inicioString.substring(0, 2));
    inicioDate.setMinutes(inicioString.substring(3, 5));

    //Escoger veterinario
    let vetEscogido = 0;
    for(let i = 0; i <= veterinario.data.length - 1; i++) {
        inicioTurno.setHours((veterinario.data[i].inicioTurno).substring(0, 2));
        inicioTurno.setMinutes((veterinario.data[i].inicioTurno).substring(3, 5));
        if (inicioTurno.getTime() <= inicioDate.getTime()) {
            vetEscogido = veterinario.data[i].id;
        }
    }
    //Caso especial para después de las 12
    for(let i = (veterinario.data.length - 1); i >= 0; i--) {
        if (vetEscogido == 0){
            inicioDate.setDate(inicioDate.getDate() + 1);
            if (inicioTurno.getTime() <= inicioDate.getTime()) {
                vetEscogido = veterinario.data[i].id;
            }
        }
    }

    //Convertir datos
    var newCliente = {nombre: cliente[0].value, apellido: cliente[1].value, dni: cliente[2].value, mail: cliente[3].value, telefono: cliente[4].value, direccion: cliente[5].value}
    var newMascota = {animal: mascota[0].value, nombre: mascota[1].value, edad: parseInt(mascota[2].value), sexo: parseInt(sexo), esterilizado: parseInt(esterilizado)}

    let clienteCargado = await cargarClientePorDni(cliente[2].value);
    let idCargada = 0;

    try {
        if (cliente[2].value == clienteCargado.data.dni) {
            await actualizarCliente(newCliente, clienteCargado.data.id);
            idCargada = clienteCargado.data.id;
        }
    } catch(err) {
        await crearCliente(newCliente);
        let clienteCreado = await cargarClientePorDni(cliente[2].value);
        idCargada = clienteCreado.data.id;
    }

    //await crearMascota(newMascota);
    var newTurno = {fecha: diaTurno, inicio: inicioString, fin: finString, status: "Necesita confirmación", mascota: newMascota, veterinario: {id: vetEscogido}, cliente: {id: idCargada}}
    let sendTurno = await crearTurno(newTurno);

    let turno = await cargarTurnoPorId(sendTurno.data.id);
    var datos = document.createElement('h4');
    datos.innerHTML = "<div id='popup-text2'> Código de turno: " + turno.data.id
        + "<br>" + "Cliente: " + rawResponse.data.nombre + " " + rawResponse.data.apellido + " con DNI " + rawResponse.data.dni
        + "<br>" + "Mascota: " + turno.data.mascota.nombre
        + "<br>" + "Será atendido por especialista " + turno.data.veterinario.nombre + " " + turno.data.veterinario.apellido
        + "<br>" + "Día de la cita: " + turno.data.fecha + ", de " + turno.data.inicio + " hasta las " + turno.data.fin + "</div>";
    popupText.appendChild(datos);
    popup.showModal()
    //Y es vencer, y es vencer... ~𝅘𝅥𝅮 (Probar esto), agregar confirmación a la consulta
}

//Pendiente: Validación de email y num telefono???

async function cargarTurnoPorId(id) {
    const rawResponse = await fetch('/api/turno/' + id, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    }).then(rawResponse => rawResponse.json());
    return rawResponse;
}
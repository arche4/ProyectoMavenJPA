/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var tabla ;
$body = $("body");

$(document).ready(function(){
    var alt = $(document).height();
    $('.modal-dialog').css('top',alt*0.3);
    $(window).resize(function(){
        var alt2 = $(document).height();
        $('.modal-dialog').css('top',alt2*0.3);
    });
    
    //DECLARACION DE LAS VARIABLES A UTILIZAR
    var pais;
    var ivr;
    var fecha_i;
    var fecha_f;
    var tabla;
    var tCons;
    var Jdata;
    var cont = 0;
    $("#botDesc").hide();
    
    //TRAE EL LISTADO DE PAISES    
    $.ajax({
        type: "GET",
        url: "sPaises",
        success: function (data) {
            var jsonResp = JSON.parse(data);
            if (jsonResp.error !== null && jsonResp.error !== undefined) {
                $('#modInf').html('<p>Se ha presentado un error en la busqueda '
                    + ' de los paises. Por favor contacte a un administrador.</p>');
                $('#modalInf').modal('show');
            } else {
                var listPaises = jsonResp.listPaises;
                var strPaises = JSON.stringify(listPaises);
                var jsonPaises = JSON.parse(strPaises.substring(1, strPaises.length - 1));
                var listItems = "<option value='" + 9999 + "'>--Seleccione un Pais--</option>";
                for (var i = 0; i < Object.keys(jsonPaises).length; i++) {
                    listItems += "<option value='" + Object.keys(jsonPaises)[i] + "'>"
                    + Object.values(jsonPaises)[i] + "</option>";
                }
            }
            $("#selectPais").html(listItems);
            $("#selectPais").prop('disabled', false);
        }
    });
    
    
    //PARA ACTUALIZAR LOS IVR DISPONIBLES POR PAIS
    
    $('#selectPais').change(function(){
        pais = $(this).val();
        if($(this).val() === '9999' || $(this).val() == ''){
            $('#selectIVR').html('--Seleccione un Pais--');
            $('#selectIVR').prop('disabled','disabled');
        }else{            
            $.ajax({
                type: "GET",
                url: "sCliente",
                data: 'pais='+pais,
                success: function (data) {
                    var jsonResp = JSON.parse(data);
                    if (jsonResp.error !== null && jsonResp.error !== undefined) {
                        $('#modInf').html('<p>Se ha presentado un error en la busqueda '
                            + ' de los clientes. Por favor contacte a un administrador.</p>');
                        $('#modalInf').modal('show');
                    }else {
                        var listIVR = jsonResp.listIVR;
                        var strIVR = JSON.stringify(listIVR);
                        var jsonIVR = JSON.parse(strIVR.substring(1, strIVR.length - 1));
                        var listItems = "<option value='" + 9999 + "'>--Seleccione un cliente--</option>";
                        for (var i = 0; i < Object.keys(jsonIVR).length; i++) {
                            listItems += "<option value='" + Object.keys(jsonIVR)[i] + "'>"
                            + Object.values(jsonIVR)[i] + "</option>";
                        }
                    }
                    $("#selectIVR").html(listItems);
                    $("#selectIVR").prop('disabled', false);
                }
            });      
        }
    });
    
    
    
    //PARA ACTUALIZAR LOS TIPOS DE CONSULTA SEGUN CADA CLIENTE
    
    $('#selectIVR').change(function(){
        ivr = $('#selectIVR option:selected').text();        
        if($(this).val() === '9999' || $(this).val() == ''){
            $('#selectConsulta').html('--Seleccione una Consulta--');
            $('#selectConsulta').prop('disabled','disabled');
        }else{
            $.ajax({
                type: "GET",
                url: "sTConsulta",
                data: 'ivr='+ivr,
                success: function (data) {
                    var jsonResp = JSON.parse(data);
                    if (jsonResp.error !== null && jsonResp.error !== undefined) {
                        $('#modInf').html('<p>Se ha presentado un error en la busqueda '
                            + ' de las consultas disponibles para el cliente. Por favor contacte a un administrador.</p>');
                        $('#modalInf').modal('show');
                    }else {
                        var listConsulta = jsonResp.listConsulta;
                        var strConsulta = JSON.stringify(listConsulta);
                        var jsonConsulta = JSON.parse(strConsulta.substring(1, strConsulta.length - 1));
                        var listItems = "<option value='" + 9999 + "'>--Seleccione una consulta--</option>";
                        for (var i = 0; i < Object.keys(jsonConsulta).length; i++) {
                            listItems += "<option value='" + Object.keys(jsonConsulta)[i] + "'>"
                            + Object.values(jsonConsulta)[i] + "</option>";
                        }
                    }
                    $("#selectConsulta").html(listItems);
                    $("#selectConsulta").prop('disabled', false);
                }
            });      
        }
    });        
    
    
    $('#selectConsulta').change(function(){
        var tCons = $('#selectConsulta option:selected').text();
        
        if(tCons == "Estadistica"){
            $("#rangTime").prop('disabled', 'disabled');            
        }
        else{
            $("#rangTime").prop('disabled', false);  
        }
        
        
    });
    
    //EL BOTON CONSULTAR EJECUTA  
    $('#botCons').click(function(event){        
        event.stopPropagation();
        event.preventDefault();
        var pais = $('#selectPais').val();
        var ivr = $('#selectIVR option:selected').text();
        var tCons = $('#selectConsulta option:selected').text();
        
        // COMO SE MANEJAN DISTINTOS FORMATOS DE FECHA, SE DEBEN TOMAR SEGUN LA CONSULTA
        var fecha_completa = ($('#rangTime').val()).split(' - ');
        
        //La fecha tiene el formato YYYY-MM-DD HH24:MI
        var fecha_i = fecha_completa[0];
        var fecha_f = fecha_completa[1];
        
        console.log("Consulta: " + tCons);
        
        //Acá armo el nombre del archivo que se va a descargar:
        var nombre;
        if(tCons === "Consulta general"){
            nombre = fecha_i.replace(":", '') + "_" + fecha_f.replace(":", '') + "_ConsultaGeneral.xls";        
            
        }
        else if(tCons === "Sabana de datos"){
            nombre = fecha_i.replace(":", '') + "_" + fecha_f.replace(":", '') + "_SabanaDatos.xls";        
            
        }
        else if(tCons === "Estadistica"){
            nombre = "Estadistica.xls"
        }
        else if (tCons === "Informe DPO AGBAR"){
            nombre = fecha_i.replace(":", '') + "_" + fecha_f.replace(":", '') + "_InformeDPO_AGBAR.xls";
        }
        else if (tCons === "Informe CH Santander"){
            nombre = fecha_i.replace(":", '') + "_" + fecha_f.replace(":", '') + "_Informe_CH_Santander.xls";
        }
        else if (tCons === "Informe Mutua"){
            nombre = fecha_i.replace(":", '') + "_" + fecha_f.replace(":", '') + "_Informe_Mutua.xls";
        }
        else{
            nombre = fecha_i.replace(":", '') + "_" + fecha_f.replace(":", '') + "_InformeSuez.xls";        
            
        }
        
        
        //CREAR UNA NUEVA TABLA CADA VEZ QUE EL CONTADOR AUMENTE
        if (cont !== 0){
            $('#miPutaTabla').children().remove();
            $( "#miPutaTabla" ).append( "<table id=\"tabDat" + cont + "\" " + "class=\"table table-bordered table-hover\"></table>" );            
        }  
        
        //Esto es para setear las cabeceras de las columnas según el tipo de consulta
        var columns;        
        if(tCons === "Consulta general"){
            columns = [{
                title: "SESSIONID"
            },{
                title: "ORIGEN"
            },{
                title: "ORIGEN IDENTIFICADO"
            },{
                title: "CANTIDAD LLAMADAS"
            },{
                title: "UTILIZA"
            },{
                title: "EXITO"
            },{
                title: "OPT_AGRUPADO"
            },{
                title: "FLUJO RELLAMADA"
            },{
                title: "DIA"
            },{
                title: "HORA"
            },{
                title: "NUMERACION"
            },{
                title: "SERVICIO"
            },{
                title: "OPT"
            },{
                title: "DURACION LLAMADA"
            },{
                title: "DURACION IVR"
            },{
                title: "DURACION CONV"
            },{
                title: "ESTADO"
            },{
                title: "NODO NUM CONTRATO"
            },{
                title: "DATOS NO CORRECTOS"
            },{
                title: "NUMERO DE CONTRATO 2"
            },{
                title: "DATOS NO CORRECTOS 2"
            },{
                title: "LECTURA NO CORRECTA"
            },{
                title: "ULTIMO NODO NAVEGACION"
            },{
                title: "CONTRATO ENVIADO"
            },{
                title: "RESPUESTA WS_C"
            },{
                title: "LECTURA ENVIADA"
            },{
                title: "RESPUESTA WS_L"
            },{
                title: "DESCRIPTIVO"
            }];
        }               
        else if(tCons === "Sabana de datos"){
            columns = [{
                title: "HORA DE INICIO"
            },{
                title: "NIVEL"
            },{
                title: "EXPERIENCE PORTAL"
            },{
                title: "ZONA"
            },{
                title: "ID DE SESION"
            },{
                title: "UCID DE DE SESION"
            },{
                title: "SERVIDOR DE APLICACION"
            },{
                title: "APLICACION"
            },{
                title: "ID DE NODO"
            },{
                title: "ACTIVIDAD"
            },{
                title: "TIPO"
            },{
                title: "MENSAJE"
            },{
                title: "NOMBRE VARIABLE"
            },{
                title: "VALOR VARIABLE"
            }];            
        }
        else if(tCons === "Estadistica"){
            columns = [{
                title: "MES"
            },{
                title: "SEMANA"
            },{
                title: "TOTAL_LLAMADAS"
            },{
                title: "OPC_LECT_CONT"
            },{
                title: "% LECT_CONT"
            },{
                title: "OTRAS_OPC"
            },{
                title: "% OTRAS_OPC"
            },{
                title: "COLUMNA_CONTROL_1"
            },{
                title: "NO_IDENT_CONT"
            },{
                title: "% NO IDENT"
            },{
                title: "LECT_OK"
            },{
                title: "% LECT_OK"
            },{
                title: "LECT_OK_FR"
            },{
                title: "% LECT_OK_FR"
            },{
                title: "LECT_KO"
            },{
                title: "% LECT_KO"
            },{
                title: "LECT_M_AGENT"
            },{
                title: "% LECT_M_AGENT"
            },{
                title: "COLUMNA_CONTROL_2"
            },{
                title: "NO_INDENT_CONT"
            },{
                title: "% NO IDENT CONT"
            },{
                title: "CONT_OK"
            },{
                title: "% CONT_OK"
            },{
                title: "CONT_NE"
            },{
                title: "% CONT_NE"
            },{
                title: "COLUMNA_CONTROL_3"
            }];  
        }
        else if(tCons === "Informe DPO AGBAR") {
            columns = [{
                title: "IDENTIFICADOR"
            },{
                title: "ORIGEN"
            },{
                title: "DIA"
            },{
                title: "HORA"
            },{
                title: "ID GRABACION"
            },{
                title: "SERVICIO"
            },{
                title: "DURACION IVR"
            },{
                title: "DURACION GRABACION"
            },{
                title: "DURACION LLAMADA"
            },{
                title: "OPT MENU"
            },{
                title: "EXITO"
            }];
        }else if (tCons === "Informe CH Santander"){
            columns = [{
                title: "IDENTIFICATIVO"
            },{
                title: "FECHA"
            },{
                title: "HORA"
            },{
                title: "ORIGEN"
            },{
                title: "DURACION"
            },{
                title: "OFICINA_1"
            },{
                title: "OFICINA_2"
            },{
                title: "OFICINA_3"
            },{
                title: "CODIGO_ERROR"
            },{
                title: "TERRITORIAL"
            },{
                title: "OPC MENU_1"
            },{
                title: "OPC MENU_2"
            },{
                title: "ESTADO"
            },{
                title: "VDN_DESTINO"
            }];
        }else if (tCons === "Informe Mutua"){
            columns = [{
                title: "IDENTIFICATIVO"
            },{
                title: "FECHA"
            },{
                title: "HORA"
            },{
                title: "ORIGEN"
            },{
                title: "DURACION"
            },{
                title: "OPC MENU_1"
            },{
                title: "ESTADO"
            },{
                title: "VDN_DESTINO"
            }];
        }else if (tCons === "Informe Mutua Captacion"){
            columns = [{
                title: "IDENTIFICATIVO"
            },{
                title: "FECHA"
            },{
                title: "HORA"
            },{
                title: "ORIGEN"
            },{
                title: "DURACION"
            },{
                title: "OPC MENU_1"
            },{
                title: "OPC MENU_2"
            },{
                title: "ESTADO"
            },{
                title: "VDN_DESTINO"
            }];
        }else {
            columns = [{
                title: "IDENTIFICADOR"
            },{
                title: "ORIGEN"
            },{
                title: "DIA"
            },{
                title: "HORA"
            },{
                title: "ID GRABACION"
            },{
                title: "SERVICIO"
            },{
                title: "DURACION IVR"
            },{
                title: "DURACION GRABACION"
            },{
                title: "DURACION LLAMADA"
            },{
                title: "OPT MENU"
            },{
                title: "EXITO"
            }];
        }           
        
        //Verificamos que los campos esten debidamente diligenciados para hacer la consulta           
        if(tCons === '9999'){
            $('#modInf').html('<p>Seleccione el tipo de consulta</p>');
            $('#modalInf').modal('show');            
        } 
        else if(pais === '9999'){
            $('#modInf').html('<p>Seleccione el pa&iacute;s del IVR</p>');
            $('#modalInf').modal('show');           
        }
        else if(ivr === '9999'){
            $('#modInf').html('<p>Seleccione un IVR</p>');
            $('#modalInf').modal('show');            
        }
        
        //Hacemos la consulta
        else{            
            $.when($.ajax({
                type: "GET",
                url:    "sConsultas",
                data: 'tCons=' + tCons + '&pais=' + pais + '&ivr=' + ivr + '&fecha_i=' + fecha_i + '&fecha_f=' + fecha_f,                
                success: function(data){
                    var jsonResp = JSON.parse(data);                    
                    if (jsonResp.error !== null && jsonResp.error !== undefined) {
                        $('#modInf').html('<p>Se ha presentado un error en la busqueda '
                            + ' de la información. Por favor contacte a un administrador.</p>');
                        $('#modalInf').modal('show');
                    }else {
                        var Sdata = JSON.stringify(jsonResp.data);
                        Jdata = JSON.parse(Sdata.substring(1,Sdata.length-1));
                    }
                }
            })).done(function(){  
                $('#botDesc').attr("href",nombre);
                $("#botDesc").show();
                tabla =  $('#miPutaTabla').children().DataTable({                    
                    "language": {
                        "lengthMenu":   "Mostrar _MENU_ registros por p&aacute;gina",
                        "zeroRecords":  "No se encontraron datos de IVR",
                        "info":                "Mostrando p&aacute;gina _PAGE_ de _PAGES_",
                        "infoEmpty":      "No hay registros disponibles",
                        "infoFiltered":    "(Filtrados de un total de _MAX_ registros)",
                        "search":           "Buscar registro:",
                        "paginate": {
                            "next":          "Siguiente",
                            "previous":   "Anterior"
                        },
                        "loadingRecords" : "Cargando..."
                    },
                    data:Jdata,                           
                    columns: columns,
                    "scrollX": true,
                    responsive:true,
                    "dom": "<'row'<'col-sm-6'l><'col-sm-6'f>>" +
                "<'row'<'col-sm-12'tr>>" +
                "<'row'<'col-sm-5'i><'col-sm-7'p>>"+
                "<'row'<'col-sm-5 myText'><'col-sm-7'>>"
                });                
            });                                    
        }  
        cont += 1;            
    });   
});



//ANIMACIÓN DE LOADING PARA LAS CONSULTAS
$(document).ajaxStart(function () {
    $body.addClass("loading");
});
$(document).ajaxStop(function () {
    $body.removeClass("loading");
}); 





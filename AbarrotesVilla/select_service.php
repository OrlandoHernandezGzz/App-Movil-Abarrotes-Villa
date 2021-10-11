<?php
    include 'conexion.php';
   
    $sql_query = $conexion->prepare("SELECT * FROM producto");
    //Executa el la consulta.
    $sql_query->execute();

    $resultado = $sql_query->get_result();

    //Agregamos los resultados al vector fila.
    if($fila = $resultado->fetch_assoc()){
        //Hacemos los resultados en forma de json y usamos unescaped_unicode para evitar la visualizacion de
        //Caracteres especiales.
        echo json_encode($fila, JSON_UNESCAPED_UNICODE);
    }

    //-> Se usa para acceder a un miembro de un objeto
    $sql_query->close();
    $conexion->close();

?>
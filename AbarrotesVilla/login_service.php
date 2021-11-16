<?php
    include 'conexion.php';
    $us_usuario = $_POST['usuario'];
    $us_password = $_POST['password'];

    //$us_usuario = "sanjuanita";
    //$us_password = "123456";
    

    $sql_query = $conexion->prepare("SELECT * FROM usuario WHERE us_usuario=? AND us_password=?");
    //Sentencia que brinda los parametros de los signos de interrogación las ss, significa que son string.
    $sql_query->bind_param('ss', $us_usuario, $us_password);
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
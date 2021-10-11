<?php
    //Mandamos a llamar los servicios de conexion de la base de datos.
    include 'conexion.php';

    //Declaramos las variables que nos daran los valores a registrar.
    $us_nombre = $_POST['nombre'];
    $us_apellidos = $_POST['apellidos'];
    $us_telefono = $_POST['telefono'];
    $us_usuario = $_POST['usuario'];
    $us_password = $_POST['password'];
    $us_tipo = $_POST['tipo_usuario'];

    //Prepara la consulta para mandar a insertarla a la base de datos
    $consulta = "INSERT INTO usuario (us_nombre, us_apellidos, us_telefono, us_usuario, 
    us_password, us_tipo) VALUES ('$us_nombre','$us_apellidos','$us_telefono','$us_usuario','$us_password',
    '$us_tipo')";

    if(mysqli_query($conexion, $consulta)){
        echo "Registro Exitoso!";
    } else{
        echo "Error al registrar los datos". mysqli_error($conexion);
    }

    //-> Se usa para acceder a un miembro de un objeto
    $consulta->close();
    $conexion->close();

?>  
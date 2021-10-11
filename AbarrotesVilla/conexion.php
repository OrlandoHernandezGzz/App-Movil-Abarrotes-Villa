<?php
    $hostname = "localhost:3307";
    $database = "abarrotesvilla";
    $user = "root";
    $password = "";

    $conexion = new mysqli($hostname, $user, $password, $database);
    
    //Le estamos diciendo que si conexion tiene error me mande un mensaje, si no siga con el proceso de conexion.
    if($conexion -> connect_errno){
        echo "La conexion de la base de datos esta experimentando problemas";
    }

?>
<%-- 
    Document   : index
    Created on : 22-01-2018, 10:24:12
    Author     : alumnossur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="cl.beans.ServicioBeanLocal"%>
<%@taglib prefix="xx" uri="/WEB-INF/tlds/tagImg.tld" %>

<!DOCTYPE html>

<!--Archivo de ServicioBeanLocal para hacer el Initial Context (Conexion con la BD) -->
<%! private ServicioBeanLocal servicio; %>
<%
    InitialContext ctx = new InitialContext();
    servicio = (ServicioBeanLocal) ctx.lookup("java:global/TiendaMascota/ServicioBean!cl.beans.ServicioBeanLocal");
%>

<html>
    <head>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tienda de Mascota</title>
    </head>
    <body>      
        
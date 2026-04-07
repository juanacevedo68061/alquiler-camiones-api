# Alquiler Camiones API

API REST desarrollada con Java EE 8 (JPA + EJB3 + JAX-RS) para gestionar el alquiler de una flota de camiones destinados al transporte de mercaderías.

## Requisitos previos

### Java 17

```bash
sudo apt install openjdk-17-jdk -y
```

Necesario para compilar el proyecto con Maven.

### Maven

```bash
sudo apt install maven -y
```

Compila el proyecto y genera el `.war`.

### Docker

```bash
sudo apt install docker.io -y
sudo apt install docker-compose -y
```

### Permisos Docker

```bash
sudo usermod -aG docker $USER
newgrp docker
```

Agrega tu usuario al grupo Docker para ejecutar comandos sin `sudo`.

## Instalación y ejecución

### 1. Compilar el proyecto

```bash
mvn clean package
```

### 2. Levantar los contenedores

```bash
docker-compose up
```

> Si querés forzar la reconstrucción de las imágenes podés usar `docker-compose up --build`.

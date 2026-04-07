package com.alquilercamiones.dto;

import java.time.LocalDate;

public class ReservaDTO {
    public String origen;
    public String destino;
    public LocalDate fechaInicio;
    public LocalDate fechaFin;
    public Double volumenCarga;
    public Long clienteId;
    public Long camionId;
}
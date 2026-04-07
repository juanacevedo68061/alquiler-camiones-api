package com.alquilercamiones.rest.resource;

import com.alquilercamiones.entity.Camion;
import com.alquilercamiones.service.CamionService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDate;

@Path("/camiones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CamionResource {

    @Inject
    private CamionService service;

    @GET
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            return Response.ok(service.findById(id)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @GET
    @Path("/disponibles")
    public Response findDisponibles(
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin,
            @QueryParam("volumen") Double volumen) {
        try {
            LocalDate inicio = fechaInicio != null ? LocalDate.parse(fechaInicio) : null;
            LocalDate fin = fechaFin != null ? LocalDate.parse(fechaFin) : null;
            return Response.ok(service.findDisponibles(inicio, fin, volumen)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @POST
    public Response crear(Camion camion, @Context UriInfo uriInfo) {
        try {
            Camion nuevo = service.save(camion);
            URI uri = uriInfo.getAbsolutePathBuilder()
                             .path(String.valueOf(nuevo.getId()))
                             .build();
            return Response.created(uri).entity(nuevo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Camion camion) {
        try {
            return Response.ok(service.update(id, camion)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            service.delete(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }
}
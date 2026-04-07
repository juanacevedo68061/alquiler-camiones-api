package com.alquilercamiones.rest.resource;

import com.alquilercamiones.entity.Reserva;
import com.alquilercamiones.service.ReservaService;
import com.alquilercamiones.dto.ReservaDTO;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaResource {

    @Inject
    private ReservaService service;

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
    @Path("/en-curso")
    public Response findEnCurso() {
        return Response.ok(service.findEnCurso()).build();
    }

    @GET
    @Path("/finalizadas")
    public Response findFinalizadas() {
        return Response.ok(service.findFinalizadas()).build();
    }

    @GET
    @Path("/proximas")
    public Response findProximas() {
        return Response.ok(service.findProximas()).build();
    }

    @POST
    public Response save(ReservaDTO dto, @Context UriInfo uriInfo) {
        try {
            Reserva nueva = service.save(dto);
            URI uri = uriInfo.getAbsolutePathBuilder()
                             .path(String.valueOf(nueva.getId()))
                             .build();
            return Response.created(uri).entity(nueva).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response cancel(@PathParam("id") Long id) {
        try {
            service.delete(id);
            return Response.noContent().build();
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
}
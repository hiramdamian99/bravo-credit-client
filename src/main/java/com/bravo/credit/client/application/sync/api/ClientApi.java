/*
 * D. R. © Hiram Solutions de C.V., Ciudad de México, 2026
 * CONFIDENTIAL Use is subject to license terms.
 *
 * Project: bravo-credit-client
 * File: ClientApi.java
 */
package com.bravo.credit.client.application.sync.api;



import com.bravo.credit.client.domain.model.Client;
import com.bravo.credit.client.domain.model.ClientRequest;
import com.bravo.credit.client.domain.model.ClientResponse;
import com.bravo.credit.client.domain.model.InformationResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/** The interface Product controller. */
@RequestMapping("api/v1")
public interface ClientApi {




    @PostMapping(
            value = "/search/client",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron clientes",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ClientResponse>> getProcess(
            @RequestHeader HttpHeaders header,
            @RequestBody ClientRequest client
    ) throws Exception;


    @PostMapping(
            value = "/created/client",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Recurso relacionado no encontrado (si aplica)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<InformationResponse> createdClient(
            @RequestHeader HttpHeaders header,
            @RequestBody Client client
    ) throws Exception;

}

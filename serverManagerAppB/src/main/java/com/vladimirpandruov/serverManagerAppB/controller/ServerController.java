package com.vladimirpandruov.serverManagerAppB.controller;

import com.vladimirpandruov.serverManagerAppB.domain.Response;
import com.vladimirpandruov.serverManagerAppB.domain.Server;
import com.vladimirpandruov.serverManagerAppB.enumeration.Status;
import com.vladimirpandruov.serverManagerAppB.service.serviceImpl.ServerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/servers")
@RequiredArgsConstructor
public class ServerController {

    private final ServerServiceImpl serverService;

    @GetMapping
    public ResponseEntity<Response> getServers() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("servers", serverService.list(30)))
                .message("Servers retrieved")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = this.serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("server", server))
                .message(server.getStatus() == Status.SERVER_UP ? "Ping sucess" : "Ping failed")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
    @PostMapping
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("server", this.serverService.create(server)))
                .message("Server created")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("server", this.serverService.get(id)))
                .message("Server retrieved")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("deleted", this.serverService.delete(id)))
                .message("Server deleted")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
    @GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Documents/panvlaGit/resources/png/" + fileName));
    }

}

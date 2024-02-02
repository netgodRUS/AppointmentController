package com.example.AppointmentController.security.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.elijah.doctorsappointmentbookingsystem.exception.DataAlreadyExistException;
import com.elijah.doctorsappointmentbookingsystem.exception.DataNotFoundException;
import com.elijah.doctorsappointmentbookingsystem.response.ApiResponse;
import com.elijah.doctorsappointmentbookingsystem.security.model.AppUser;
import com.elijah.doctorsappointmentbookingsystem.security.model.Role;
import com.elijah.doctorsappointmentbookingsystem.security.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/appUser")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/all")
    public List<AppUser> getAllUsers(){
        return appUserService.getAllUsers();
    }

    @PostMapping("/user/create")
    public ResponseEntity<ApiResponse> saveUsers(@RequestBody AppUser appUser) throws DataAlreadyExistException {
        appUserService.saveUser(appUser);
        return new ResponseEntity<>(new ApiResponse(true,"User created Successfully"), HttpStatus.CREATED);
    }

    @PostMapping("/role/save")
    public ResponseEntity<ApiResponse> saveRole(@RequestBody Role role) throws DataAlreadyExistException {
        appUserService.saveRole(role);
        return new ResponseEntity<>(new ApiResponse(true,"Role saved Successfully"), HttpStatus.CREATED);
    }
    @PostMapping("/role/addToUser")
    public ResponseEntity<ApiResponse> addRoleToUsr(@RequestBody RoleToUserForm form) throws DataAlreadyExistException, DataNotFoundException {
        appUserService.addRoleToUser(form.getUserName(),form.getRoleName());
        return new ResponseEntity<>(new ApiResponse(true,"Role Added to the user Successfully"),HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws DataNotFoundException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser appUser = appUserService.getUser(username);

                String access_token = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10 *60 *1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",appUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch (Exception exception){
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        }else {
            throw   new DataNotFoundException("Refresh Token is Missing");
        }
    }


}
@Data
class RoleToUserForm{
    private String userName;
    private String roleName;
}

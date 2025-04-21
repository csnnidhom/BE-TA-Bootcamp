package com.backend.inventaris.security;


import com.backend.inventaris.config.JwtConfig;
import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.core.MyHttpServletRequestWrapper;
import com.backend.inventaris.service.UserService;
import com.backend.inventaris.util.LoggingFile;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Bearer token
        String authorization = request.getHeader("Authorization");
        authorization = authorization==null ? "" : authorization;
        String token = null;
        String username = null;

        try{
            if(!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length()>7){
                token = authorization.substring(7);
                /** decrypt - OPSIONAL !! */
                if(JwtConfig.getTokenEncryptEnable().equals("y")){
                    token = Crypto.performDecrypt(token);
                }
                username = jwtUtility.getUsernameFromToken(token);
                String strContentType = request.getContentType()==null?"":request.getContentType();
                if(!strContentType.startsWith("multipart/form-data") || "".equals(strContentType)){
                    request = new MyHttpServletRequestWrapper(request);
                }
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    if(jwtUtility.validateToken(token)){
                        UserDetails userDetails = userService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            }
        }catch (Exception e){
            LoggingFile.logException("JwtFilter","doFilterInternal -- "+RequestCapture.allRequest(request),e, OtherConfig.getEnableLog());
        }
        filterChain.doFilter(request, response);
    }
}

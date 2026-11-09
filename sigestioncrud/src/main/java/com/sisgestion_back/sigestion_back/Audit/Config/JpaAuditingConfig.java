package com.sisgestion_back.sigestion_back.Audit.Config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider") // Habilita la auditoría de JPA y especifica el bean AuditorAware
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // Retorna el usuario actual para los campos @CreatedBy y @LastModifiedBy
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
                return Optional.of("SYSTEM"); // O un usuario por defecto si no hay sesión
            }
            return Optional.of(authentication.getName()); // Nombre de usuario de Spring Security
        };
    }

    // Clase de utilidad para obtener datos del contexto HTTP
    public static class AuditContextUtils {

        public static String getClientIpAddress() {
            HttpServletRequest request = getCurrentHttpRequest();
            if (request == null) return "N/A_APP"; // Si no hay request en el contexto, es una operación interna
            String ipAddress = request.getHeader("X-Forwarded-For"); // Para balanceadores de carga/proxies
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr(); // IP directa del cliente
            }
            return ipAddress;
        }

        public static String getClientHostname() {
            HttpServletRequest request = getCurrentHttpRequest();
            if (request == null) return "N/A_APP";
            return Optional.ofNullable(request.getRemoteHost()).orElse("N/A_APP");
        }

        public static String getClientMacAddress() {
            // Repito: Obtener la MAC address del CLIENTE desde el servidor es muy difícil o imposible
            // en entornos web estándar. Esto debería venir del frontend.
            // Esto solo devolverá la MAC del servidor o N/A.
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface ni = networkInterfaces.nextElement();
                    byte[] hardwareAddress = ni.getHardwareAddress();
                    if (hardwareAddress != null && hardwareAddress.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < hardwareAddress.length; i++) {
                            sb.append(String.format("%02X%s", hardwareAddress[i], (i < hardwareAddress.length - 1) ? "-" : ""));
                        }
                        return sb.toString();
                    }
                }
            } catch (SocketException e) {
                // Log error
            }
            return "N/A_CLIENT_MAC_REQUIRES_FRONTEND";
        }

        private static HttpServletRequest getCurrentHttpRequest() {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return (sra != null) ? sra.getRequest() : null;
        }
    }
}
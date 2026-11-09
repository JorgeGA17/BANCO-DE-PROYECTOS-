package com.sisgestion_back.sigestion_back.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sisgestion_back.sigestion_back.model.entity.Proyecto;
import com.sisgestion_back.sigestion_back.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RAGService {

    private final ProyectoRepository proyectoRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "de", "la", "el", "un", "una", "sobre", "en", "y", "o", "con", "por",
            "para", "dame", "informacion", "proyecto", "proyectos", "acerca", "del"
    ));

    @Value("${gemini.api.key:}")
    private String apiKey;

    @Value("${gemini.model:gemini-2.5-flash}")
    private String geminiModel;

    public RAGService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
        this.objectMapper = new ObjectMapper();

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(60000);

        this.restTemplate = new RestTemplate(factory);
    }

    private List<String> extractKeywords(String question) {
        String[] words = question.toLowerCase().split("\\s+");
        return Arrays.stream(words)
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toList());
    }

    public String generateResponse(String question) {
        List<String> keywords = extractKeywords(question);

        if (keywords.isEmpty()) {
            return "Por favor, haz una pregunta más específica sobre los proyectos.";
        }

        Set<Proyecto> relevantProyectos = new HashSet<>();
        for (String keyword : keywords) {
            relevantProyectos.addAll(proyectoRepository.searchByKeyword(keyword));
        }

        if (relevantProyectos.isEmpty()) {
            return "No encontré información sobre '" + String.join(" ", keywords) + "' en los proyectos.";
        }

        String context = relevantProyectos.stream()
                .map(p -> "Proyecto: " + p.getXnombreProyecto() + 
                          ", Resumen: " + (p.getXresumen() != null ? p.getXresumen() : "No disponible.") +
                          ", Corte: " + (p.getCortefk() != null ? p.getCortefk().getXnombre() : "No asignado.") +
                          ", Peso: " + p.getNpeso())
                .collect(Collectors.joining("\n---\n"));

        String prompt = "Responde la pregunta del usuario basándote únicamente en el siguiente contexto. " +
                "Para cada proyecto que encuentres en el contexto, debes listar explícitamente su nombre usando el formato '- Nombre del Proyecto: [descripción]'.\n\n" +
                "Contexto:\n" + context + "\n\n" +
                "Pregunta: " + question;

        return callGeminiAPI(prompt);
    }

    private String callGeminiAPI(String prompt) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            String errorMsg = "ERROR CRÍTICO: La API Key de Gemini está vacía. Verifica que la variable GEMINI_API_KEY esté configurada en el entorno o en application.properties.";
            System.err.println(errorMsg);
            return errorMsg;
        }

        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + geminiModel + ":generateContent?key=" + apiKey.trim();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // Construcción del JSON usando ObjectMapper para evitar problemas con saltos de línea o caracteres especiales
            Map<String, Object> part = Map.of("text", prompt);
            Map<String, Object> content = Map.of("parts", List.of(part));
            Map<String, Object> generationConfig = Map.of("temperature", 0.2);
            Map<String, Object> bodyMap = Map.of(
                    "contents", List.of(content),
                    "generationConfig", generationConfig
            );

            String requestBody = objectMapper.writeValueAsString(bodyMap);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<GeminiResponse> response = restTemplate.postForEntity(url, entity, GeminiResponse.class);

            if (response.getBody() != null && response.getBody().getText() != null) {
                return response.getBody().getText().trim();
            } else {
                return "La respuesta de la API no tuvo el formato esperado o estaba vacía.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al contactar el servicio de IA. Revisa la consola para más detalles. Causa: " + e.getMessage();
        }
    }

    private static class GeminiResponse {
        @JsonProperty("candidates")
        private List<Candidate> candidates;

        public String getText() {
            if (candidates != null && !candidates.isEmpty()) {
                Candidate firstCandidate = candidates.get(0);
                if (firstCandidate.content != null && firstCandidate.content.parts != null && !firstCandidate.content.parts.isEmpty()) {
                    return firstCandidate.content.parts.get(0).text;
                }
            }
            return null;
        }
    }

    private static class Candidate {
        @JsonProperty("content")
        private Content content;
    }

    private static class Content {
        @JsonProperty("parts")
        private List<Part> parts;
    }

    private static class Part {
        @JsonProperty("text")
        private String text;
    }
}

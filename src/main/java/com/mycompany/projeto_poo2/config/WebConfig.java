package com.mycompany.projeto_poo2.config;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String pastaUploads;

    public WebConfig(@Value("${app.uploads.dir}") String pastaUploads) {
        this.pastaUploads = pastaUploads;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(
                "/anuncios/novo", "/anuncios/*/editar", "/anuncios/*/excluir", "/anuncios/*/status",
                "/anuncios/*/fotos", "/anuncios/*/fotos/**", "/anuncios/*/negociacoes", "/meus-anuncios",
                "/negociacoes", "/negociacoes/**", "/perfil", "/perfil/**", "/admin/**");
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String local = Path.of(pastaUploads).toAbsolutePath().normalize().toUri().toString();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(local.endsWith("/") ? local : local + "/");
    }
}

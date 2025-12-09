package com.backendpill.cart.infrastructure.controller;

import com.backendpill.auth.application.UserService;
import com.backendpill.auth.application.dtos.UserResponse;
import com.backendpill.cart.application.CartService;
import com.backendpill.cart.application.dtos.CartRequest;
import com.backendpill.cart.application.dtos.CartResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expone las operaciones del Carrito de Compras.
 * <p>
 * <b>Rol Arquitectónico (Driving Adapter):</b>
 * Su responsabilidad principal es adaptar las peticiones HTTP al lenguaje del dominio.
 * Además, realiza la <b>Resolución de Identidad</b>: traduce el principal de seguridad
 * (email del token) en un ID de usuario numérico, desacoplando al Servicio de Carrito
 * de los detalles de autenticación.
 */
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    /**
     * Obtiene el estado actual del carrito del usuario autenticado.
     *
     * @param userDetails Detalles del usuario extraídos del token JWT.
     * @return El carrito con productos enriquecidos (nombres, precios, imágenes).
     */
    @GetMapping
    public ResponseEntity<CartResponse> getMyCart(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse user = userService.findByEmailAsResponse(userDetails.getUsername());
        return ResponseEntity.ok(cartService.getMyCart(user.id()));
    }

    /**
     * Agrega un item al carrito o actualiza su cantidad si ya existe.
     *
     * @param userDetails Identidad del usuario.
     * @param request Datos del producto y cantidad.
     * @return El carrito actualizado con los nuevos totales calculados.
     */
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CartRequest request
    ) {
        UserResponse user = userService.findByEmailAsResponse(userDetails.getUsername());
        return ResponseEntity.ok(cartService.addToCart(user.id(), request));
    }

    /**
     * Elimina un producto específico del carrito.
     *
     * @param userDetails Identidad del usuario.
     * @param productId ID del producto a remover.
     * @return El carrito actualizado (sin el item eliminado) para refrescar la UI.
     */
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponse> removeItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId
    ) {
        UserResponse user = userService.findByEmailAsResponse(userDetails.getUsername());
        return ResponseEntity.ok(cartService.removeFromCart(user.id(), productId));
    }

    /**
     * Vacía completamente el carrito de compras.
     *
     * @param userDetails Identidad del usuario.
     * @return 204 No Content.
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse user = userService.findByEmailAsResponse(userDetails.getUsername());
        cartService.clearCart(user.id());
        return ResponseEntity.noContent().build();
    }
}
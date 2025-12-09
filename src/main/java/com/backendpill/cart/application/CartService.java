package com.backendpill.cart.application;

import com.backendpill.cart.application.dtos.*;
import com.backendpill.cart.domain.Cart;
import com.backendpill.cart.domain.CartItem;
import com.backendpill.cart.domain.repository.CartRepository;
import com.backendpill.catalog.domain.Product;
import com.backendpill.catalog.domain.repository.ProductRepository;
import com.backendpill.shared.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de aplicación para la gestión del carrito de compras.
 * <p>
 * <b>Responsabilidades de Arquitectura:</b>
 * <ol>
 * <li><b>Orquestación:</b> Coordina el repositorio de Carrito y el de Productos.</li>
 * <li><b>Gestión de Sesión:</b> Asegura que cada usuario tenga un único carrito activo (Get or Create).</li>
 * <li><b>Enriquecimiento de Datos (Data Enrichment):</b> Combina los IDs almacenados en el carrito
 * con la información detallada del catálogo (nombres, precios, imágenes) en tiempo real.</li>
 * </ol>
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    /**
     * Agrega un producto al carrito del usuario.
     * <p>
     * <b>Lógica:</b>
     * 1. Recupera el carrito existente o crea uno nuevo si es la primera interacción.
     * 2. Valida contra el Catálogo que el producto exista (Integridad referencial lógica).
     * 3. Delega a la Entidad de Dominio {@link Cart} la lógica de fusión (sumar cantidad si ya existe).
     * 4. Persiste y retorna la vista enriquecida.
     *
     * @param userId ID del usuario autenticado.
     * @param request Datos del item a agregar.
     * @return El carrito actualizado con precios calculados.
     * @throws NotFoundException Si el producto no existe en el catálogo.
     */
    @Transactional
    public CartResponse addToCart(Long userId, CartRequest request) {
        // 1. Obtener o crear carrito (Strategy: Lazy Creation)
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.builder().userId(userId).build());

        // 2. Validar existencia del producto
        if (!productRepository.existsById(request.productId())) {
            throw new NotFoundException("Producto no encontrado");
        }

        // 3. Agregar item (Delegación al Rich Domain Model)
        CartItem item = CartItem.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .build();

        cart.addItem(item);

        // 4. Guardar y transformar
        Cart savedCart = cartRepository.save(cart);
        return mapToResponse(savedCart);
    }

    /**
     * Elimina un producto específico del carrito.
     *
     * @param userId ID del usuario.
     * @param productId ID del producto a remover.
     * @return El carrito actualizado.
     */
    @Transactional
    public CartResponse removeFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado"));

        cart.removeItem(productId);

        Cart savedCart = cartRepository.save(cart);
        return mapToResponse(savedCart);
    }

    /**
     * Recupera el estado actual del carrito del usuario.
     * <p>
     * Si el usuario no tiene carrito, retorna una estructura vacía en lugar de error 404,
     * facilitando la lógica del frontend.
     *
     * @param userId ID del usuario.
     * @return El carrito enriquecido.
     */
    @Transactional(readOnly = true)
    public CartResponse getMyCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.builder().userId(userId).build());
        return mapToResponse(cart);
    }

    /**
     * Vacía el carrito por completo (ej. al finalizar una compra o vaciar manualmente).
     *
     * @param userId ID del usuario.
     */
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart != null) {
            cart.clear();
            cartRepository.save(cart);
        }
    }

    // --- MAPPER DE LÓGICA DE NEGOCIO ---

    /**
     * Transforma la entidad de dominio {@link Cart} en un DTO {@link CartResponse}.
     * <p>
     * <b>Importante:</b> Este método realiza consultas al {@link ProductRepository} para
     * obtener los precios actuales y metadatos de los productos.
     * Calcula los subtotales y el total general en el servidor para garantizar la integridad financiera.
     *
     * @param cart La entidad carrito con datos crudos (IDs y cantidades).
     * @return El DTO con datos completos y calculados.
     */
    private CartResponse mapToResponse(Cart cart) {
        List<CartItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;

        for (CartItem item : cart.getItems()) {
            // Nota de Rendimiento: En un escenario de alta carga, esto podría generar un problema N+1.
            // Optimización futura: Usar productRepository.findAllById(ids) para traer todo en una sola query.
            Product product = productRepository.findById(item.getProductId()).orElse(null);

            if (product != null) {
                BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

                itemResponses.add(new CartItemResponse(
                        product.getId(),
                        product.getName(),
                        product.getSlug(),
                        product.getImageUrl(),
                        product.getPrice(),
                        item.getQuantity(),
                        subtotal
                ));

                totalAmount = totalAmount.add(subtotal);
                totalItems += item.getQuantity();
            }
        }

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                itemResponses,
                totalAmount,
                totalItems
        );
    }
}
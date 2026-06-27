package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

        CartItem existingCartItem = cartItemRepository
                .findByUserIdAndProductId(userId, String.valueOf(request.getProductId()));

        if (existingCartItem != null) {
            existingCartItem.setQuantity(
                    existingCartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(String.valueOf(request.getProductId()));
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.ZERO);
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteCartItem(String userId, CartItemRequest request) {

        CartItem existingCartItem = cartItemRepository
                .findByUserIdAndProductId(userId, String.valueOf(request.getProductId()));

        if (existingCartItem == null) {
            return false;
        }

        cartItemRepository.delete(existingCartItem);
        return true;
    }

    public List<CartItemResponse> getCartItems(String userId) {
        return cartItemRepository.findByUserId(userId).stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getUserId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getCreatedAt(),
                        item.getUpdatedAt()
                )).collect(Collectors.toList());
    }

    public List<CartItem> getCartItemsRaw(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
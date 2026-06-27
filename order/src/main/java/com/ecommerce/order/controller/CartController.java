package com.ecommerce.order.controller;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<Void> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {

        if (cartItemService.addToCart(userId, request)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCartItem(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {

        if (cartItemService.deleteCartItem(userId, request)) {
            return new ResponseEntity<>("Item Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("Item Not Deleted", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(
            @RequestHeader("X-User-ID") String userId) {
        return new ResponseEntity<>(cartItemService.getCartItems(userId), HttpStatus.OK);
    }
}
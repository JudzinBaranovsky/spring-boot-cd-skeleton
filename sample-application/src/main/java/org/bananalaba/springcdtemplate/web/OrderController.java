package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.bananalaba.springcdtemplate.model.OrderDto;
import org.bananalaba.springcdtemplate.model.OrderReferenceDto;
import org.bananalaba.springcdtemplate.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Loggable
public class OrderController {

    @NonNull
    private OrderService service;

    @GetMapping(path = "/{id}", produces = "application/json")
    public OrderDto getById(final @PathVariable long id) {
        return service.get(id);
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public OrderReferenceDto create(final @RequestBody OrderDto order) {
        var id = service.create(order);
        return new OrderReferenceDto(id);
    }

}

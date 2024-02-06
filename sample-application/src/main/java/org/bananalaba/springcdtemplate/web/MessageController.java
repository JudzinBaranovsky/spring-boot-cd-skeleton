package org.bananalaba.springcdtemplate.web;

import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.data.Message;
import org.bananalaba.springcdtemplate.data.MessageUpdate;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.bananalaba.springcdtemplate.service.MessageService;
import org.bananalaba.springcdtemplate.stereotype.WebComponent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/messages")
@RequiredArgsConstructor
@Loggable
public class MessageController implements WebComponent {

    private final MessageService service;

    @PostMapping(path = "/{key}", consumes = "application/json")
    public void save(@PathVariable("key") String key, @RequestBody MessageUpdate message) {
        service.save(key, message);
    }

    @DeleteMapping("/{key}")
    public void delete(@PathVariable("key") String key) {
        service.delete(key);
    }

    @GetMapping(path = "/{key}", produces = "application/json")
    @ResponseBody
    public Message get(@PathVariable("key") String key) {
        return service.get(key);
    }

}

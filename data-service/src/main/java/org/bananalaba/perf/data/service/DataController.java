package org.bananalaba.perf.data.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.model.DataItemDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/data")
@Slf4j
@RequiredArgsConstructor
public class DataController {

    @NonNull
    private final DataService dataService;

    @RequestMapping(method = RequestMethod.GET, value = "/{parameter}")
    public DataItemDto get(final String parameter) {
        return dataService.getData(parameter);
    }

}

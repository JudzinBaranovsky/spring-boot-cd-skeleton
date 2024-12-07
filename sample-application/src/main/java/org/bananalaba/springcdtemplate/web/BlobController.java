package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.data.BlobItemSummary;
import org.bananalaba.springcdtemplate.logging.Loggable;
import org.bananalaba.springcdtemplate.service.BlobItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blob")
@RequiredArgsConstructor
@Loggable
public class BlobController {

    @NonNull
    private final BlobItemService blobItemService;

    @RequestMapping(method = RequestMethod.POST, path = "/process-image")
    public BlobItemSummary processImage(@RequestParam("id") final long id) {
        return blobItemService.processImage(id);
    }

}

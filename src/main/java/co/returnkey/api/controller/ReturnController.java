package co.returnkey.api.controller;

import co.returnkey.api.domain.QcSetStatusRequest;
import co.returnkey.api.domain.ReturnRequest;
import co.returnkey.api.entity.ReturnEntity;
import co.returnkey.api.service.QcService;
import co.returnkey.api.service.ReturnService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/returns")
public class ReturnController {

  private final ReturnService returnService;
  private final QcService qcService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReturnEntity create(@RequestBody @Valid final ReturnRequest request) {
    return returnService.createReturn(request);
  }

  @GetMapping("/{returnId}")
  public ReturnEntity getById(@PathVariable final Long returnId) {
    return returnService.findById(returnId);
  }

  @PutMapping("/{returnId}/items/{itemId}/qc/status")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void setQcStatus(@PathVariable final Long returnId, @PathVariable final Long itemId,
      @RequestBody @Valid QcSetStatusRequest request) {
    qcService.changeStatus(returnId, itemId, request.getStatus());
  }
}

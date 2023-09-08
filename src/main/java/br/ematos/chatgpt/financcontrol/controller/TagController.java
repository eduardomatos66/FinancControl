package br.ematos.chatgpt.financcontrol.controller;

import br.ematos.chatgpt.financcontrol.entity.Tag;
import br.ematos.chatgpt.financcontrol.service.TagService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {

  private final TagService tagService;

  @GetMapping
  public List<Tag> getAllTags() {
    return tagService.findAllTags();
  }

  @GetMapping("/by-tag-name/{name}")
  public List<Tag> getTagsByName(@PathVariable String name) {
    return tagService.findTagsByName(name);
  }

  @PostMapping
  public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
    Tag createdTag = tagService.createTag(tag);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
  }
}
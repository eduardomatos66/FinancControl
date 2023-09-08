package br.ematos.chatgpt.financcontrol.service;

import br.ematos.chatgpt.financcontrol.entity.Tag;
import br.ematos.chatgpt.financcontrol.exception.EntityNotFoundException;
import br.ematos.chatgpt.financcontrol.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class TagService extends AbstractService<Tag> {

  private final TagRepository tagRepository;

  @Override
  public Optional<Tag> findById(Integer id) {
    log.info("Finding Tag by ID: " + id);
    return Optional.of(tagRepository.getReferenceById(id));
  }

  public List<Tag> findAllTags() {
    log.info("Finding all Tags");
    return tagRepository.findAll();
  }

  public List<Tag> findTagsByName(String name) {
    log.info("Finding Tags by Name: " + name);
    return tagRepository.findByName(name);
  }

  public Tag createTag(Tag tag) {
    log.info("Creating a new Tag: " + tag);
    return tagRepository.findByName(tag.getName()).stream()
        .findFirst()
        .orElseGet(() -> tagRepository.save(tag));
  }

  public List<Tag> createTags(List<Tag> tags) {
    log.info("Creating multiple Tags");
    return tags.stream().map(this::createTag).toList();
  }

  public boolean deleteTag(Integer id) {
    log.info("Deleting Tag with ID: " + id);
    return tagRepository
        .findById(id)
        .map(
            tag -> {
              tagRepository.delete(tag);
              log.info("Tag deleted successfully: " + tag);
              return true;
            })
        .orElse(false);
  }

  public Tag updateTag(Integer id, Tag updatedTag) {
    log.info("Updating Tag with ID: " + id);
    Optional<Tag> optionalTag = tagRepository.findById(id);
    if (optionalTag.isEmpty()) {
      throw new EntityNotFoundException("Tag with id " + id + " not found");
    }

    Tag tag = optionalTag.get();
    tag.setName(updatedTag.getName());
    return tagRepository.save(tag);
  }
}

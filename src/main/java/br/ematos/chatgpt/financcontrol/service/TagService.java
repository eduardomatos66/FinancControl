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
    return Optional.of(tagRepository.getReferenceById(id));
  }

  public List<Tag> findAllTags() {
    return tagRepository.findAll();
  }

  public List<Tag> findTagsByName(String name) {
    return tagRepository.findByName(name);
  }

  public Tag createTag(Tag tag) {
    return tagRepository.findByName(tag.getName()).stream()
        .findFirst()
        .orElseGet(() -> tagRepository.save(tag));
  }

  public boolean deleteTag(Integer id) {
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
    Optional<Tag> optionalTag = tagRepository.findById(id);
    if (optionalTag.isEmpty()) {
      throw new EntityNotFoundException("Tag with id " + id + " not found");
    }

    Tag tag = optionalTag.get();
    tag.setName(updatedTag.getName());
    return tagRepository.save(tag);
  }
}

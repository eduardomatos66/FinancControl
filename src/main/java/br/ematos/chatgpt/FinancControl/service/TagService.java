package br.ematos.chatgpt.FinancControl.service;

import br.ematos.chatgpt.FinancControl.entity.Tag;
import br.ematos.chatgpt.FinancControl.repository.TagRepository;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
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
        return tagRepository.save(tag);
    }
}

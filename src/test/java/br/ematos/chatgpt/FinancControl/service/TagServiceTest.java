package br.ematos.chatgpt.FinancControl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.ematos.chatgpt.FinancControl.entity.Tag;
import br.ematos.chatgpt.FinancControl.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TagServiceTest {
  @Mock private TagRepository tagRepository;

  @InjectMocks private TagService tagService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testFindById() {
    Tag mockTag = new Tag(1, "MockTag");
    when(tagRepository.getReferenceById(1)).thenReturn(mockTag);

    Optional<Tag> result = tagService.findById(1);

    assertTrue(result.isPresent());
    assertEquals(mockTag, result.get());
  }

  @Test
  public void testFindAllTags() {
    List<Tag> mockTags = new ArrayList<>();
    mockTags.add(new Tag(1, "Tag1"));
    mockTags.add(new Tag(2, "Tag2"));
    when(tagRepository.findAll()).thenReturn(mockTags);

    List<Tag> result = tagService.findAllTags();

    assertEquals(2, result.size());
    assertEquals("Tag1", result.get(0).getName());
    assertEquals("Tag2", result.get(1).getName());
  }

  @Test
  public void testFindTagsByName() {
    List<Tag> mockTags = new ArrayList<>();
    mockTags.add(new Tag(1, "Tag1"));
    mockTags.add(new Tag(2, "Tag1"));
    when(tagRepository.findByName("Tag1")).thenReturn(mockTags);

    List<Tag> result = tagService.findTagsByName("Tag1");

    assertEquals(2, result.size());
    assertEquals("Tag1", result.get(0).getName());
    assertEquals("Tag1", result.get(1).getName());
  }

  @Test
  public void testCreateTag() {
    Tag newTag = new Tag(1, "NewTag");
    when(tagRepository.save(any(Tag.class))).thenReturn(newTag);

    Tag result = tagService.createTag(newTag);

    assertNotNull(result);
    assertEquals(newTag.getName(), result.getName());
  }
}

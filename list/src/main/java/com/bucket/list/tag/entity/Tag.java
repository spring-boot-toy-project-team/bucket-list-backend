package com.bucket.list.tag.entity;

import com.bucket.list.completedList.entity.CompletedList;
import com.bucket.list.completedList.entity.CompletedListTag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long tagId;

  @Column(unique = true)
  private String name;

  @OneToMany(mappedBy = "tag")
  private List<CompletedListTag> completedListTags;

  public void addCompletedListTag(CompletedListTag completedListTag) {
    if(!completedListTags.contains(completedListTag))
      this.completedListTags.add(completedListTag);
  }
}

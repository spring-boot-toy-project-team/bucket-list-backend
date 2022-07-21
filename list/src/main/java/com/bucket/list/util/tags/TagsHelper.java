package com.bucket.list.util.tags;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class TagsHelper {
  public static String duplicateCheck(String tags) {
    Set<String> tagSet =  Arrays.stream(tags.split("#")).collect(Collectors.toSet());
    return Arrays.stream(tagSet.toArray(String[]::new)).reduce((i, j)->i+"#"+j).orElse(null);
  }
}

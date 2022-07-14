package com.bucket.list.bucketList.service;

import com.bucket.list.bucketList.repository.BucketListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BucketListService {
  private final BucketListRepository bucketListRepository;

}

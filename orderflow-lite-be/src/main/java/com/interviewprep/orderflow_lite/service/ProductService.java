package com.interviewprep.orderflow_lite.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.interviewprep.orderflow_lite.dto.ProductDto;
import com.interviewprep.orderflow_lite.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        log.info("Fetching products with page: {}, size: {}, sort: {}, direction: {}", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString(), "");
        return productRepository.findAll(pageable).map(ProductDto::fromEntity);
    }
}

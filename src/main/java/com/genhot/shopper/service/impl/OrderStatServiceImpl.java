package com.genhot.shopper.service.impl;

import com.genhot.shopper.service.OrderStatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderStatServiceImpl implements OrderStatService {

    private final Logger log = LoggerFactory.getLogger(OrderStatServiceImpl.class);
}

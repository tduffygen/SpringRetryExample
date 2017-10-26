/*
 * Classname : Configuration.java
 *
 * Created on: 26 Oct 2017
 *
 * Copyright (c) 2000-2017 Realex Payments, Ltd.
 * Realex Payments, The Observatory, 7-11 Sir John Rogerson's Quay, Dublin 2, Ireland
 *
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Realex Payments, Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Realex Payments.
 *
 */

package com.realex.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author thomasduffy
 *
 */
@Configuration
@EnableRetry
public class Config {

	@Bean
	HttpGet httpGet() {
		return new RetryingHttpGetExample();
	}
}

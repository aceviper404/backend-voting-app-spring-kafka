package com.demo.backendvotingapp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.demo.backendvotingapp.model.NamesRequest;
import com.demo.backendvotingapp.model.NamesRequestSerializer;

@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	public Map<String, Object> producerConfig(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return map;
	}
	
	@Bean
	public ProducerFactory<String, NamesRequest> producerFactory(){
		return new DefaultKafkaProducerFactory<String, NamesRequest>(producerConfig());
	}
	
	@Bean
	public KafkaTemplate<String, NamesRequest> kafkaTemplate(ProducerFactory<String, NamesRequest> producerFactory){
		return new KafkaTemplate<String, NamesRequest>(producerFactory);
	}
}

package com.demo.backendvotingapp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.demo.backendvotingapp.model.NamesRequest;
import com.demo.backendvotingapp.model.NamesRequestDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
    @Value(value = "${general.topic.group.id}")
    private String groupId;
	
	public Map<String, Object> consumerConfig(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		map.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		
		return map;
	}
	
	@Bean
	public ConsumerFactory<String, NamesRequest> consumerFactory(){
		return new DefaultKafkaConsumerFactory<String, NamesRequest>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(NamesRequest.class));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, NamesRequest> namesRequestListener(ConsumerFactory<String, NamesRequest> consumerFactory){
		ConcurrentKafkaListenerContainerFactory<String, NamesRequest> factory = new ConcurrentKafkaListenerContainerFactory<String, NamesRequest>();
		
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

}

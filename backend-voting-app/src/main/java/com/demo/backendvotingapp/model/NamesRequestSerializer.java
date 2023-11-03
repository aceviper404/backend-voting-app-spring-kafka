package com.demo.backendvotingapp.model;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NamesRequestSerializer implements Serializer<NamesRequest>{

	@Override
	public byte[] serialize(String topic, NamesRequest data) {
	    try {
	        // use Jackson or Gson to convert the object to JSON
	        return new ObjectMapper().writeValueAsBytes(data);
	    } catch (Exception e) {
	        throw new SerializationException("Error serializing NamesRequest", e);
	    }
	}

}

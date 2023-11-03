package com.demo.backendvotingapp.model;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NamesRequestDeserializer implements Deserializer<NamesRequest> {
	@Override
	public NamesRequest deserialize(String topic, byte[] data) {
		try {
			// use Jackson or Gson to convert the JSON to object
			return new ObjectMapper().readValue(data, NamesRequest.class);
		} catch (Exception e) {
			throw new SerializationException("Error deserializing NamesRequest", e);
		}
	}

}

package com.example.license;

import com.example.license.repository.LicenseRepository;
import com.example.license.resolver.Mutation;
import com.example.license.resolver.Query;
import com.example.license.exception.GraphQLErrorAdapter;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
public class LicenseManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicenseManagerApplication.class, args);
	}

	@Bean
	public GraphQLScalarType date() {
		return ExtendedScalars.DateTime;
	}

	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}


	@Bean
	public Query query(LicenseRepository licenseRepository) {
		return new Query(licenseRepository);
	}

	@Bean
	public Mutation mutation(LicenseRepository licenseRepository) {
		return new Mutation(licenseRepository);
	}

}

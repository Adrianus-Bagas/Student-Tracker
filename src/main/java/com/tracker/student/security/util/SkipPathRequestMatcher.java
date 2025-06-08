package com.tracker.student.security.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;

public class SkipPathRequestMatcher implements RequestMatcher {

	private OrRequestMatcher skipMatcher;

	private OrRequestMatcher processingMatcher;

	public SkipPathRequestMatcher(List<String> pathToSkip, List<String> processingPath) {
		List<RequestMatcher> skipMatchers = pathToSkip.stream().map(path -> new AntPathRequestMatcher(path))
				.collect(Collectors.toList());
		List<RequestMatcher> processingMatchers = processingPath.stream().map(path -> new AntPathRequestMatcher(path))
				.collect(Collectors.toList());
		this.skipMatcher = new OrRequestMatcher(skipMatchers);
		this.processingMatcher = new OrRequestMatcher(processingMatchers);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		if(this.skipMatcher.matches(request)) {
			return false;
		}
		return this.processingMatcher.matches(request);
	}

}

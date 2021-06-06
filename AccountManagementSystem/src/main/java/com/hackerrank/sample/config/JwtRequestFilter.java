package com.hackerrank.sample.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.jwt.model.SecurityUser;
import com.hackerrank.sample.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private final ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

	@Value("${rate.limit}")
	private int limit;

	@Value("${rate.limiter.enabled}")
	private boolean isEnabled;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException, RuntimeException {
		boolean proceed = true;

		if (isEnabled) {
			proceed = false;
			if (getRateLimiter() != null) {

				RateLimiter rateLimiter = getRateLimiter();
				if (rateLimiter.tryAcquire()) {
					proceed = true;
				} else {
					logger.info("Time Up - Limit exceeded");
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					Map<String, Object> errorDetails = new HashMap();
					errorDetails.put("message", "Max request exceeded");
					httpServletResponse.setStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.value());
					httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
					mapper.writeValue(httpServletResponse.getWriter(), errorDetails);

				}
			}
		}
		if (proceed) {
			final String requestTokenHeader = request.getHeader("Authorization");

			String username = null;
			String jwtToken = null;
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				try {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					logger.info("Unable to get JWT Token");
				} catch (ExpiredJwtException e) {
					logger.info("JWT Token has expired");
				}
			} else {
				try {
					throw new UnsupportedJwtException("JWT Token should begin with a Bearer String");
				} catch (UnsupportedJwtException e) {
					logger.warn("JWT Token should begin with a Bearer String");
				}
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				SecurityUser userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			chain.doFilter(request, response);
		}
	}

	private RateLimiter getRateLimiter() {

		synchronized (this) {
			RateLimiter rateLimiter = RateLimiter.create(limit, TimeUnit.MINUTES);
			return rateLimiter;
		}
	}

}

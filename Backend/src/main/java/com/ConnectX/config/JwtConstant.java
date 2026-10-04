package com.ConnectX.config;

public class JwtConstant {
	public static String JWT_HEADER="Authorization";
	public static String SECRET_KEY = System.getenv().getOrDefault
	("JWT_SECRET",
	"ConnectX-dev-only-secret-key-at-least-32-chars-long");
}
package com.example.app_medical.Security;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component

public class JwtUtil { // Declares the class itself — a utility for creating and reading JWTs

    private static final String SECRET_KEY =
            "thisIsMysecregtfrdesww233eggtffeeddgkjjhhtdhttebd54ndhdhfhhhshs8877465sbbdd"; // The secret key used to sign and verify tokens — hardcoded here (a security risk in real apps; should come from config/env variables instead)

    private static final long EXPIRATION_MS = 30 * 60 * 1000; // Defines how long a token stays valid: 15 (minutes) * 60 (seconds) * 1000 (ms) = 900,000 ms = 15 minutes

    public String generateToken(String identity) { // Public method that creates a new JWT for a given identity (e.g., username); returns the token as a String

        return Jwts.builder() // Starts building a new JWT using JJWT's fluent builder API

                .setSubject(identity) // Sets the "sub" (subject) claim in the token's payload — here, the user's identity

                .setIssuedAt(new Date()) // Sets the "iat" (issued-at) claim to the current date/time — records when the token was created

                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // Sets the "exp" (expiration) claim to now + 15 minutes — after this time, the token becomes invalid

                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256) // Signs the token: converts SECRET_KEY into bytes, builds an HMAC-SHA key from it, and signs using HS256 — this signature lets you later verify the token wasn't tampered with

                .compact(); // Finalizes the builder and serializes everything into the standard compact JWT string format: header.payload.signature
    }

    public String extractIdentity(String token) { // Public method that takes an existing JWT string and pulls the identity (subject) out of it

        return Jwts.parserBuilder() // Starts building a JWT parser (used to read/validate incoming tokens)

                .setSigningKey(SECRET_KEY.getBytes()) // Tells the parser which key to use to verify the token's signature — must match the key used when signing, or verification fails

                .build() // Finalizes the parser configuration and creates the actual parser object

                .parseClaimsJws(token) // Parses the given token string: decodes it, verifies the signature against the key, and checks expiration — throws an exception if invalid/expired/tampered

                .getBody()   //payload // Retrieves the "claims" (payload) section of the token — the decoded JSON body containing sub, iat, exp, etc.

                .getSubject(); // Extracts just the "sub" (subject) claim from that payload — the identity string originally set in generateToken()
    }
}
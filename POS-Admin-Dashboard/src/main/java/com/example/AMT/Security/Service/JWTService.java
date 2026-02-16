    package com.example.AMT.Security.Service;

    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Service;

    import javax.crypto.SecretKey;
    import java.security.Key;
    import java.util.*;

    @Service
    public class JWTService {
        private String secretKey="";

        public JWTService() {
            SecretKey sk = Jwts.SIG.HS256.key().build();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }
        private Key getKey() {
            byte[] encoded = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(encoded);
        }
        public String generateToken(String userName, Collection<? extends GrantedAuthority> authorities) {
            Map<String,Object> claims = new HashMap<>();
            // Convert authorities to a simple list of strings (e.g., ["ROLE_ADMIN"])
            List<String> roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            claims.put("roles", roles); // 💡 Put the roles inside the JWT!
            return Jwts.builder()
                    .claims(claims)
                    .subject(userName)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                    .signWith(getKey())
                    .compact();
        }
        public Date extractExpiration(String token){
            return extractAllClaims(token).getExpiration();
        }

        public Date extractIssueAt(String token){
            return extractAllClaims(token).getIssuedAt();
        }

        // This is the private "helper" that unlocks the token
        private io.jsonwebtoken.Claims extractAllClaims(String token) {
            return Jwts.parser()
                    .verifyWith((SecretKey) getKey()) // Use your secret stamp
                    .build()
                    .parseSignedClaims(token) // Open the envelope
                    .getPayload();            // Get the data inside
        }

        public String extractUsername(String token) {
            // We open the envelope and look at the "Subject" line
            return extractAllClaims(token).getSubject();
        }

//        public boolean validateToken(String token, UserDetails userDetails) {
//            final String username = extractUsername(token);
//
//            // Check two things:
//            // 1. Does the name on the token match the user from our Database?
//            // 2. Has the token's "Best Before" date passed?
//            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//        }
public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    boolean isNameCorrect = username.equals(userDetails.getUsername());
    boolean isNotExpired = !isTokenExpired(token);

    System.out.println("Is Name Correct? " + isNameCorrect);
    System.out.println("Is Not Expired? " + isNotExpired);

    return (isNameCorrect && isNotExpired);
}

        private boolean isTokenExpired(String token) {
            // It calls extractAllClaims to get the date, then compares it to "Now"
            return extractAllClaims(token).getExpiration().before(new Date());
        }

    }

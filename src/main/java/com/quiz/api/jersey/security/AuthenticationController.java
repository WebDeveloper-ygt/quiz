package com.quiz.api.jersey.security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.utils.ApiUtils;
import com.quiz.api.jersey.utils.ConstantUtils;
import com.quiz.api.jersey.utils.HateoasUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;

@Path("/identity")
public class AuthenticationController {

	private final Logger LOG = Logger.getLogger(AuthenticationController.class);
	String role = "user";
	private Connection dbConnection;
	@Context
	UriInfo uriInfo;
	
	@POST
	public Response createJwtusingJson(LoginCredentials logCreds) throws ExceptionOccurred {

		try {
			String email = logCreds.getEmailId();
			String password = logCreds.getPassword();
			if (email != null && password != null) {
				Response jwtToken = authenticateUserCredentials(email, password);
				//System.out.println(jwtToken.getAuthToken() + " == " + jwtToken);
				if (jwtToken.getStatus() == 200) {
					LOG.info("Generated token for -> " + logCreds.getEmailId() + " is  \n:: Bearer "+ jwtToken.getEntity());
					return Response.status(Status.CREATED).entity(jwtToken.getEntity()).build();
					
				} else {
					return HateoasUtils.userNotFound();
				}
			} else {
				return HateoasUtils.unAuthorizedException();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new ExceptionOccurred();
		}

	}

	private TokenBean issueJWtToken(String username)
			throws InvalidKeyException {

		ZonedDateTime datetime = ZonedDateTime.now();
		Date issuedDate = Date.from(datetime.toInstant());
		Date expriesDate = Date.from(datetime.toInstant().plusSeconds(18000l));
		// Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String token = Jwts.builder().setSubject(username)
									 .setIssuer(uriInfo.getAbsolutePath().toString())
									 .setId(UUID.randomUUID().toString())
									 .setAudience(uriInfo.getAbsolutePath().toString())
									 .setIssuedAt(issuedDate)
									 .setExpiration(expriesDate).claim("name", username)
									 .signWith(SignatureAlgorithm.HS256, ConstantUtils.SECRET.getBytes(StandardCharsets.UTF_8))
									 .compact();
		TokenBean tokenBean = new TokenBean();
		tokenBean.setAuthToken(token);
		tokenBean.setExpiresAt(expriesDate.toString());
		return tokenBean;

	}

	private Response authenticateUserCredentials(String email, String password)
			throws SQLException, ExceptionOccurred, NullPointerException {

		String encode = Base64.getEncoder().encodeToString(password.getBytes());
		System.out.println(encode);

		try {
			dbConnection = ApiUtils.getDbConnection();
			// ConstantUtils.LOGIN
			String query = "select * from quiz_users where emailId=? and password=?";
			PreparedStatement pst = dbConnection.prepareStatement(query);
			pst.setString(1, email);
			pst.setString(2, encode);

			ResultSet result = pst.executeQuery();

			//noinspection unchecked
			while (result.next()) {
				TokenBean jwtToken = issueJWtToken(result.getString(2));
				return Response.status(Status.OK).entity(jwtToken).build();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new ExceptionOccurred();
		}
		 return HateoasUtils.unAuthorizedException();
		
	}
}

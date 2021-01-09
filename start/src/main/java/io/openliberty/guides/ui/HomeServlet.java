package io.openliberty.guides.ui;

import java.io.IOException;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( urlPatterns = "/home" )
@FormAuthenticationMechanismDefinition(
		loginToContinue = @LoginToContinue( 
				errorPage =  "/error.html" , 
				loginPage = "/welcome.html" )
)
@ServletSecurity(
		value = @HttpConstraint( 
				rolesAllowed = { Utils.USER , Utils.ADMIN } ,
				transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL )
)
@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {

	@Inject
	private SecurityContext secutiryContext;
	
	@Override
	protected void doGet( HttpServletRequest request , HttpServletResponse response ) throws ServletException , IOException {
		if( secutiryContext.isCallerInRole( Utils.USER ) ) {
			response.sendRedirect("/admin.jsf");
		}else if( secutiryContext.isCallerInRole( Utils.ADMIN ) ) {
			response.sendRedirect("/user.jsf");
		}
	}
	
	@Override
	protected void doPost( HttpServletRequest request , HttpServletResponse response ) throws ServletException , IOException {
		doGet( request ,  response );
	}
	
}

package a00123456.lab;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import javax.sql.DataSource;

import a00123456.lab.data.DBBean;

public class LabServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	//@Resource(mappedName = "java:jboss/datasources/com/microsoft/sqlserver")
	@Resource(mappedName = "java:/BCIT_MSSQLDS")
	private DataSource dataSource;			
	private DBBean db;


	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		db = new DBBean(dataSource);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("QUERY STRING: " + request.getParameter("queryInput"));
		db.setQueryString(request.getParameter("queryInput"));
		try {
			db.connect();
			db.generateResultSet();
			//request.setAttribute("columns", db.getColumnNames());
			request.setAttribute("results",  ResultSupport.toResult(db.getResultSet()));
			
		} catch (SQLException e) {
			System.err.println(e);
			throw new ServletException(e);
		}
		finally {
			db.cleanUp();
		}
		HttpSession session = request.getSession();
		session.setAttribute("db", db);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/header.jsp");
		dispatcher.include(request, response);
		dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/output.xhtml");
		dispatcher.include(request, response);
		dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/footer.jsp"); 
		dispatcher.include(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	public void destroy() {
		db.cleanUp();
	}
	
}

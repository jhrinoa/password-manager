package com.jleepersonal.password_manager;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.jleepersonal.mongo.MongoService;
import com.jleepersonal.rest_api.AuthSvc;

public class PasswordManagerApp {
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8082);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/passwordMgr");
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/rest/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages","com.jleepersonal.rest_api"); //Set the package where the services reside
        jerseyServlet.setInitParameter("jersey.config.server.prodiver.classnames", AuthSvc.class.getCanonicalName());

        ServletHolder staticServlet = context.addServlet(DefaultServlet.class,"/*");
        staticServlet.setInitParameter("resourceBase","src/main/resources");
        staticServlet.setInitParameter("pathInfoOnly","true");
        
        // TODO: During development, disable caching resources.
        // Enable caching after development?
        staticServlet.setInitParameter("cacheControl","max-age=0,public");        
                
//        List<String> dbs = MongoService.getMongoClient().getDatabaseNames();
//        
//        System.out.println("JLEE PRINT");
//        for(String db : dbs){
//    		System.out.println(db);
//    	}
//        System.out.println("JLEE PRINT END");
        
        try
        {
            server.start();
            server.join();
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
    }
}

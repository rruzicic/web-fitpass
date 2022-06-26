package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import beans.Content;
import beans.PromoCode;
import beans.SportsObject;
import beans.Training;
import beans.User;
import dao.PromoCodeDAO;
import dao.SportsObjectDAO;
import dao.TrainingDAO;
import dao.UserDAO;

@Path("/objects")
public class SportsObjectService {
	
	@Context
	ServletContext ctx;
	
	public SportsObjectService() { 
		
	}
	
	@PostConstruct
	private void init() {
		if (ctx.getAttribute("sportsObjectDAO") == null) {
			ctx.setAttribute("sportsObjectDAO", new SportsObjectDAO());
		}
		if (ctx.getAttribute("trainingDAO") == null) {
			ctx.setAttribute("trainingDAO", new TrainingDAO());
		}
		if (ctx.getAttribute("userDAO") == null) {
			ctx.setAttribute("userDAO", new UserDAO());
		}
		PromoCodeDAO pcDAO = new PromoCodeDAO();
	}
	
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<SportsObject> findAllSportsObjects(@Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		return sportsObjectDAO.findAll();
	}
		

	@GET
	@Path("/showObject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SportsObject setObject(SportsObject so, @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		request.getSession().setAttribute("object", sportsObjectDAO.findByName(so.getName()));
		return sportsObjectDAO.findByName(so.getName());
	}
	
	@GET
	@Path("/currentObject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SportsObject currentObject( @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO"); 
		return sportsObjectDAO.findByName(request.getParameter("name"));
	}
	
	
	@GET
	@Path("/currentContent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Content currentContent( @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO"); 
		return sportsObjectDAO.findContentByName(request.getParameter("name").replace("%20", " "));
	}
	
	@GET
	@Path("/content/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findContent(@PathParam("name") String sportsObjectName, @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO"); 
		return sportsObjectDAO.findContents(sportsObjectName) == null ? 
				Response.status(400).build() : 
					Response.ok(sportsObjectDAO.findContents(sportsObjectName), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/training")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Training findTrainig(@Context HttpServletRequest request){
		System.out.println(request.getAttribute("trainingId"));
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return trainingDAO.findById(Integer.parseInt(request.getParameter("trainingId")));
	}
	
}

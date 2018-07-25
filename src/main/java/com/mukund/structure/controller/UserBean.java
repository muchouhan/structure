package com.mukund.structure.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "UserBean")
@ViewScoped
public class UserBean implements Serializable{
	
	/**
	 * Beans' attributes
	 * =================
	 */
	private static final long serialVersionUID = 1L;

//	@ManagedProperty(value = "#{UserService}")
//	IUserService userService;      
//    
//	List<User> users;
//	
//	User user=new User();
//	User userNuevo=new User();

	private boolean accionEditar = true;
	
	
	/* SETTER AND GETTERS
	*  ==================
	*/	
	
//	public IUserService getUserService() {
//		return userService;
//	}
//
//
//	public void setUserService(IUserService userService) {
//		this.userService = userService;
//	}
//
//
//	public List<User> getUsers() {
//		return users;
//	}
//
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}

//	public User getUser() {
//		return user;
//	}
//
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//
//	public User getUserNuevo() {
//		return userNuevo;
//	}
//
//
//	public void setUserNuevo(User userNuevo) {
//		this.userNuevo = userNuevo;
//	}
	
	public boolean isAccionEditar() {
		return accionEditar;
	}


	public void setAccionEditar(boolean accionEditar) {
		this.accionEditar = accionEditar;
	}


	/* CUSTOM LABELS  */
	
	public String etiBotonDialog(){
		if(accionEditar){
			return "Editar";
		}else{
			return "Nuevo";
		}
	}


 	/* CONSTRUCTOR AND POSTCONSTRUCT 
	*  ============================
 	*/

//	public UserBean() {  
//        users = new ArrayList<User>();  
//   
//    }  
//	
//	@PostConstruct
//	public void init(){				
//		users = userService.getUsers();
//	}

	/* AJAX BUTTON EVENTS  
	*  ==================
	*/

	
	public void nuevoEvent(){
//		accionEditar=false;
//		limpiarCampos();
	}
	
	public void editarEvent(){
		accionEditar=true;
//		try{
//			if(getUserNuevo()!=null){
//				setUser(userService.getUserById(getUserNuevo().getId()));
//				RequestContext.getCurrentInstance().execute("dialog.show()");
//			}else{
//				FacesMessage msg = null;  
//				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Seleccione un registro");  
//				FacesContext.getCurrentInstance().addMessage(null, msg);  
//			}
//		}catch (Exception e){
//			accionEditar=false;
//		}
	}
	
	public void eliminarEvent(){
//		if(getUserNuevo()!=null){
//			RequestContext.getCurrentInstance().execute("confirmation.show()");
//		}
//		else{
//			FacesMessage msg = null;  
//			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Seleccione un registro");  
//			FacesContext.getCurrentInstance().addMessage(null, msg);  
//		}
	}


	//BOTON PROCESAR-DIALOG
	public void procesarDialog(){
		if(accionEditar){
			System.out.println("llegue");
			validarEditar();
		}else{
			validarNuevo();
		}
	}


	/* ACCIONES CRUD
	*  =============
	*/
	
	
	public void insertar(){
//
//		userService.addUser(getUser());
//		refrescarUsers();
//		limpiarCampos();		
	}
	
	public void editar(){
//		getUser().setId(getUser().getId());
//		userService.updateUser(getUser());
//		refrescarUsers();
//		limpiarCampos();
			
	}
	
	public void eliminar(){
//		userService.deleteUser(getUserNuevo());
//		refrescarUsers();
	}		
	
	
	public void validarNuevo() {  
//        RequestContext context = RequestContext.getCurrentInstance();  
//        FacesMessage msg = null;  
//        boolean registrado = false;  
//          
//        if(user.getName() != null  && user.getName().length()>=1 && user.getSurname() != null
//        		&& user.getSurname().length()>=1) {  
//        	registrado = true; 
//    		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", user.getName());
//            insertar();
//        } else {  
//        	registrado = false;  
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de registro", "Campo(s) invalido(s)");  
//        }        
//      
//          
//        FacesContext.getCurrentInstance().addMessage(null, msg);  
//        context.addCallbackParam("registrado", registrado);  
    }  
	
	public void validarEditar() {  
        
		
//		RequestContext context = RequestContext.getCurrentInstance();  
//        FacesMessage msg = null;  
//        boolean editado = false;  
//          
//        if(user.getName() != null  && user.getName().length()>=1) {  
//        	editado = true;  
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Editado", user.getName());
//            editar();
//        } else if(userNuevo==null){  
//        	editado = false;  
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Seleccione un registro");  
//        } 
//        else{
//        	editado = false;  
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Nombre invalido");  
//        }
//          
//        FacesContext.getCurrentInstance().addMessage(null, msg);  
//        context.addCallbackParam("editado", editado);  
    }  
	
	public void validarEliminar() {  
//        RequestContext context = RequestContext.getCurrentInstance();  
//        FacesMessage msg = null;  
//        boolean eliminado = false;  
//          
//        if(userNuevo!=null) {  
//        	eliminado = true;  
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", userNuevo.getName());
//            eliminar();
//        } else {  
//        	eliminado = false;  
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Seleccione un registro");  
//        }  
//          
//        FacesContext.getCurrentInstance().addMessage(null, msg);  
//        context.addCallbackParam("eliminado", eliminado);  
    } 
	

    /*
	*	RUTINAS ADICIONALES
	*	===================
    */


//	public void limpiarCampos()
//	{
//		user= new User();		
//		userNuevo = new User();
//	}
//
//	private void refrescarUsers()
//	{
//		setUsers(userService.getUsers());
//		
//	}
//	
	

}
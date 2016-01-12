package unacloud

import unacloud.enums.ClusterEnum;
import unacloud.enums.PhysicalMachineStateEnum;
import unacloud.enums.VirtualMachineImageEnum;
import javassist.bytecode.stackmap.BasicBlock.Catch;

class ClusterController {
	
	//-----------------------------------------------------------------
	// Properties
	//-----------------------------------------------------------------
	/**
	 * Representation of cluster service
	 */
	ClusterService clusterService
	
	/**
	 * Representation of User Restriction service
	 */
	UserRestrictionService userRestrictionService
	
	/**
	 * Representation of Lab service
	 */
	LaboratoryService laboratoryService
	
	//-----------------------------------------------------------------
	// Actions MVC
	//-----------------------------------------------------------------
	
	/**
	 * Makes session verifications before executing any action
	 */
	
	def beforeInterceptor = {
		if(!session.user){
			flash.message="You must log in first"
			redirect(uri:"/login", absolute:true)
			return false
		}
		session.user.refresh()
	}
	/**
	 * Action by default
	 *
	 */
    def index() { 
		redirect(uri:"/services/cluster/list", absolute:true)
	}
	
	/**
	 * Cluster list action
	 * @return list of all clusters owned by user
	 */
	def list() {
		def user = User.get(session.user.id)
		[clusters: user.getOrderedClusters()]
	}
	
	/**
	 * New cluster creation action that sends the available images for the user
	 * @return list of ordered images that user can add to a new cluster
	 */
	def newCluster(){
		def user = User.get(session.user.id)
		[images: user.getAvailableImages()]
	}
	
	/**
	 * Action to create a new cluster. It redirects to index after saving
	 */
	def save(){		
		if(params.name&&params.images){			
			def user = User.get(session.user.id)
			try{
				clusterService.createCluster(params.name,params.images, user)
				flash.message="Your new cluster has been created"
				flash.type="success"
				redirect(uri:"/services/cluster/list", absolute:true)
			}catch(Exception e){
				flash.message=e.message
				redirect(uri:"/services/cluster/new", absolute:true)
			}		
		}else{
			if(!params.name){
				flash.message="The name for your new cluster is required"
			}else if(!params.images){
				flash.message="At least one image must be selected"
			}			
			redirect(uri:"/services/cluster/new", absolute:true)
		}	
	}
	
	/**
	 * Delete cluster action. Receives the cluster id  and
	 * responses success and redirect to index after deletion
	 * @return
	 */
	def delete(){
		def cluster = Cluster.get(params.id)
		if(cluster){
			def user= User.get(session.user.id)
			try{
				clusterService.deleteCluster(cluster, user)
				flash.message="Your cluster has been deleted"
				flash.type="success"
			}catch(Exception e){
				flash.message = e.message;
			}			
		}
		redirect(uri:"/services/cluster/list", absolute:true)
	}
	
	/**
	 * Deploy options action that brings the form with deploying options for each
	 * image in the cluster
	 * @return limits shown in the information of form and cluster to be deployed
	 */
	def deployOptions(){
		def cluster=Cluster.get(params.id);
		if(cluster&&cluster.state==ClusterEnum.AVAILABLE){
			def unavailable = cluster.images.findAll {it.state==VirtualMachineImageEnum.AVAILABLE}
			if(unavailable.size()!=cluster.images.size()){
				flash.message= "Some images in cluster are not available at this moment. Please, change cluster or remove images in cluster."
				redirect(uri:"/services/cluster/list", absolute:true)
				return
			}	
			def user = User.get(session.user.id)
			def hwdProfilesAvoided = userRestrictionService.getAvoidHwdProfiles(user)
			def labsAvoided = userRestrictionService.getAvoidLabs(user)			
			def quantitiesTree = new TreeMap<String, Integer>()
			def quantitiesAvailableTree = new TreeMap<String, Integer>()
			labsAvoided.each {
				def results = laboratoryService.calculateDeploys(it,hwdProfilesAvoided, false)	
				def resultsAvailable = laboratoryService.calculateDeploys(it,hwdProfilesAvoided, true)
				for(HardwareProfile hwd in hwdProfilesAvoided){
					if(!quantitiesTree.get(hwd.name))quantitiesTree.put(hwd.name,results.get(hwd.name))
					else quantitiesTree.put(hwd.name,results.get(hwd.name)+quantitiesTree.get(hwd.name))
					if(!quantitiesAvailableTree.get(hwd.name))quantitiesAvailableTree.put(hwd.name,resultsAvailable.get(hwd.name))
					else quantitiesAvailableTree.put(hwd.name,resultsAvailable.get(hwd.name)+quantitiesAvailableTree.get(hwd.name))
				}
			}
			def quantities = []
			def quantitiesAvailable = []
			def high = false;
			for(HardwareProfile hwd in hwdProfilesAvoided){
				quantities.add(['name':hwd.name,'quantity':quantitiesTree.get(hwd.name)])
				if(quantitiesAvailableTree.get(hwd.name)>0)high = true
				quantitiesAvailable.add(['name':hwd.name,'quantity':quantitiesAvailableTree.get(hwd.name)])
			}
			[quantities:quantities,quantitiesAvailable:quantitiesAvailable,hardwareProfiles: HardwareProfile.list(),cluster:cluster,high:high]
		}else{
			redirect(uri:"/services/cluster/list", absolute:true)
		}		
	}
	
	/**
	 * Action used to render view to deploy the cluster in external 
	 * @return
	 */
	def externalDeployOptions(){		
		//TODO to be implemented
	}
}

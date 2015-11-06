class UrlMappings {
	
	static excludes = ["/virtualMachineImage/update"]
	static mappings = {
		/**"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}**/
		"/"(controller:"user", action:"home")
		"/login"(view:"/index")
		"/user/login"(controller:'user',action:'login')
		"/logout"(controller:'user',action:"logout")
		"/error"(view:'/error')
		"404"(view:'/error')
		"500"(view:'/error')
		
		/** services - my images**/
		"/services/image/list"(controller:'VirtualMachineImage',action:"list")
		"/services/image/new"(controller:"VirtualMachineImage",action:"newUploadImage")
		"/services/image/upload"(controller:"VirtualMachineImage",action:"upload")
		"/services/image/edit/$id"(controller:'VirtualMachineImage',action:"edit")
		"/services/image/delete/$id"(controller:'VirtualMachineImage',action:"delete")
		"/services/image/clear/$id"(controller:'VirtualMachineImage',action:"clearFromCache")
		"/services/image/public"(controller:'VirtualMachineImage',action:'newFromPublic')
		"/services/image/public/copy"(controller:'VirtualMachineImage',action:'copyPublic')
		"/services/image/edit/save"(controller:'VirtualMachineImage',action:'saveEdit')
		"/services/image/external/$id"(controller:'VirtualMachineImage',action:'external')
		"/services/image/update/$id"(controller:'VirtualMachineImage',action:'update')
		"/services/image/update/save"(controller:'VirtualMachineImage',action:'updateFiles')
		
		/** services - my clusters**/
		"/services/cluster/list"(controller:'Cluster',action:"list")
		"/services/cluster/new"(controller:"Cluster",action:"newCluster")
		"/services/cluster/save"(controller:"Cluster",action:"save")
		"/services/cluster/delete/$id"(controller:"Cluster",action:"delete")
		"/services/cluster/deploy/$id"(controller:"Cluster",action:"deployOptions")
		"/services/cluster/external/$id"(controller:"Cluster",action:"externalDeployOptions")		
		
		/** admin - users**/
		"/admin/user/list"(controller:'user',action:"list")
		"/admin/user/new"(controller:'user',action:"create")
		"/admin/user/save"(controller:'user',action:"save")
		"/admin/user/delete/$id"(controller:'user',action:"delete")
		"/admin/user/edit/$id"(controller:'user',action:"edit")
		"/admin/user/edit/save"(controller:'user',action:"saveEdit")
		"/admin/user/restrictions/$id"(controller:'user',action:"config")
		"/admin/user/restrictions/set"(controller:'user',action:"setRestrictions")
		
		/** admin - groups**/
		"/admin/group/list"(controller:'userGroup',action:"list")
		"/admin/group/new"(controller:'userGroup',action:"create")
		"/admin/group/save"(controller:'userGroup',action:"save")
		"/admin/group/delete/$id"(controller:'userGroup',action:"delete")
		"/admin/group/edit/$id"(controller:'userGroup',action:"edit")		
		"/admin/group/edit/save"(controller:'userGroup',action:"saveEdit")
		"/admin/group/restrictions/$id"(controller:'userGroup',action:"config")
		"/admin/group/restrictions/set"(controller:'userGroup',action:"setRestrictions")
		
		/** admin - hypervisors**/
		"/admin/hypervisor/list"(controller:'hypervisor',action:"list")
		"/admin/hypervisor/new"(controller:'hypervisor',action:"create")
		"/admin/hypervisor/save"(controller:'hypervisor',action:"save")
		"/admin/hypervisor/delete/$id"(controller:'hypervisor',action:"delete")
		"/admin/hypervisor/edit/$id"(controller:'hypervisor',action:"edit")
		"/admin/hypervisor/edit/save"(controller:'hypervisor',action:"saveEdit")
		
		/** admin - Operating system**/
		"/admin/os/list"(controller:'operatingSystem',action:"list")
		"/admin/os/new"(controller:'operatingSystem',action:"create")
		"/admin/os/save"(controller:'operatingSystem',action:"save")
		"/admin/os/delete/$id"(controller:'operatingSystem',action:"delete")
		"/admin/os/edit/$id"(controller:'operatingSystem',action:"edit")
		"/admin/os/edit/save"(controller:'operatingSystem',action:"saveEdit")
		
		/** admin - Labs management**/
		"/admin/lab/list"(controller:'laboratory',action:"index")
		"/admin/lab/new"(controller:'laboratory',action:"create")
		
		"/test/"(controller:"test", action:"index")
		"/functionalities"(view:"/functionalities")
		"/administration"(view:"/administration")
		"/configuration"(view:"/configuration")
		"/configuration/serverVariables"(view:"/configuration/serverVariables")
		"/configuration/agentVersion"(view:"/configuration/agentVersion")
		"/user/create"(view:"user/create")
		"/hypervisor/create"(view:"hypervisor/create")
		"/operatingSystem/create"(view:"operatingSystem/create")
		"/mainpage"(view:"/mainpage")		
		"/account"(controller:"user", action:"account")
	}
}

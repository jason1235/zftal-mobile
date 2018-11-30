/**部门代码js块  开始
 * **************************************************************
 */	
//部门代码列表
	var BmdmGrid = Class.create(BaseJqGrid,{  
			 caption : "部门列表",
			 pager: "pager", //分页工具栏  
		     url: _path+'/xsxx/bmdm_cxJcsj.html?doType=query&yqbh=bmdm_id', //这是Action的请求地址  
		     colModel:[
					{label:'ID',name:'bmdm_id', index:'bmdm_id', key:true, hidden:true},
					{label:'部门代码',name:'bmdm_id', index: 'bmdm_id'},
					{label:'部门名称',name:'bmmc', index: 'bmmc'},
					{label:'部门类型',name:'bmlx', index: 'bmlx'}
			]
		});
	
	//部门代码 按钮菜单事件绑定
	function ancdBd_bmdm(){
		var btnzj=jQuery("#btn_zj");//按钮ID  命名规则  btn_xx（xx:操作）
		var btnsc=jQuery("#btn_sc");
		var btnxg=jQuery("#btn_xg");
		//绑定增加点击事件
		if(btnzj!=null){
			btnzj.click(function () {
				showWindow('增加用户',490,350,'bmdm_zjBmdm.html');return false;
			});
		}
		
		//绑定删除事件
		if(btnsc!=null){
			btnsc.click(function () {
				plcz(_path+'/xsxx/bmdm_scBmdm.html','删除');
				return false;
			});
		}
	
		//绑定修改事件
		if(btnxg!=null){
			btnxg.click(function () {
				var id=getChecked();
				if(id.length!=1){
					alert("请先选定一条记录!");
					return ;
				}
				var url="bmdm_xgBmdm.html"; 
				url+="?";
				url+="bmdm_id="+id;
				showWindow('修改用户用户',490,350,url);
				return false;
			});
		}
	}

	//查询
	function searchResult_bmdm(){
		var map = {};
		map["bmdm_id"] = jQuery('#search_bmdm_id').val();
		map["bmmc"] = jQuery('#search_bmmc').val();
		map["bmlx"] = jQuery('#search_bmlx').val();
		search('tabGrid',map);
	}
		/**部门代码js块  结束
		 * **************************************************************
		 */
		
		
		/**专业代码js块  开始
		 * **************************************************************
		 */
	//专业代码列表
	var ZydmGrid = Class.create(BaseJqGrid,{  
		 caption : "专业列表",
		 pager: "pager", //分页工具栏  
	     url: _path+'/xsxx/bmdm_cxJcsj.html?doType=query&yqbh=zydm', //这是Action的请求地址  
	     colModel:[
				{label:'专业代码',name:'zydm', key:true,index: 'zydm'},
				{label:'专业名称',name:'zymc', index: 'zymc'},
				{label:'学院代码',name:'bmdm_id', index: 'bmdm_id'},
				{label:'学院姓名',name:'bmmc', index: 'bmmc'},
				{label:'学制',name:'xz', index: 'xz'}
		]
		//sortname: 'fbsj', //首次加载要进行排序的字段 
	   	//sortorder: "desc"
	});

	//专业代码 按钮菜单事件绑定
	function ancdBd_zydm(){
		var btnzj=jQuery("#btn_zj");//按钮ID  命名规则  btn_xx（xx:操作）
		var btnsc=jQuery("#btn_sc");
		var btnxg=jQuery("#btn_xg");
		//绑定增加点击事件
		if(btnzj!=null){
			btnzj.click(function () {
				showWindow('增加用户',490,350,'zydm_zjZydm.html');return false;
			});
		}
		
		//绑定删除事件
		if(btnsc!=null){
			btnsc.click(function () {
				plcz(_path+'/xsxx/zydm_scZydm.html','删除');
				return false;
			});
		}
	
		//绑定修改事件
		if(btnxg!=null){
			btnxg.click(function () {
				var id=getChecked();
				if(id.length!=1){
					alert("请先选定一条记录!");
					return ;
				}
				var url="zydm_xgZydm.html"; 
				url+="?";
				url+="zydm="+id;
				showWindow('修改用户用户',490,350,url);
				return false;
			});
		}
	}
	//查询
	function searchResult_zydm(){
		var map = {};
		map["zydm"] = jQuery('#search_zydm').val();
		map["zymc"] = jQuery('#search_zymc').val();
		map["bmdm_id"] = jQuery('#search_bmdm_id').val();
		search('tabGrid',map);
	}
		/**专业代码js块  结束
		 * **************************************************************
		 */
		
		
		/**班级代码js块  开始
		 * **************************************************************
		 */
	//班级代码列表
		var BjdmGrid = Class.create(BaseJqGrid,{  
			 caption : "班级列表",
			 pager: "pager", //分页工具栏  
		     url: _path+'/xsxx/bmdm_cxJcsj.html?doType=query&yqbh=bjdm', //这是Action的请求地址  
		     colModel:[
					{label:'ID',name:'bjdm', index:'bjdm', key:true, hidden:true},
					{label:'班级代码',name:'bjdm', index: 'bjdm'},
					{label:'班级名称',name:'bjmc', index: 'bjmc'},
					{label:'专业代码',name:'zydm', index: 'zydm'},
					{label:'专业名称',name:'zymc', index: 'zymc'},
					{label:'学院代码',name:'bmdm_id', index: 'bmdm_id'},
					{label:'学院姓名',name:'bmmc', index: 'bmmc'},
					{label:'班级',name:'nj', index: 'nj'}
			]
		});

		//班级代码按钮菜单事件绑定
		function ancdBd_bjdm(){
			var btnzj=jQuery("#btn_zj");//按钮ID  命名规则  btn_xx（xx:操作）
			var btnsc=jQuery("#btn_sc");
			var btnxg=jQuery("#btn_xg");
			//绑定增加点击事件
			if(btnzj!=null){
				btnzj.click(function () {
					showWindow('增加用户',490,350,'bjdm_zjBjdm.html');return false;
				});
			}
			
			//绑定删除事件
			if(btnsc!=null){
				btnsc.click(function () {
					plcz(_path+'/xsxx/bjdm_scBjdm.html','删除');
					return false;
				});
			}
		
			//绑定修改事件
			if(btnxg!=null){
				btnxg.click(function () {
					var id=getChecked();
					if(id.length!=1){
						alert("请先选定一条记录!");
						return ;
					}
					var url="bjdm_xgBjdm.html"; 
					url+="?";
					url+="bjdm="+id;
					showWindow('修改用户用户',490,350,url);
					return false;
				});
			}
		}
		//查询
		function searchResult_bjdm(){
			var map = {};
			map["zydm"] = jQuery('#search_zydm').val();
			map["bmdm_id"] = jQuery('#search_bmdm_id').val();
			map["bjdm"] = jQuery('#search_bjdm').val();
			map["bjmc"] = jQuery('#search_bjmc').val();
			map["nj"] = jQuery('#search_nj').val();
			search('tabGrid',map);
		}
		/**班级代码js块  结束
		 * **************************************************************
		 */
		
		
		/**部门、专业、班级代码共用 js块  开始
		 * **************************************************************
		 */
		//数据内容切换
		function  contentSwitch(contentId){
			document.location.href=_path+"/xsxx/bmdm_cxJcsj.html?yqbh="+contentId+"";
		}
		
		/**部门、专业、班级代码共用 js块  结束
		 * **************************************************************
		 */